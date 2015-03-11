#include <linux/module.h>
#include <rtdm/rtdm_driver.h>


#define MAX_DEVICES		3

#include "i2c_omap.h"
#include "rti2c.h"

//faire un champ fifo size
//a utiliser pour une verif lors de read/write
struct rt_i2c_context {
	struct rti2c_config config;	/* current device configuration */
	rtdm_irq_t irq_handle;		/* device IRQ handle */
	rtdm_lock_t lock;		/* lock to protect context struct */
	unsigned long base_addr;	/* hardware IO base address */
	rtdm_event_t event;		/* raised to unblock reader or writter*/
	rtdm_mutex_t out_lock;		/* single-writer mutex */
	int status;			/* cache for LSR + soft-states */
	int errors;			/* cache for errors */
	unsigned char data[8];
};

static const struct rti2c_config default_config = {
	0xFFFF, RTI2C_DEF_BAUD,RTI2C_DEF_TIMEOUT, RTI2C_DEF_TIMEOUT
};

static struct rtdm_device *device[MAX_DEVICES];

static unsigned int irq[MAX_DEVICES]={I2C1_IRQ,I2C2_IRQ,I2C3_IRQ};
static unsigned long irqtype[MAX_DEVICES] = {
	[0 ... MAX_DEVICES-1] = RTDM_IRQTYPE_SHARED | RTDM_IRQTYPE_EDGE
};


static unsigned long mem[MAX_DEVICES]={I2C1_MEM,I2C2_MEM,I2C3_MEM};
static void *mapped_io[MAX_DEVICES];
static unsigned int port_num[MAX_DEVICES];

compat_module_param_array(port_num, uint, MAX_DEVICES, 0400);
MODULE_PARM_DESC(port_num, "serial devices port numbers (1,2,3)");

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Guillaume Sanahuja");

#define DEVICE_NAME			"rt_i2c"

static int rt_i2c_init_io(int dev_id, char* name)
{
	mapped_io[dev_id] = ioremap(mem[dev_id], 8);
	if (!mapped_io[dev_id])
		return -EBUSY;

	return 0;
}

static void rt_i2c_release_io(int dev_id)
{
	iounmap(mapped_io[dev_id]);
}


static int rt_i2c_interrupt(rtdm_irq_t * irq_context)
{
	struct rt_i2c_context *ctx;
	unsigned long base;
	int ret = RTDM_IRQ_NONE;
	int val;
	unsigned char index;

	ctx = rtdm_irq_get_arg(irq_context, struct rt_i2c_context);
	base = ctx->base_addr;

	rtdm_lock_get(&ctx->lock);

	val=readw((void *)base + I2C_STAT);
	//rtdm_printk("interrupt I2C_STAT %x\n",val);

	if(testbits(val,NACK))
	{
		ctx->errors=val;
		writew(NACK,(void *)base + I2C_STAT);
		writew(TXFIFO_CLR,(void *)base + I2C_BUF);
	}

	if(testbits(val,BF) && testbits(val,ARDY))
	{
		ctx->status=val;
		rtdm_event_signal(&ctx->event);
		writew(BF,(void *)base + I2C_STAT);
		writew(ARDY,(void *)base + I2C_STAT);
	}


	if(testbits(val,RRDY))
	{
        //rtdm_printk("cnt %x\n",readw((void *)base  + I2C_CNT));
        index=readw((void *)base  + I2C_CNT);
		ctx->data[index]=readb((void *)base  + I2C_DATA);
        //rtdm_printk("read %i %x\n",index,ctx->data[index]);
		writew(RRDY,(void *)base + I2C_STAT);
	}

    val=readw((void *)base + I2C_STAT);
    writew(0xffff,(void *)base + I2C_STAT);
	rtdm_lock_put(&ctx->lock);
	ret = RTDM_IRQ_HANDLED;
	//rtdm_printk("fin interrupt I2C_STAT %x\n",val);
	return ret;
}


