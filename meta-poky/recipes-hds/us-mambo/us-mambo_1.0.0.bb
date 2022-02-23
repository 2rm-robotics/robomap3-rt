DESCRIPTION = "RT module pour uart omap"
SECTION = "kernel/modules"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
RDEPENDS_${PN} = "kernel"
DEPENDS = "virtual/kernel"

SRC_URI = "git://gitlab.utc.fr/uav-hds/parrot/mambo/us-kernel-module.git;branch=main;protocol=https \
            file://us.conf "
SRCREV = "4b7d6396953f43e0981f37b6cf5c5bfce4c7ae9a"

S = "${WORKDIR}/git"

inherit module

COMPATIBLE_MACHINE = "mambo"

do_compile () {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS CC LD CPP
	make KSRC="${STAGING_KERNEL_DIR}"
}

do_install () {
	install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/us
	install -m 0644 ${S}/ultra_snd*${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/us

	install -d ${D}/etc/modules-load.d
	install -m 0644 ${WORKDIR}/us.conf ${D}/etc/modules-load.d/
}

FILES_${PN} += "/etc/modules-load.d/*"

