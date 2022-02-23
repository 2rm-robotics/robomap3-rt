DESCRIPTION = "local power manager"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel dsplink"
PR = "r0"

SRC_URI = "git://gitlab.utc.fr/uav-hds/igep/dsp/${PN}.git;branch=${PV};protocol=https \
	file://${PN}.conf"
SRCREV = "f205c51021b39ae2223a52ae3d7a2b5c6e3be8f6"

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

S = "${WORKDIR}/git"

inherit module

# Poky migration makes modules build in another directory, so force to use the kernel one
KBUILD_OUTPUT = "${STAGING_KERNEL_DIR}"

do_compile () {
	export DSPLINK=${STAGING_DIR_TARGET}/opt/dsplink/
    export OMAP3530_KERNEL=${STAGING_KERNEL_DIR}
	unset LDFLAGS
	
	#compilation lpm
	cd ${S}/packages/ti/bios/power/modules/omap3530/lpm
	make	
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/packages/ti/bios/power/modules/omap3530/lpm/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	
	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/${PN}.conf ${D}/etc/modules-load.d/

	install -d ${D}${bindir}/
	install -m 0755 ${S}/packages/ti/bios/power/utils/bin/ti_platforms_evm3530/linux/release/lpmON.x470uC ${D}${bindir}/lpmON
	install -m 0755 ${S}/packages/ti/bios/power/utils/bin/ti_platforms_evm3530/linux/release/lpmOFF.x470uC ${D}${bindir}/lpmOFF
}

FILES_${PN} += "${bindir}/lpmON ${bindir}/lpmOFF"
FILES_${PN} += "/etc/modules-load.d/*"

