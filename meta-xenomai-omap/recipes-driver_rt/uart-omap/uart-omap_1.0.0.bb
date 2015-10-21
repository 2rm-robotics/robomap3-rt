DESCRIPTION = "RT module pour uart omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel xenomai"
PR = "r7"

SRC_URI = "git://git.renater.fr/omap3-rtdrivers.git;protocol=git \
	file://rt_serial/Correct-Misconfiguration-of-divisor-for-uart-speed.patch \
	file://uart.conf \
"
SRCREV = "a5a86afd886e3dad7ecd42a710b1b03bf7730086"

S = "${WORKDIR}/git/rt_serial"

inherit module

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}"
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt
	install -m 0644 ${S}/uart_omap*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/rt

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/uart.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"

