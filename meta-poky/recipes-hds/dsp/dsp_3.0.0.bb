DESCRIPTION = "dsp module omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel"
PR = "r4"

SRCREV = "40"
SRC_URI = "svn://devel.hds.utc.fr/svn/igep_dsp/;module=trunk;protocol=https \
	file://dsp.conf"

SRC_URI[md5sum] = "73b6615ccfa0ab60cd45bf921eabcde2"

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

S = "${WORKDIR}/trunk"

inherit module

do_compile () {
	export TI_TOOLS_BASE_DIR=${S}
	export DSPLINK=${TI_TOOLS_BASE_DIR}/dsplink/dsplink
	export OMAP3530_KERNEL=${STAGING_KERNEL_DIR}
	unset LDFLAGS

	#compilation dsplink
	cd ${S}/dsplink/dsplink/config/bin
	perl dsplinkcfg.pl --platform=OMAP3530 --nodsp=1 --dspcfg_0=OMAP3530SHMEM --dspos_0=DSPBIOS5XX --gppos=OMAPLSP --comps=PONM --DspTskMode=1
	cd ../../gpp/src 
	make release

	#compilation cmem
	cd ${S}/linuxutils/packages/ti/sdo/linuxutils/cmem/src/module
	make release
	
	#compilation lpm
	cd ${S}/local_power_manager/packages/ti/bios/power/modules/omap3530/lpm
	make	
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/dsplink/dsplink/gpp/export/BIN/Linux/OMAP3530/RELEASE/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/linuxutils/packages/ti/sdo/linuxutils/cmem/src/module/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/local_power_manager/packages/ti/bios/power/modules/omap3530/lpm/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	
	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/dsp.conf ${D}/etc/modules-load.d/

	install -d ${D}${bindir}/
	install -m 0755 ${S}/local_power_manager/packages/ti/bios/power/utils/bin/ti_platforms_evm3530/linux/release/lpmON.x470uC ${D}${bindir}/lpmON
	install -m 0755 ${S}/local_power_manager/packages/ti/bios/power/utils/bin/ti_platforms_evm3530/linux/release/lpmOFF.x470uC ${D}${bindir}/lpmOFF
}

FILES_${PN} += "${bindir}/lpmON ${bindir}/lpmOFF"
FILES_${PN} += "/etc/modules-load.d/*"

