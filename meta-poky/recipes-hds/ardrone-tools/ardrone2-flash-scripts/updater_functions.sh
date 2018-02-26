#!/bin/sh

#sets KERNEL_FILE and ROOTFS_FILE
#arguments: path1 path2
check_rootfs_and_kernel_presence () {
local KERNEL_FILE1=$1/zImage.plf
local KERNEL_FILE2=$2/zImage.plf
local ROOTFS_FILE1=$1/rootfs.tar.bz2
local ROOTFS_FILE2=$2/rootfs.tar.bz2

  #check kernel file
  if [ -e $KERNEL_FILE1 ]; then
    KERNEL_FILE=$KERNEL_FILE1  
  elif [ -e $KERNEL_FILE2 ]; then
    KERNEL_FILE=$KERNEL_FILE2 
  else
    echo kernel file not found in $1 and $2
    exit 1
  fi

  echo found $KERNEL_FILE

  #check kernel type
  if [ ! "$(get_kernel_type $KERNEL_FILE)" = "production" ]; then
	  echo "not a valid kernel"
	  exit 1
  fi

  #check rootfs file
  if [ -e $ROOTFS_FILE1 ]; then
    ROOTFS_FILE=$ROOTFS_FILE1  
  elif [ -e $ROOTFS_FILE2 ]; then
    ROOTFS_FILE=$ROOTFS_FILE2 
  else
    echo rootfs file not found in $1 and $2
    exit 1
  fi

  echo found $ROOTFS_FILE
}

#argument: mtd number
#sets UBI_PART
#/dev/mtd${1} is attached after calling this function
attach_mtd () {
local MTD_PART=/dev/mtd$1
UBI_PART=not_found

  #search if mtd already attached
  #for i in `seq 0 9`; do # seq does not work in original ardrone2's busybox
  for i in 0 1 2 3 4 5 6 7 8 9; do
	  if [ -d /sys/devices/virtual/ubi/ubi$i/ ];then
		  if [[ $(cat /sys/devices/virtual/ubi/ubi$i/mtd_num) = $1 ]]; then
			  UBI_PART=/dev/ubi$i
			  echo $MTD_PART is already attached to $UBI_PART 
			  break
		  fi
	  fi
  done

  #if not, attach it
  if [ "$UBI_PART" = "not_found" ];then
	  echo $MTD_PART not attached, attaching it
	  local UBI_NUMBER=$(ubiattach -p $MTD_PART | sed -r 's/.*UBI device number ([^,]+).*/\1/')
	  UBI_PART=/dev/ubi$UBI_NUMBER
	  echo $MTD_PART attached to $UBI_PART
  fi
}


#check kernel type
#argument: kernel file
get_kernel_type () {
  local KERNEL_BOOT=$(strings $1 | grep root= | sed -r 's/.*root=([^ ]+).*/\1/')
  if [ "$KERNEL_BOOT" = "ubi1:rootfs" ]; then
	  echo "production"
  elif  [ "$KERNEL_BOOT" = "ubi0:factory" ]; then
    echo "updater"
  else
	  echo "invalid"
  fi
}

#argument: kernel file
get_kernel_ubi_name () {
local KERNEL_TYPE=$(get_kernel_type $1)
  if [ "$KERNEL_TYPE" = "production" ]; then
	  echo "main_boot"
  elif  [ "$KERNEL_TYPE" = "updater" ]; then
    echo "alt_boot"
  else
	  echo "invalid"
  fi
}
