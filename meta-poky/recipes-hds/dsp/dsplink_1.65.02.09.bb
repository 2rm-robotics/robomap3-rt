DESCRIPTION = "dsplink"
SECTION = "kernel/modules"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel"
PR = "r0"

SRC_URI = "git://gitlab.utc.fr/uav-hds/igep/dsp/${PN}.git;branch=${PV};protocol=https \
	file://${PN}.conf"
SRCREV = "cf59bf75557cb176109bae661efa6bde67653ca3"

COMPATIBLE_MACHINE = "(uav|overo|ardrone2)"

S = "${WORKDIR}/git"

inherit module

# Poky migration makes modules build in another directory, so force to use the kernel one
KBUILD_OUTPUT = "${STAGING_KERNEL_DIR}"

do_compile () {
	export TI_TOOLS_BASE_DIR=${S}
	export DSPLINK=${TI_TOOLS_BASE_DIR}/dsplink
	export OMAP3530_KERNEL=${STAGING_KERNEL_DIR}
	unset LDFLAGS

	cd ${S}/dsplink/config/bin
	perl dsplinkcfg.pl --platform=OMAP3530 --nodsp=1 --dspcfg_0=OMAP3530SHMEM --dspos_0=DSPBIOS5XX --gppos=OMAPLSP --comps=PONM --DspTskMode=1
	cd ../../gpp/src 
	make release
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	install -m 0644 ${S}/dsplink/gpp/export/BIN/Linux/OMAP3530/RELEASE/*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/dsp
	
    #local-power-manager needs acces to all dsplink files, lpm Makefile should be adapted to avoid this...
    install -d ${STAGING_DIR_TARGET}/opt
    cp -arv ${S}/dsplink ${STAGING_DIR_TARGET}/opt

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/${PN}.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"
