DESCRIPTION = "linuxutils"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel"
PR = "r0"

SRC_URI = "git://gitlab.utc.fr/uav-hds/igep/dsp/${PN}.git;branch=${PV};protocol=https \
	file://cmem.conf"
SRCREV = "eaa92a4800317a48b5a7cf5f5ef450dd8ba001a9"

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

S = "${WORKDIR}/git"

inherit module

# Poky migration makes modules build in another directory, so force to use the kernel one
KBUILD_OUTPUT = "${STAGING_KERNEL_DIR}"

do_compile () {
	export OMAP3530_KERNEL=${STAGING_KERNEL_DIR}
	unset LDFLAGS

	#compilation cmem
	cd ${S}/packages/ti/sdo/linuxutils/cmem/src/module
	make release
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/packages/ti/sdo/linuxutils/cmem/src/module/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/cmem.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"

