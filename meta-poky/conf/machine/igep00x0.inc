PREFERRED_PROVIDER_virtual/xserver ?= "xserver-xorg"
XSERVER ?= "xserver-xorg \
           xf86-input-evdev \
           xf86-input-mouse \
           xf86-video-omapfb \
           xf86-input-keyboard"

# Ship all kernel modules by default
MACHINE_EXTRA_RRECOMMENDS = "kernel-modules linux-firmware-sd8686 mtd-utils igep-x-loader screen nano bash strace"

# Allow for extra depends
#EXTRA_IMAGEDEPENDS += ""

# Uncomment the following line to enable the hard floating point abi. Note that
# this breaks some binary libraries and 3D (neither of which ship with
# meta-yocto). For maximum compatibility, leave this disabled.
#DEFAULTTUNE ?= "cortexa8hf-neon"
include conf/machine/include/tune-cortexa8.inc

IMAGE_FSTYPES += "tar.bz2 jffs2"
EXTRA_IMAGECMD_jffs2 = "-lnp "

# 2.6.37 and later kernels use OMAP_SERIAL, ttyO2
# earlier kernels use ttyS2
SERIAL_CONSOLE = "115200 ttyO2"

PREFERRED_PROVIDER_virtual/kernel ?= "linux-igep"

KERNEL_IMAGETYPE = "zImage"
