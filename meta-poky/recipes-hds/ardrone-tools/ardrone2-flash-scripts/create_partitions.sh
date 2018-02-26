#!/bin/sh
MTD_NUMBER=3
MTD_PART=/dev/mtd$MTD_NUMBER

source /usr/bin/updater_functions.sh

echo "creating 2 partitions in $MTD_PART"

ubiformat $MTD_PART > /dev/null
attach_mtd $MTD_NUMBER
ubimkvol ${UBI_PART} --type=dynamic -N "rootfs" -n 0 --size=60MiB
ubimkvol ${UBI_PART} --type=dynamic -N "home" -n 1 -m

mkdir /tmp/home
mount -t ubifs ${UBI_PART}_1 /tmp/home -o compr=none
mkdir /tmp/home/root
umount /tmp/home
rmdir /tmp/home

ubidetach -p $MTD_PART > /dev/null
echo done
