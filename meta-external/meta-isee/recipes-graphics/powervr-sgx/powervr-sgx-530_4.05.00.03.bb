DESCRIPTION = "Kernel drivers for the PowerVR SGX chipset found in the omap3 SoCs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=21228a42e27d1d104b31a83f7c9da935"

inherit module

SRC_URI = "http://meta-igep.googlecode.com/files/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "a7bcc6b2694406f2b53e13b420b0c3b4"
SRC_URI[sha256sum] = "909f8ed4a2cf4a2e3764ce7e4b80ea3e65cf878945dbc79d6179594bd2302f7b"

S = "${WORKDIR}/${PN}-${PV}/GFX_Linux_KM"

export KERNELDIR = "${STAGING_KERNEL_DIR}"

PACKAGE_STRIP = "no"

MAKE_TARGETS = " BUILD=release TI_PLATFORM=omap3630"

do_install() {
	mkdir -p ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
	cp ${S}/pvrsrvkm.ko \
	   ${S}/services4/3rdparty/dc_omapfb3_linux/omaplfb.ko  \
	   ${S}/services4/3rdparty/bufferclass_ti/bufferclass_ti.ko \
	   ${D}/lib/modules/${KERNEL_VERSION}/kernel/drivers/gpu/pvr
}