static int rt_i2c_set_config(struct rt_i2c_context *ctx, const struct rti2c_config *config)
{
	rtdm_lockctx_t lock_ctx;
	unsigned long base = ctx->base_addr;
	int err = 0;
	int fsscll, fssclh;
	unsigned int scll, sclh;
    rtdm_printk("rt_i2c_set_config\n");

	/* make line configuration atomic and IRQ-safe */
	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	if (testbits(config->config_mask, RTI2C_SET_BAUD))
	{
		ctx->config.baud_rate = config->baud_rate;

		/* Only handle standard and fast speeds */
		if ((config->baud_rate != RTI2C_STANDARD_BAUD) && (config->baud_rate != RTI2C_FAST_MODE_BAUD))
        {
			rtdm_printk("Error : I2C unsupported speed %d\n", config->baud_rate);
			err=-EINVAL;
		}

		/* Standard and fast speed */
		fsscll = fssclh = I2C_INTERNAL_SAMPLING_CLK / (2 * config->baud_rate);

		fsscll -= I2C_FASTSPEED_SCLL_TRIM;
		fssclh -= I2C_FASTSPEED_SCLH_TRIM;
		if( (fsscll < 0) || (fssclh < 0) || (fsscll > 255) || (fssclh > 255) )
		{
			rtdm_printk("Error : I2C initializing clock\n");
			err=-EINVAL;
		}

		if(err==0)
		{
			scll = (unsigned int)fsscll;
			sclh = (unsigned int)fssclh;

			//sccl
			writeb(scll,(void *)base + I2C_SCLL);
			writeb(sclh,(void *)base + I2C_SCLH);
		}
	}

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	/* Timeout manipulation is not atomic. The user is supposed to take
	   care not to use and change timeouts at the same time. */
	if (testbits(config->config_mask, RTI2C_SET_TIMEOUT_RX))
	{
		if(config->rx_timeout>=0)
		{
			ctx->config.rx_timeout = config->rx_timeout;
		}
		else
		{
			rtdm_printk("Error : non blocking read unsupported\n");
			err=-EINVAL;
		}
	}
	if (testbits(config->config_mask, RTI2C_SET_TIMEOUT_TX))
	{
		if(config->tx_timeout>=0)
		{
			ctx->config.tx_timeout = config->tx_timeout;
		}
		else
		{
			rtdm_printk("Error : non blocking write unsupported\n");
			err=-EINVAL;
		}
	}

	rtdm_printk("fin rt_i2c_set_config\n");
	return err;
}


void rt_i2c_cleanup_ctx(struct rt_i2c_context *ctx)
{
	rtdm_event_destroy(&ctx->event);
}

/**
 * Open the device
 *
 * This function is called when the device shall be opened.
 *
 */
static int i2c_open(struct rtdm_dev_context *context,
				rtdm_user_info_t * user_info, int oflags)
{
	struct rt_i2c_context *ctx;
	int dev_id = context->device->device_id;
	int err;
	rtdm_lockctx_t lock_ctx;

	ctx = (struct rt_i2c_context *)context->dev_private;
    rtdm_printk("i2c_open %i\n",dev_id);

	/* IPC initialisation - cannot fail with used parameters */
	rtdm_lock_init(&ctx->lock);
	rtdm_event_init(&ctx->event, 0);

	ctx->base_addr = (unsigned long)mapped_io[dev_id];

	ctx->status = 0;

	rt_i2c_set_config(ctx, &default_config);

	err = rtdm_irq_request(&ctx->irq_handle, irq[dev_id],
			       rt_i2c_interrupt, irqtype[dev_id],
			       context->device->proc_name, ctx);
	if (err)
	{
		rtdm_printk("rtdm_irq_request err\n");
		rt_i2c_cleanup_ctx(ctx);

		return err;
	}

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	/* enable interrupts */
	writew(0x7fff,ctx->base_addr + I2C_STAT);
	//writew(BF|NACK|ARDY|XRDY,ctx->base_addr + I2C_IE);
    //writew(BF|ARDY|RRDY,ctx->base_addr + I2C_IE);
    writew(0x7fff,ctx->base_addr + I2C_IE);
    writew(BF|ARDY|RRDY|AERR,ctx->base_addr + I2C_IE);
    writew(0,ctx->base_addr + I2C_CNT);

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	rtdm_printk("fin i2c_open\n");

	return 0;
}

/**
 * Close the device
 *
 * This function is called when the device shall be closed.
 *
 */
static int i2c_close(struct rtdm_dev_context *context,
				rtdm_user_info_t * user_info)
{
	struct rt_i2c_context *ctx;
	unsigned long base;

	rtdm_lockctx_t lock_ctx;
    rtdm_printk("i2c_close\n");
	ctx = (struct rt_i2c_context *)context->dev_private;
	base = ctx->base_addr;

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	/* mask all I2C interrupts and clear pending ones. */
	writew(0,base + I2C_IE);
	writew(0x7fff,base + I2C_STAT);

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	rtdm_irq_free(&ctx->irq_handle);

	rt_i2c_cleanup_ctx(ctx);

	return 0;
}

