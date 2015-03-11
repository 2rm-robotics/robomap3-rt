DESCRIPTION = "RT module i2c omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel xenomai"
PR = "r9"

SRCREV = "315"
SRC_URI = "file://rt_i2c/i2c_omap.c \
	file://rt_i2c/i2c_omap.h \
	file://rt_i2c/rti2c.h \
	file://rt_i2c/Makefile \
	file://i2c.conf"

S = "${WORKDIR}/rt_i2c"

inherit module

COMPATIBLE_MACHINE = "(uav|overo)"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}"
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	install -m 0644 ${S}/i2c_omap*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	
	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/i2c.conf ${D}/etc/modules-load.d/

	install -d ${D}/usr/include/xenomai/rtdm
	install -m 0644 ${S}/rti2c.h ${D}/usr/include/xenomai/rtdm
}

FILES_${PN} += "/etc/modules-load.d/*"
