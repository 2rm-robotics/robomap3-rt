#!/bin/sh
MTD_NUMBER=1
MTD_PART=/dev/mtd$MTD_NUMBER

source /usr/bin/updater_functions.sh

#basic checks
if [ "$1" = "" ]; then
        echo "usage: update_kernel.sh zImage.plf"
        exit 1
fi

if [ ! -e $1 ]; then
        echo file $1 does not exist
        exit 1
fi

attach_mtd $MTD_NUMBER

#check kernel type
KERNEL_TYPE=$(get_kernel_type $1)
UBI_NAME=$(get_kernel_ubi_name $1)
if [ "$KERNEL_TYPE" = "production" ]; then
	echo "found a production kernel"
elif  [ "$KERNEL_TYPE" = "updater" ]; then
  echo "found an updater kernel"
else
	echo "not a valid kernel"
	exit 1
fi

echo updating kernel with $1

for UBI_SUBPART in ${UBI_PART}_0 ${UBI_PART}_1; do
  NAME=$(ubinfo $UBI_SUBPART | grep Name |  sed 's/ \+/ /g' | cut -d ' ' -f2-)
	if [ "$NAME" = "$UBI_NAME" ]; then
	  echo "found $UBI_NAME in $UBI_SUBPART, writting new kernel"
	  ubiupdatevol $UBI_SUBPART $1
	  echo "done"
    break
  else
	  echo "$UBI_SUBPART does not correspond to $UBI_NAME kernel"
  fi
done

ubidetach -p $MTD_PART