int i2c_ioctl(struct rtdm_dev_context *context,
		   rtdm_user_info_t * user_info,
		   unsigned int request, void *arg)
{
	rtdm_lockctx_t lock_ctx;
	struct rt_i2c_context *ctx;
	int err = 0;
	unsigned long base;

	ctx = (struct rt_i2c_context *)context->dev_private;
	base = ctx->base_addr;

	switch (request)
	{
		case RTI2C_RTIOC_GET_CONFIG:
			if (user_info)
				err =rtdm_safe_copy_to_user(user_info, arg,&ctx->config,sizeof(struct rti2c_config));
			else
				memcpy(arg, &ctx->config,sizeof(struct rti2c_config));
			break;

		case RTI2C_RTIOC_SET_CONFIG:
		{
			struct rti2c_config *config;
			struct rti2c_config config_buf;

			config = (struct rti2c_config *)arg;

			if (user_info)
			{
				err = rtdm_safe_copy_from_user(user_info, &config_buf,arg,sizeof(struct rti2c_config));

				if (err) return err;

				config = &config_buf;
			}

			rt_i2c_set_config(ctx, config);

			break;
		}
		case RTI2C_RTIOC_SET_SLAVE:
		{
			unsigned short* address;

			rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

			address=(unsigned short*)arg;
			writew(*address,(void *)base + I2C_SA);

			rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

			err=0;
			break;
		}

		default:
		err = -ENOTTY;
	}

	return err;
}

/**
 * Read from the device
 *
 * This function is called when the device is read in realtime context.
 *
 */
static ssize_t i2c_read_rt(struct rtdm_dev_context *context,
				    rtdm_user_info_t * user_info, void *buf,
				    size_t nbyte)
{
	int val,i;

	struct rt_i2c_context *ctx;
	rtdm_lockctx_t lock_ctx;
	size_t read = 0;
	char *out_pos = (char *)buf;
	rtdm_toseq_t timeout_seq;
	ssize_t ret = -EAGAIN;	/* for non-blocking read */

    //rtdm_printk("i2c_read %i\n",nbyte);

	if (nbyte == 0)
		return 0;

	if (nbyte >8)
	{
		rtdm_printk("i2c_write taille superieure a fifo %i\n",nbyte);
		return -ENOSPC;
	}

	if (user_info && !rtdm_rw_user_ok(user_info, buf, nbyte)) return -EFAULT;

	ctx = (struct rt_i2c_context *)context->dev_private;
    ctx->status = 0;
for(i=0;i<8;i++)  ctx->data[i]=203;
    val=readw(ctx->base_addr + I2C_STAT);
    if(testbits(val,BB))
    {
        rtdm_printk("read bus not free\n");
        return -1;
    }

	rtdm_toseq_init(&timeout_seq, ctx->config.rx_timeout);

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	//write count
	writew(nbyte,ctx->base_addr + I2C_CNT);

	//enable, master
	val=readw(ctx->base_addr + I2C_CON);
	val=val&(~TRX);//clear trx
	writew(val|I2C_EN|MST|STT|STP,ctx->base_addr+ I2C_CON);

	while (1)
	{
		if (ctx->status)
		{
			if (testbits(ctx->status, BF))
			{
				if (testbits(ctx->status, NACK))
				{
					rtdm_printk("read nack\n");
					ret=-ENXIO;
				}

				if (testbits(ctx->status, ARDY))
				{
					//rtdm_printk("read ack\n");
					for(i=0;i<nbyte;i++) out_pos[i]=ctx->data[nbyte-i-1];
					read=nbyte;
				}
				ctx->status = 0;
				break;
			}
		}

		rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

		ret = rtdm_event_timedwait(&ctx->event,ctx->config.rx_timeout,&timeout_seq);

		if (ret < 0)
		{
			if (ret == -EIDRM)
			{
				/* Device has been closed -
				   return immediately. */

				return -EBADF;
			}

			rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

			rtdm_printk("i2c read timeout\n");
            writew(RXFIFO_CLR,ctx->base_addr + I2C_BUF);
            writew(0x7FFFF,ctx->base_addr + I2C_STAT);
            val=readw(ctx->base_addr + I2C_STAT);
            //rtdm_printk("I2C_STAT %x\n",val);

			break;
		}

		rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);
	}

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	if (read > 0) ret = read;
//rtdm_printk("fin i2c_read %i\n",nbyte);
	return ret;
}

