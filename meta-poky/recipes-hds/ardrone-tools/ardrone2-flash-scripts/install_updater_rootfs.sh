#!/bin/sh
MTD_NUMBER=2
MTD_PART=/dev/mtd$MTD_NUMBER
UBI_NAME=factory

source /usr/bin/updater_functions.sh

#basic checks
if [ "$1" = "" ]; then
	echo "usage: install_updater_rootfs.sh rootfs.tar.bz2"
	exit 1
fi

if [ ! -e $1 ]; then
	echo file $1 does not exist
	exit 1
fi

attach_mtd $MTD_NUMBER

UBI_SUBPART=${UBI_PART}_0

#umount /factory if necessary
if [[ "/dev/"$(mount | grep "on /factory type ubifs" | cut -d':' -f1) = ${UBI_PART} ]]; then
    umount /factory  
fi

echo installing $1

NAME=$(ubinfo $UBI_SUBPART | grep Name |  sed 's/ \+/ /g' | cut -d ' ' -f2-)
ubidetach -p $MTD_PART > /dev/null

if [ "$NAME" = "$UBI_NAME" ]; then
	echo "found $UBI_NAME in $UBI_SUBPART, writing rootfs"
	ubiformat $MTD_PART > /dev/null
	ubiattach -p $MTD_PART > /dev/null
	ubimkvol $UBI_PART -N "$UBI_NAME" -m > /dev/null
	mkdir -p /tmp/$UBI_NAME
	mount -t ubifs $UBI_SUBPART /tmp/$UBI_NAME -o compr=lzo
    if [ "${1##*.}" = "gz" ]; then
        tar -xzf $1 -C /tmp/$UBI_NAME
     else
        tar -xjf $1 -C /tmp/$UBI_NAME
    fi
	umount /tmp/$UBI_NAME
    rmdir /tmp/$UBI_NAME
	ubidetach -p $MTD_PART > /dev/null
	echo done
else
	echo "$UBI_PART does not correspond to $UBI_NAME partition"
fi

