#include <linux/module.h>
#include <rtdm/rtdm_driver.h>


#include "capture_omap.h"
#include "rtradio.h"

struct rt_capture_context {
	unsigned int values[NB_CHANNELS];//channels values
	rtdm_irq_t irq_handle;		/* device IRQ handle */
	rtdm_lock_t lock;		/* lock to protect context struct */
	unsigned int base_addr;	/* hardware IO base address */
	
};


static struct rtdm_device *device;

static unsigned int irq;
static unsigned int irqtype = RTDM_IRQTYPE_SHARED | RTDM_IRQTYPE_EDGE;

static unsigned int mem;
static void *mapped_io;

static signed char index;
static unsigned int start_time;

static unsigned int port_num[1];

compat_module_param_array(port_num, uint,1, 0400);
MODULE_PARM_DESC(port_num, "capture port number (8,9,10,11)");

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Guillaume Sanahuja");

#define DEVICE_NAME			"rt_capture"

static int rt_capture_init_io(void)
{
	mapped_io = ioremap(mem, 8);
	if (!mapped_io)
		return -EBUSY;

	return 0;
}

static void rt_capture_release_io(void)
{
	iounmap(mapped_io);
}


static int rt_capture_interrupt(rtdm_irq_t * irq_context)
{
	struct rt_capture_context *ctx;
	unsigned int base;
	int ret = RTDM_IRQ_NONE;
	unsigned int val;

	ctx = rtdm_irq_get_arg(irq_context, struct rt_capture_context);
	base = ctx->base_addr;

	rtdm_lock_get(&ctx->lock);

	val=readl((void *)base + TISR);
	//rtdm_printk("interrupt TISR %x\n",val);

	if(index<0) writel(GPO_CFG|CAPT_MODE|TCM_BOTH|AR|ST ,ctx->base_addr + TCLR);//init

	if(testbits(val,TCAR_IT_FLAG))
	{
		unsigned int val1,diff;
		//clear flag
		writel(TCAR_IT_FLAG,(void *)base + TISR);
		val1=readl((void *)base + TCAR1);
		diff=val1-start_time;
		start_time=readl((void *)base + TCAR2);
		
		if(index>=0 && index<NB_CHANNELS) ctx->values[index]=diff;
		if(index>=0) index++;
		if(diff>100000) index=0;
//		rtdm_printk("delta %d\n",diff);
	}

	if(testbits(val,OVF_IT_FLAG))
	{
		rtdm_printk("overflow\n");
		//clear flag
		writew(OVF_IT_FLAG,(void *)base + TISR);
		val=readw((void *)base + TISR);
	}

	rtdm_lock_put(&ctx->lock);
	ret = RTDM_IRQ_HANDLED;
	//rtdm_printk("fin interrupt TISR %x %i\n",val,cpt);
	return ret;
}

/**
 * Open the device
 *
 * This function is called when the device shall be opened.
 *
 */
static int capture_open(struct rtdm_dev_context *context,
				rtdm_user_info_t * user_info, int oflags)
{
	struct rt_capture_context *ctx;
	int err;
	rtdm_lockctx_t lock_ctx;

	ctx = (struct rt_capture_context *)context->dev_private;
    	rtdm_printk("capture_open\n");

	/* IPC initialisation - cannot fail with used parameters */
	rtdm_lock_init(&ctx->lock);

	ctx->base_addr = (unsigned int)mapped_io;

	err = rtdm_irq_request(&ctx->irq_handle, irq,
			       rt_capture_interrupt, irqtype,
			       context->device->proc_name, ctx);
	index=-1;

	if (err)
	{
		rtdm_printk("rtdm_irq_request err\n");
		return err;
	}

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	//input capture, first and second, falling, auto reload, start
	writel(GPO_CFG|CAPT_MODE|TCM_FALLING|AR|ST ,ctx->base_addr + TCLR);

	/* enable interrupts */
	writew(TCAR_IT_ENA|OVF_IT_ENA,ctx->base_addr + TIER);

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	rtdm_printk("fin capture_open\n");

	return 0;
}

/**
 * Close the device
 *
 * This function is called when the device shall be closed.
 *
 */
static int capture_close(struct rtdm_dev_context *context,
				rtdm_user_info_t * user_info)
{
	struct rt_capture_context *ctx;
	unsigned int base;

	rtdm_lockctx_t lock_ctx;
    	rtdm_printk("capture_close\n");
	ctx = (struct rt_capture_context *)context->dev_private;
	base = ctx->base_addr;

	rtdm_lock_get_irqsave(&ctx->lock, lock_ctx);

	/* mask all I2C interrupts and clear pending ones. */
	writel(0,base + TIER);
	writel(TCAR_IT_FLAG|OVF_IT_FLAG,base + TISR);

	rtdm_lock_put_irqrestore(&ctx->lock, lock_ctx);

	rtdm_irq_free(&ctx->irq_handle);

	return 0;
}

int capture_ioctl(struct rtdm_dev_context *context,
		   rtdm_user_info_t * user_info,
		   unsigned int request, void *arg)
{
	struct rt_capture_context *ctx;
	int err = 0;
	
	ctx = (struct rt_capture_context *)context->dev_private;

	switch (request)
	{
		case RTRADIO_RTIOC_GET_CHANNELS:
			rtdm_lock_get(&ctx->lock);
			if (user_info)
			{
				err =rtdm_safe_copy_to_user(user_info, arg,&ctx->values,NB_CHANNELS*sizeof(unsigned int));
			}
			else
			{
				memcpy(arg, &ctx->values,NB_CHANNELS*sizeof(unsigned int));
			}
			rtdm_lock_put(&ctx->lock);
			break;

		default:
		err = -ENOTTY;
	}

	return err;
}

