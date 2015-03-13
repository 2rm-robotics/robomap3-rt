#!/bin/sh
### BEGIN INIT INFO
# Provides:          mountall
# Required-Start:    mountvirtfs
# Required-Stop:
# Default-Start:     S
# Default-Stop:
# Short-Description: Mount all filesystems.
# Description:
### END INIT INFO

. /etc/default/rcS

#
# Mount local filesystems in /etc/fstab. For some reason, people
# might want to mount "proc" several times, and mount -v complains
# about this. So we mount "proc" filesystems without -v.
#
test "$VERBOSE" != no && echo "Mounting local filesystems..."
mount -at nonfs,nosmbfs,noncpfs 2>/dev/null

#
# We might have mounted something over /dev, see if /dev/initctl is there.
#
if test ! -p /dev/initctl
then
	rm -f /dev/initctl
	mknod -m 600 /dev/initctl p
fi
kill -USR1 1

#
# Execute swapon command again, in case we want to swap to
# a file on a now mounted filesystem.
#
swapon -a 2> /dev/null

#
# We should have mounted /boot if we boot from mmc or flash
#
nfsroot=0
mmcroot=0
nandroot=0

exec 9<&0 < /proc/mounts
while read dev mtpt fstype rest; do
	if test $mtpt = "/" ; then
		case $fstype in
			nfs | nfs4)
				nfsroot=1
				break
				;;
			ext2 | ext3 | ext4)
				mmcroot=1
				break
				;;
			jffs2 | ubifs)
				nandroot=1
				break
				;;
			*)
				;;
		esac
	fi
done
# nothing to do if is a nfsroot
if test $mmcroot -eq 1 ; then
	mount -t auto -o defaults,sync /dev/mmcblk0p1 /boot
elif test $nandroot -eq 1 ; then
	mount -t jffs2 -o defaults,sync /dev/mtdblock1 /boot
fi

: exit 0
