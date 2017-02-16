#!/bin/sh
MTD_NUMBER=3
MTD_PART=/dev/mtd$MTD_NUMBER
KERNEL_MTD_NUMBER=1
KERNEL_MTD_PART=/dev/mtd$KERNEL_MTD_NUMBER

source /usr/bin/updater_functions.sh

cd /tmp
attach_mtd $MTD_NUMBER
mkdir -p rootfs
mount -t ubifs ${UBI_PART}_0 rootfs -o compr=none
mkdir -p home
mount -t ubifs ${UBI_PART}_1 home -o compr=none

check_rootfs_and_kernel_presence /tmp/home/root /tmp/rootfs

echo installing $KERNEL_FILE

update_kernel.sh $KERNEL_FILE

echo installing $ROOTFS_FILE

cp $ROOTFS_FILE /tmp
cd rootfs
rm -rf *
tar -xjf /tmp/*.tar.bz2
cd ..
umount rootfs
umount home
rmdir rootfs
rmdir home

ubidetach -p $MTD_PART

echo "rebooting to new system"
attach_mtd $KERNEL_MTD_NUMBER
#swap kernel labels (parrot bootloader boots on main_boot kernel)
ubirename $UBI_PART main_boot alt_boot alt_boot main_boot
ubidetach -p $KERNEL_MTD_PART

reboot
