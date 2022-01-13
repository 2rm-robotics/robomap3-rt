DESCRIPTION = "RT module i2c omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel xenomai"
DEPENDS = "virtual/kernel xenomai"
PR = "r10"

SRC_URI = "git://git.renater.fr/anonscm/git/omap3-rtdrivers/omap3-rtdrivers.git;protocol=https \
	file://i2c.conf \
"

SRCREV = "3a448c9dcaa0501c54aa6b0411a363431120753b"

S = "${WORKDIR}/git/rt_i2c"

inherit module

COMPATIBLE_MACHINE = "(uav|overo)"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}" XENO_INC_DIR=${STAGING_INCDIR}/xenomai
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	install -m 0644 ${S}/i2c_omap_rtdm*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	
	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/i2c.conf ${D}/etc/modules-load.d/
}

# create different packages
PACKAGES = "${PN} ${PN}-dev"

FILES_${PN} += "/etc/modules-load.d/*"
