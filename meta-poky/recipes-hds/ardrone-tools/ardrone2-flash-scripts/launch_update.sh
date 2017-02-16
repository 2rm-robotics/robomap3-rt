#!/bin/sh
KERNEL_MTD_NUMBER=1
KERNEL_MTD_PART=/dev/mtd$KERNEL_MTD_NUMBER
UPDATER_MTD_NUMBER=2
UPDATER_MTD_PART=/dev/mtd$UPDATER_MTD_NUMBER

source /usr/bin/updater_functions.sh

echo "*checking if new kernel and new rootfs are present"
check_rootfs_and_kernel_presence /home/root /

echo "*check if updater rootfs is present"
attach_mtd $UPDATER_MTD_NUMBER
mkdir -p /tmp/updater
UBI_SUBPART=${UBI_PART}_0
mount -t ubifs $UBI_SUBPART /tmp/updater
if [ ! -e /tmp/updater/usr/bin/update.sh ]; then
	echo "updater is not installed"
  umount /tmp/updater
  rmdir /tmp/updater
  ubidetach -p $UPDATER_MTD_PART
	exit 1
fi
umount /tmp/updater
rmdir /tmp/updater
ubidetach -p $UPDATER_MTD_PART

echo "*check if updater kernel is present"
attach_mtd $KERNEL_MTD_NUMBER
UBI_SUBPART=${UBI_PART}_1
KERNEL_TYPE=$(get_kernel_type $UBI_SUBPART)
if [ "$KERNEL_TYPE" = "updater" ]; then
  echo "found an updater kernel"
else
  ubidetach -p $KERNEL_MTD_PART
	echo "not a valid kernel"
	exit 1
fi

echo "*rebooting to updater"
#swap kernel labels (parrot bootloader boots on main_boot kernel)
ubirename $UBI_PART main_boot alt_boot alt_boot main_boot
ubidetach -p $KERNEL_MTD_PART

reboot
