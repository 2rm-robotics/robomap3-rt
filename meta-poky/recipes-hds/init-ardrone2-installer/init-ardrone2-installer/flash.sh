#!/bin/sh

ROOTFS=core-image-base-ardrone2.tar.bz2
KERNEL=zImage.plf
TTY=/dev/ttyGS0

function receive ()
{
	echo receiving $1

	HEXSIZE=$(dd if=$TTY iflag=count_bytes count=10 status=none)
	SIZE=$(printf "%i\n" $HEXSIZE)
	echo size: $SIZE

	MD5=$(dd if=$TTY iflag=count_bytes count=32 status=none)

	dd if=$TTY of=$1 iflag=count_bytes,fullblock count=$SIZE
	echo md5: $MD5

	if [ "$MD5" = "$(md5sum < $1 | head -c -4)" ]; then
		echo md5 ok
	else
		echo md5 ko
		exit 1
	fi
}

receive $ROOTFS
receive $KERNEL

#a revoir, ne pas formater le /home!!
echo partitionning, formatting and updating rootfs
ubiformat /dev/mtd3 > /dev/null
ubiattach -p /dev/mtd3
ubimkvol /dev/ubi0 --type=dynamic -N "rootfs" -n 0 --size=60MiB
ubimkvol /dev/ubi0 --type=dynamic -N "home" -n 1 -m
mkdir rootfs
mount -t ubifs /dev/ubi0_0 rootfs -o compr=none
cd rootfs
tar -xjf ../$ROOTFS
cd ..
umount rootfs
mkdir home
mount -t ubifs /dev/ubi0_1 home -o compr=none
mkdir home/root
umount home
ubidetach -p /dev/mtd3

echo updating kernel
ubiattach -p /dev/mtd1
ubiupdatevol /dev/ubi0_0 $KERNEL
ubidetach -p /dev/mtd1

