//  created:    2014/08/28
//  filename:   main.c
//
//  author:     Guillaume Sanahuja
//              Copyright Heudiasyc UMR UTC/CNRS 7253
//
//  version:    $Id: $
//
//  purpose:    battery voltage reading via madc for ardrone2
//
//
/*********************************************************************/

#include <stdio.h>
#include <stdint.h>
#include <fcntl.h>   /* File control definitions */
#include <sys/ioctl.h>
#include <linux/i2c-dev.h>

//0x49 slave bank
#define GPBR1 0x91

//0x4a slave bank
#define CTRL1 0x00
#define SW1SELECT_LSB 0x06
#define SW1AVERAGE_LSB 0x08
#define CTRL_SW1 0x12
#define GPCH0_LSB 0x37
#define GPCH0_MSB 0x38

int fd;

int SetSlave(uint16_t address)
{
    int err=ioctl( fd, I2C_SLAVE_FORCE, address);
	if( err < 0 )
    {
        printf("Failed to set slave address\n");
    }

	return err;
}

ssize_t Write(const void *buf,size_t nbyte)
{
    return write(fd,buf, nbyte);
}

ssize_t Read(void *buf,size_t nbyte)
{
    return read(fd,buf, nbyte);
}

int main(int argc, char* argv[])
{
	unsigned char lsb, msb;
	int raw_voltage;
	uint8_t tx[2];
	uint8_t rx;

	fd = open( "/dev/i2c-1", O_RDWR );
	if (fd == -1)
	{
		printf("open_port: Unable to open i2c-1\n");
		return 1;
	}

	//slave 0x49 for clocks
	SetSlave(0x49);

	//GPBR1: set MADC_HFCLK_EN and DEFAULT_MADC_CLK_EN
	tx[0]=GPBR1;
	tx[1]=0x90;
	Write(tx,2);

	//slave 0x4a for madc
	SetSlave(0x4a);

	// Turn on MADC in CTRL1
	tx[0]=CTRL1;
	tx[1]=0x01;
	Write(tx,2);

	// Select ADCIN0 for conversion in SW1SELECT_LSB
	tx[0]=SW1SELECT_LSB;
	tx[1]=0x01;
	Write(tx,2);

	// Setup SW1AVERAGE_LSB register for averaging
	tx[0]=SW1AVERAGE_LSB;
	tx[1]=0x01;
	Write(tx,2);

	// Start all channel conversion by setting bit 5 to one in CTRL_SW1
        tx[0]=CTRL_SW1;
        tx[1]=0x20;
        Write(tx,2);

        usleep(100000);

        //check end of conversion in CTRL_SW1
        Write(tx,1);
        Read(&rx,1);
        if((rx&0x02)==0x02)
        {
		tx[0]=GPCH0_LSB;
		Write(tx,1);
		Read(&lsb,1);

		tx[0]=GPCH0_MSB;
		Write(tx,1);
		Read(&msb,1);

		raw_voltage = (lsb >> 6) | (msb << 2);

		//From paparazzi:
		// we know from spec sheet that ADCIN0 has no prescaler
		// so that the max voltage range is 1.5 volt
		// multiply by ten to get decivolts

		//from raw measurement we got quite a lineair response
		//9.0V=662, 9.5V=698, 10.0V=737,10.5V=774, 11.0V=811, 11.5V=848, 12.0V=886, 12.5V=923
		//leading to our 0.13595166 magic number for decivolts conversion
            
		printf("%.1fV\n",raw_voltage*0.013595166);
        }

    	return 0;

}