/**
 * Write in the device
 *
 * This function is called when the device is written in realtime context.
 *
 */
ssize_t i2c_write_rt(struct rtdm_dev_context *context,
				     rtdm_user_info_t * user_info,
				     const void *buf, size_t nbyte)
{
	struct rt_i2c_context *ctx;
	rtdm_lockctx_t lock_ctx;
	size_t written = 0;
	int val,i;
	char *in_pos = (char *)buf;
	rtdm_toseq_t timeout_seq;
	ssize_t ret;

    //rtdm_printk("i2c_write %i\n",nbyte);

	if (nbyte == 0)
	{
		rtdm_printk("i2c_write %i\n",nbyte);
		return 0;
	}

	if (nbyte >8)
	{
		rtdm_printk("i2c_write taille superieure a fifo %i\n",nbyte);
		return -ENOSPC;
	}

	if (user_info && !rtdm_read_user_ok(user_info, buf, nbyte))
	{
        rtdm_printk("efault\n");
		return -EFAULT;
	}
	ctx = (struct rt_i2c_context *)context->dev_private;
    ctx->errors = 0;
    ctx->status = 0;

    val=readw(ctx->base_addr + I2C_STAT);
    //rtdm_printk("I2C_STAT %i\n",val);
    if(testbits(val,BB))
    {
        rtdm_printk("write bus not free\n");
        return -1;
    }
    writew(0x7fff,ctx->base_addr+ I2C_STAT);

	rtdm_toseq_init(&timeout_seq, ctx->config.tx_timeout);

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	//count
	writew(nbyte,ctx->base_addr + I2C_CNT);
	//put datas in fifo
	for(i=0;i<nbyte;i++)
	{
		 writeb(in_pos[i],ctx->base_addr + I2C_DATA);
	}

	//enable, master, trx
	writew(I2C_EN|MST|TRX|STT|STP,ctx->base_addr+ I2C_CON);

	while (1)
    {
		if (ctx->status)
        {
			if (testbits(ctx->status, BF))
			{
				if (testbits(ctx->status,ARDY))
				{

					if (testbits(ctx->errors,NACK))
					{
						//rtdm_printk("write nack\n");
						//rtdm_printk("I2C_SA %x\n",readw(ctx->base_addr + I2C_SA));
						written=0;
						ret=-ENXIO;
						ctx->errors = 0;
					}
					else
					{
						written=nbyte;
					}
				}
				ctx->status = 0;
				break;
			}
		}


		rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);
		ret = rtdm_event_timedwait(&ctx->event,ctx->config.tx_timeout,&timeout_seq);

		if (ret < 0)
		{
			if (ret == -EIDRM)
			{
				/* Device has been closed -
				   return immediately. */
                rtdm_printk("ebadf\n");
				return -EBADF;
			}
            //rtdm_printk("i2c write timeout\n");
            val=readw(ctx->base_addr + I2C_STAT);
            //rtdm_printk("I2C_STAT %x\n",val);
            writew(TXFIFO_CLR,ctx->base_addr + I2C_BUF);

            writew(0x7FFFF,ctx->base_addr + I2C_STAT);
            val=readw(ctx->base_addr + I2C_STAT);
            //rtdm_printk("I2C_STAT %x\n",val);
			rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

			break;
		}

		rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);
	}

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	if (written > 0)
	{
		ret = written;
	}
    	if(ret==0) rtdm_printk("0\n");

	return ret;

}

/**
 * This structure describe the simple RTDM device
 *
 */
static const struct rtdm_device __initdata device_tmpl = {
	.struct_version = RTDM_DEVICE_STRUCT_VER,

	.device_flags = RTDM_NAMED_DEVICE,
	.context_size = sizeof(struct rt_i2c_context),
	.device_name = DEVICE_NAME,

	.open_nrt = i2c_open,
	//.open_rt  = i2c_open,

	.ops = {
		.close_nrt = i2c_close,
		//.close_rt  = i2c_close,
		.read_rt   = i2c_read_rt,
		.write_rt  = i2c_write_rt,
		.ioctl_rt  = i2c_ioctl,
		.ioctl_nrt  = i2c_ioctl,
	},

	.device_class = RTDM_CLASS_EXPERIMENTAL,
	.device_sub_class = 0,
	.profile_version = 1,
	.driver_name = DEVICE_NAME,
	.driver_version = RTDM_DRIVER_VER(0, 1, 2),
	.peripheral_name = DEVICE_NAME,
	.provider_name = "Guillaume Sanahuja",
};

