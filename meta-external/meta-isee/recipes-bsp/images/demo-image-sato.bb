DESCRIPTION = "Image with Sato, a mobile environment and visual style for \
mobile devices. The image supports X11 with a Sato theme, Pimlico \
applications, and contains terminal, editor, and file manager."

#  "tools-debug"    - add debugging tools (gdb, strace)
#  "tools-profile"  - add profiling tools (oprofile, exmap, lttng )
#  "tools-testapps" - add useful testing tools (ts_print, aplay, arecord etc.)
#  "debug-tweaks"   - make an image suitable for development
#                     e.g. ssh root access has a blank password
#${ENHANCED_IMAGE_FEATURES}: removed for poky 10.0.0
IMAGE_FEATURES += "splash x11-sato \
	ssh-server-openssh tools-debug tools-profile tools-testapps \
	debug-tweaks"

LICENSE = "MIT"

inherit core-image

EXTRA_UTIL_LINUX = "util-linux e2fsprogs-mke2fs grep dosfstools sysfsutils parted"

#util-linux-sfdisk util-linux-mkfs util-linux-swaponoff 
#util-linux-fdisk util-linux-cfdisk util-linux-mount util-linux-umount 
#util-linux-losetup

EXTRA_BSP = "poky-feed-config-rpm openssh-root-tweaks mtd-utils writeloader \
	devmem2 igep-x-loader igep-tools cpufrequtils canutils alsa-states \
	media-ctl yavta iproute2 bridge-utils"

EXTRA_TESTTOOLS = "iperf memtester evtest multimon i2c-tools tcpdump"

EXTRA_GRAPHICS = "xinput-calibrator xterm"

EXTRA_CONNECTIVITY = "ppp ppp-tools dhcp-server iw openssh-sftp-server lighttpd web-webkit"

EXTRA_LIBS = "libsdl"

IMAGE_INSTALL += " \
	${EXTRA_BSP} \
	${EXTRA_TESTTOOLS} \
	${EXTRA_UTIL_LINUX} \
	${EXTRA_GRAPHICS} \
	${EXTRA_CONNECTIVITY} \
	${EXTRA_LIBS} \
	packagegroup-python \
	gnuplot "
