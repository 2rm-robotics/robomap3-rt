//  created:    2013/05/30
//  filename:   main.c
//
//  author:     Guillaume Sanahuja
//              Copyright Heudiasyc UMR UTC/CNRS 7253
//
//  version:    $Id: $
//
//  purpose:    programme affichant la tension batterie, via les moteurs i2c
//
//
/*********************************************************************/

#include <stdio.h>
#include <rtdm/rti2c.h>
#include <rtdk.h>
#include <sys/mman.h>


int main(int argc, char* argv[])
{
	int err=0;
	int i2c_fd;
    struct rti2c_config write_config;

    // Avoids memory swapping for this program
    mlockall(MCL_CURRENT|MCL_FUTURE);

    rt_task_shadow(NULL,"voltage",1,0);
    i2c_fd = rt_dev_open("rti2c3", 0);
    if (i2c_fd < 0)
	{
		printf("erreur rt_dev_open (%s)\n",strerror(-i2c_fd));
	}

	write_config.config_mask       = RTI2C_SET_BAUD|RTI2C_SET_TIMEOUT_RX|RTI2C_SET_TIMEOUT_TX;
	write_config.baud_rate         = 400000;
	write_config.rx_timeout        = 100000000;
	write_config.tx_timeout        = 10000000;
	// the rest implicitely remains default

	// config
	err = rt_dev_ioctl(i2c_fd, RTI2C_RTIOC_SET_CONFIG, &write_config );
	if (err)
	{
	    printf("erreur rt_dev_ioctl RTI2C_RTIOC_SET_CONFIG (%s)\n",strerror(-err));
	}

    //slave
    uint8_t address=0x29;
    err = rt_dev_ioctl(i2c_fd, RTI2C_RTIOC_SET_SLAVE, &address);
    if (err)
	{
	    printf("erreur rt_dev_ioctl RTI2C_RTIOC_SET_SLAVE (%s)\n",strerror(-err));
	}

	//envoie 0
	unsigned char tx[2];
    ssize_t written;

    tx[0]=0;//msb
    tx[1]=16+8+(0&0x07);//16+8 pour recuperer la vitesse
    written = rt_dev_write(i2c_fd, &tx, 2);
    if(written<0)
    {
        printf("erreur rt_dev_write (%s)\n",strerror(-written));
    }
    else if (written != 2)
    {
        printf("erreur rt_dev_write %i/2\n",written);
    }

    //tension
    ssize_t read;
    uint8_t value[3];
    read = rt_dev_read(i2c_fd, value, 3);

    if(read<0)
    {
        printf("erreur rt_dev_read (%s)\n",strerror(-read));
    }
    else if (read != sizeof(value))
    {
        printf("erreur rt_dev_read %i/2\n",read);
    }
    else
    {
        printf("%.1fV\n",value[2]/10.);
    }
    return 0;

}