void i2c_exit(void);

/**
 * This function is called when the module is loaded
 *
 * It simply registers the RTDM device.
 *
 */
int __init i2c_init(void)
{
	struct rtdm_device *dev;
	unsigned long base;
	int err=0;
	int i;
	int val;

	for (i = 0; i < MAX_DEVICES; i++)
	{
		if (port_num[i]==0 || port_num[i]>MAX_DEVICES)
			continue;

        rtdm_printk("i2c_init %i\n",port_num[i]);

		dev = kmalloc(sizeof(struct rtdm_device), GFP_KERNEL);
		err = -ENOMEM;
		if (!dev) goto cleanup_out;

		memcpy(dev, &device_tmpl, sizeof(struct rtdm_device));
		snprintf(dev->device_name, RTDM_MAX_DEVNAME_LEN, "rti2c%d",port_num[i]);
		dev->device_id = port_num[i]-1;

		dev->proc_name = dev->device_name;

		err = rt_i2c_init_io(dev->device_id, dev->device_name);
		if (err) goto kfree_out;

		err = rtdm_dev_register(dev);

		if (err) goto release_io_out;

		device[dev->device_id] = dev;

        //enable
        if(port_num[i]==1)
		{
			base=(unsigned long)ioremap(CORE_CM, 8);
			val=readl((void *)base + CM_FCLKEN1_CORE);
			writel(val|EN_I2C1,(void *)base + CM_FCLKEN1_CORE);
			val=readl((void *)base + CM_ICLKEN1_CORE);
			writel(val|EN_I2C1,(void *)base + CM_ICLKEN1_CORE);
		}
		if(port_num[i]==2)
		{
			base=(unsigned long)ioremap(CORE_CM, 8);
			val=readl((void *)base + CM_FCLKEN1_CORE);
			writel(val|EN_I2C2,(void *)base + CM_FCLKEN1_CORE);
			val=readl((void *)base + CM_ICLKEN1_CORE);
			writel(val|EN_I2C2,(void *)base + CM_ICLKEN1_CORE);
		}
		if(port_num[i]==3)
		{
			base=(unsigned long)ioremap(CORE_CM, 8);
			val=readl((void *)base + CM_FCLKEN1_CORE);
			writel(val|EN_I2C3,(void *)base + CM_FCLKEN1_CORE);
			val=readl((void *)base + CM_ICLKEN1_CORE);
			writel(val|EN_I2C3,(void *)base + CM_ICLKEN1_CORE);
		}

		//reset
		base=(unsigned long)mapped_io[dev->device_id];
		val=readw((void *)base + I2C_CON);
		writew(val&(~I2C_EN),(void *)base + I2C_CON);

		writew(SRST,(void *)base + I2C_SYSC);
		writew(I2C_EN,(void *)base + I2C_CON);

		val=readw((void *)base + I2C_SYSS);
		while((val&RDONE)==0)
		{
			//rtdm_task_sleep(1000*1000*1000);
			val=readw((void *)base + I2C_SYSS);
			rtdm_printk("I2C_SYSS %x\n",val);
		}
		rtdm_printk("reset ok\n");

		//prescaler
		writeb(0x07,(void *)base + I2C_PSC);

		/* Mask all I2C interrupts and clear pending ones. */
		//ie
		writew(0,(void *)base + I2C_IE);

		//stat
		writew(0x7fff,(void *)base + I2C_STAT);

	}
    rtdm_printk("fin i2c_init\n");

	return 0;

release_io_out:
	rt_i2c_release_io(dev->device_id);
kfree_out:
	kfree(dev);

cleanup_out:
	i2c_exit();
    rtdm_printk("erreur i2c_init\n");

	return err;
}

/**
 * This function is called when the module is unloaded
 *
 * It unregister the RTDM device, polling at 1000 ms for pending users.
 *
 */
void __exit i2c_exit(void)
{
	int i;
    rtdm_printk("i2c_exit\n");

	for (i = 0; i < MAX_DEVICES; i++)
		if (device[i])
		{
			rtdm_dev_unregister(device[i], 1000);
			rt_i2c_release_io(i);
			kfree(device[i]);
		}
}


module_init(i2c_init);
module_exit(i2c_exit);
