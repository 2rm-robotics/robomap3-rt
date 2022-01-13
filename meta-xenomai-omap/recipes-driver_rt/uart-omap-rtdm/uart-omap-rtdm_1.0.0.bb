DESCRIPTION = "RT module pour uart omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel xenomai"
PR = "r7"

SRC_URI = "git://git.renater.fr/anonscm/git/omap3-rtdrivers/omap3-rtdrivers.git;protocol=https \
	file://rt_serial/Correct-Misconfiguration-of-divisor-for-uart-speed.patch \
	file://uart.conf \
"
SRCREV = "74156c343281f1fd09bf9a54c0327f28f6f31290"

S = "${WORKDIR}/git/rt_serial"

inherit module

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}"
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	install -m 0644 ${S}/uart_omap_rtdm*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/uart.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"

