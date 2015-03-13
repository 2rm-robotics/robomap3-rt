DESCRIPTION = "RT module for input capture and radio receptor"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel xenomai"
PR = "r0"

SRC_URI = "file://rt_radio/capture_omap.c \
	file://rt_radio/capture_omap.h \
	file://rt_radio/rtradio.h \
	file://rt_radio/Makefile \
	file://capture.conf"

S = "${WORKDIR}/rt_radio"

inherit module

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}"
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	install -m 0644 ${S}/capture_omap*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/capture.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"