/**
 * This structure describe the simple RTDM device
 *
 */
static const struct rtdm_device __initdata device_tmpl = {
	.struct_version = RTDM_DEVICE_STRUCT_VER,

	.device_flags = RTDM_NAMED_DEVICE,
	.context_size = sizeof(struct rt_capture_context),
	.device_name = DEVICE_NAME,

	.open_nrt = capture_open,

	.ops = {
		.close_nrt = capture_close,
		.ioctl_rt  = capture_ioctl,
		.ioctl_nrt  = capture_ioctl,
	},

	.device_class = RTDM_CLASS_EXPERIMENTAL,
	.device_sub_class = 0,
	.profile_version = 1,
	.driver_name = DEVICE_NAME,
	.driver_version = RTDM_DRIVER_VER(0, 1, 2),
	.peripheral_name = DEVICE_NAME,
	.provider_name = "Guillaume Sanahuja",
};

void capture_exit(void);

/**
 * This function is called when the module is loaded
 *
 * It simply registers the RTDM device.
 *
 */
int __init capture_init(void)
{
	struct rtdm_device *dev;
	unsigned int base;
	int err=0;
	unsigned int val;

        rtdm_printk("capture_init\n");

	dev = kmalloc(sizeof(struct rtdm_device), GFP_KERNEL);
	err = -ENOMEM;
	if (!dev) goto cleanup_out;

	memcpy(dev, &device_tmpl, sizeof(struct rtdm_device));
	snprintf(dev->device_name, RTDM_MAX_DEVNAME_LEN, "rtcapture");
	dev->device_id = 0;

	dev->proc_name = dev->device_name;

	err = rtdm_dev_register(dev);

	if (err) goto release_io_out;

	device = dev;

	if(port_num[0]==8)
	{
	}
	
	if(port_num[0]==9)
	{
	}

	if(port_num[0]==10)
	{
		irq=GPT10_IRQ;
		mem=GPT10_MEM;
		//enable gpt10 clocks
		base=(unsigned int)ioremap(CORE_CM, 8);
		val=readl((void *)base + CM_FCLKEN1_CORE);
		writel(val|EN_GPT10,(void *)base + CM_FCLKEN1_CORE);
		val=readl((void *)base + CM_ICLKEN1_CORE);
		writel(val|EN_GPT10,(void *)base + CM_ICLKEN1_CORE);
		rtdm_printk("capture_init 1\n");//faire une attente, sinon segfault, le temps que tout soit allumé?
	
		//enable sys clock
		val=readl((void *)base + CM_CLKSEL_CORE);
		writel(val|CLKSEL_GPT10,(void *)base +CM_CLKSEL_CORE);
	}

	if(port_num[0]==11)
	{
		irq=GPT11_IRQ;
		mem=GPT11_MEM;
		//enable gpt11 clocks
		base=(unsigned int)ioremap(CORE_CM, 8);
		val=readl((void *)base + CM_FCLKEN1_CORE);
		writel(val|EN_GPT11,(void *)base + CM_FCLKEN1_CORE);
		val=readl((void *)base + CM_ICLKEN1_CORE);
		writel(val|EN_GPT11,(void *)base + CM_ICLKEN1_CORE);
		rtdm_printk("capture_init 1\n");//faire une attente, sinon segfault, le temps que tout soit allumé?
	
		//enable sys clock
		val=readl((void *)base + CM_CLKSEL_CORE);
		writel(val|CLKSEL_GPT11,(void *)base +CM_CLKSEL_CORE);
	}

	err = rt_capture_init_io();
	if (err) goto kfree_out;

	//reset
	base=(unsigned int)mapped_io;
	val=readw((void *)base + TIOCP_CFG);
	writew(val|SOFTRESET,(void *)base + TIOCP_CFG);

	val=readw((void *)base + TISTAT);
	while((val&RESETDONE)==0)
	{
		rtdm_printk("TISTAT %x\n",val);
		val=readw((void *)base + TISTAT);
	}
	rtdm_printk("reset ok\n");
	
	//non posted
	writew(0,(void *)base + TSICR);

	/* Mask all timer interrupts and clear pending ones. */
	//ie
	writew(0,(void *)base + TIER);

	//stat
	writew(TCAR_IT_FLAG|OVF_IT_FLAG|MAT_IT_FLAG,(void *)base + TSICR);

    	rtdm_printk("fin capture_init\n");

	return 0;

release_io_out:
	rt_capture_release_io();

kfree_out:
	kfree(dev);

cleanup_out:
	capture_exit();
    	rtdm_printk("erreur capture_init\n");

	return err;
}

/**
 * This function is called when the module is unloaded
 *
 * It unregister the RTDM device, polling at 1000 ms for pending users.
 *
 */
void __exit capture_exit(void)
{
    	rtdm_printk("capture_exit\n");

	if (device)
	{
		rtdm_dev_unregister(device, 1000);
		rt_capture_release_io();
		kfree(device);
	}
}


module_init(capture_init);
module_exit(capture_exit);
