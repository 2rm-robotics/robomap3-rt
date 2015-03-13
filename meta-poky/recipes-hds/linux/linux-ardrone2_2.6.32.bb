DESCRIPTION = "2.6 Linux Kernel for the Ar Drone 2 platform"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"


DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ardrone2 = "1"

COMPATIBLE_MACHINE = "ardrone2"

inherit kernel

PR = "r7"
KV = "${PV}-9"

SRCREV = "13"
SRC_URI = "svn://devel.hds.utc.fr/svn/ardrone2_src/trunk;module=${PN}-${KV};protocol=https"

do_configure() {

        rm -f ${S}/.config || true

        oe_runmake ardrone2_defconfig
}

do_install_append() {
	# Necessary for building modules like dsp
	if [ -f include/linux/bounds.h ]; then
		cp include/linux/bounds.h $kerneldir/include/linux/bounds.h
	fi
}
S = "${WORKDIR}/${PN}-${KV}"
