#!/bin/sh
MTD_NUMBER_PROD=3
MTD_NUMBER_UPDATE=2

source /usr/bin/updater_functions.sh

#arguments: MTD_NUMBER rootfs.tar.bz2
flash () {
  local MTD_PART=/dev/mtd$1
  
  attach_mtd $1
#check if we are not flashing ourself!
  if [[ "/dev/"$(mount | grep "on / type ubifs" | cut -d':' -f1) = ${UBI_PART} ]]; then
	  echo "error, cannot flash ourself"
    exit 1		  
  fi

#umount /factory if necessary
  if [[ "/dev/"$(mount | grep "on /factory type ubifs" | cut -d':' -f1) = ${UBI_PART} ]]; then
	umount /factory  
  fi

  mkdir -p /tmp/rootfs
  mount -t ubifs ${UBI_PART}_0 /tmp/rootfs
  rm -rf /tmp/rootfs/*
  if [ "${2##*.}" = "gz" ]; then
    tar -xzf $2 -C /tmp/rootfs
  else
    tar -xjf $2 -C /tmp/rootfs
  fi
  
  umount /tmp/rootfs
  rmdir /tmp/rootfs
  ubidetach -p $MTD_PART
}


#basic checks
if [ "$1" = "" ]; then
  echo "usage: flash_rootfs.sh rootfs.tar.bz2 [rootfs_type]"
  echo "rootfs_type can be production or updater; if omitted it will be asked at runtime"
  exit 1
fi

if [ ! -e $1 ]; then
  echo file $1 does not exist
  exit 1
fi

if [ "$2" = "" ]; then
  echo "select rootfs_type:"
  echo "-1 production"
  echo "-2 updater"
  echo "choice [1/2]?"
	read answer
  if [ "$answer" = "1" ]; then
    ROOTFS_TYPE=production
  fi
  if [ "$answer" = "2" ]; then
    ROOTFS_TYPE=updater
  fi
else
  ROOTFS_TYPE=$2
fi

if [ "$ROOTFS_TYPE" != "production" ] && [ "$ROOTFS_TYPE" != "updater" ]; then
  echo "invalid rootfs_type" $ROOFS_TYPE
  exit 1
fi

if [ "$ROOTFS_TYPE" = "production" ]; then
  echo "flashing production rootfs"
  flash $MTD_NUMBER_PROD $1
fi

if [ "$ROOTFS_TYPE" = "updater" ]; then
  echo "flashing updater rootfs"
 flash $MTD_NUMBER_UPDATE $1
fi
