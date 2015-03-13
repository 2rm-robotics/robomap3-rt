DESCRIPTION = "2.6 Linux Kernel for IGEP based platforms"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

COMPATIBLE_MACHINE_igep00x0 = "igep00x0"

inherit kernel kernel-arch

PR = "r5"
KV = "${PV}-5"

SRC_URI = "http://downloads.isee.biz/pub/releases/linux_kernel/v${KV}/linux-omap-${KV}.tar.gz"

SRC_URI[md5sum] = "ff5824f64fe6546ab5e6e69250e4b7ce"
SRC_URI[sha256sum] = "2d36496becd7f3ba390b68bf452dfe0041c16048cd8770426b1828abe4e8f4c2"

do_configure() {
	rm -f ${S}/.config || true
	oe_runmake igep00x0_defconfig
}

S = "${WORKDIR}/linux-omap-${KV}"
