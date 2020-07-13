/*

tool to prepare an ardrone2 for flashing with custom kernel and rootfs
this program do the following:

1 send a bootloader (inst_usb_bootldr.bin, binary from Parrot) using omap3 usb boot
2 send a plf to RAM (kernel+rootfs) using Parrot USB bootloader
3 run the kernel and the rootfs

This code is an adaptation from omap3_usbload.c 
https://github.com/gtvhacker/NestDFUAttack
and from usb_flash.c:
https://github.com/scorp2kk/ardrone-tool

Guillaume Sanahuja (gsanahuj@hds.utc.fr)

*/

/*  $Id:  Exp $
 *
 * omap3_usbload, an USB download application for OMAP3 processors
 * Copyright (C) 2008 Martin Mueller <martinmm@pfump.org>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with paparazzi; see the file COPYING.  If not, write to
 * the Free Software Foundation, 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.  
 *
 */

/*
 * plf_inst_extract.c
 *
 * Copyright (c) 2011 scorp2kk, All rights reserved
 *
 * Description:
 *  This program flashes a new firmware via USB to the drone.
 *
 * License:
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


#include <stdio.h>
#include <fcntl.h>
#include <usb.h>
#include <stdint.h>
#include <stdbool.h>

#define OMAP3_VENDOR_ID     0x0451
#define OMAP3_DEVICE_ID     0xD000
#define OMAP3_DEVICE_ID_MASK      0xff00

#define PARROT_VENDOR_ID    0x19CF
#define PARROT_PRODUCT_ID   0x1111

#define OMAP3_BOOT_CMD     0xF0030002  /* see page 3515 sprugn4c.pdf */

#define EP_BULK_IN         0x81
#define EP_BULK_OUT        0x01
#define BUF_LEN             128

#define USB_TIMEOUT 1000  /* time in ms */


usb_dev_handle* usb_connect(uint16_t idVendor, uint16_t idProduct,uint16_t idProductMask) {
    struct usb_bus *bus;
    usb_dev_handle *udev=NULL;

    printf("Trying to connect to VID: 0x%04x PID: 0x%04x ", idVendor, idProduct);

	while (1) {
		printf(".");
		fflush(stdout);

		if (usb_find_devices()) {
			for (bus = usb_get_busses(); bus; bus = bus->next) {
				struct usb_device *dev;

				for (dev = bus->devices; dev; dev = dev->next) {

					if (dev->descriptor.idVendor == idVendor &&
						(dev->descriptor.idProduct & idProductMask) == idProduct) {
						udev = usb_open(dev);

						if (udev) {
                            char buffer[256];
							printf("\nFound device:\n");
                            usb_get_string_simple(udev, dev->descriptor.iManufacturer, buffer, sizeof(buffer));
        					printf("- Manufacturer: %s\n", buffer);
           					usb_get_string_simple(udev, dev->descriptor.iProduct, buffer, sizeof(buffer));
			        		printf("- Product: %s\n", buffer);
							usb_claim_interface(udev,0);
                            return udev;

						}
				    }
				}
			}
		}
		usleep(500 * 1000); /* wait until we put another dot */
	}
}

int send_file(usb_dev_handle* udev, const char* filename,bool send_size) {
    int file_cnt, usb_cnt,fd,filesize;
	int dat_buf[0x10000];

    fd = open(filename, O_RDONLY);
    if (fd < 0) {
        perror("Can't open file");
        return -1;
    }
    filesize = lseek (fd, 0, SEEK_END);
    if (filesize < 0  ||  lseek (fd, 0, SEEK_SET) < 0) {
        perror ("lseek binfile");
        close (fd);
        return -1;
    }
    printf("Uploading %s, size = %d\n", filename, filesize);

    if(send_size) {
	    if (sizeof(filesize) != usb_bulk_write(udev, EP_BULK_OUT, (char*) &filesize, sizeof(filesize), USB_TIMEOUT)) {
		    printf("could not send length\n");
            close (fd);
		    return(-1);
	    }
    }

	do {

		file_cnt = read(fd, dat_buf, sizeof (dat_buf));

		if (file_cnt > 0) {
			usb_cnt = usb_bulk_write(udev, EP_BULK_OUT, (char *) dat_buf, file_cnt, USB_TIMEOUT);            

			if (usb_cnt != file_cnt) {
				printf("could not write to usb usb_cnt = %d file_cnt = %d\n", usb_cnt, file_cnt );
                close (fd);
				return(-1);
			}
		}
	} while (file_cnt > 0);

	printf("Upload ok!\n");
	close(fd);
    return 0;
}

int send_usb_bootloader(usb_dev_handle *udev,const char *filename) {
	int dat_buf[BUF_LEN];
	unsigned int command;

	if (usb_bulk_read(udev, EP_BULK_IN, (char *) dat_buf, sizeof (dat_buf), USB_TIMEOUT) <= 0) {
		printf("could not get ASIC ID\n");
    	return(-1);
	}

	command = OMAP3_BOOT_CMD;
	if (sizeof(command) != usb_bulk_write(udev, EP_BULK_OUT, (char*) &command, sizeof(command), USB_TIMEOUT)) {
		printf("could not send peripheral boot command\n");
		return(-1);
	}

    return send_file(udev,filename,true);
}

int send_payload(usb_dev_handle *udev,const char *filename) {
	unsigned int resp;
    int ret;

    ret=send_file(udev,filename,false);
    if(ret<0) return ret;
	
    if (sizeof(resp) !=usb_bulk_read(udev, 0x81, (char*)(&resp), sizeof(resp), USB_TIMEOUT)) {
        printf("could not get return code\n");
    	return(-1);
    }

	if (resp == 0) {
		return 0;
	}
	else {
		printf("get error code %x\n",resp);
		return -1;
	}
}

int main(int argc, char **argv) {
	int ret;
    usb_dev_handle *udev;

	if (argc != 3) {
		fprintf (stderr, "Usage: ardrone2_usbload inst_usb_bootldr.bin payload.plf\n");
		return(-1);
	}

	usb_init();
	usb_find_busses();

    printf("* Bootloader *\n");
    udev=usb_connect(OMAP3_VENDOR_ID,OMAP3_DEVICE_ID, OMAP3_DEVICE_ID_MASK);
    ret=send_usb_bootloader(udev,argv[1]);
    usb_close(udev);
    if(ret!=0) return ret;
    
    printf("\n");
    printf("* Payload *\n");
    udev=usb_connect(PARROT_VENDOR_ID,PARROT_PRODUCT_ID, 0xffff);
    ret=send_payload(udev,argv[2]);
    usb_close(udev);
    return ret;	
}
