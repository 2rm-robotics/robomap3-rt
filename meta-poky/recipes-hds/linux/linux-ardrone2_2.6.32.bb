DESCRIPTION = "2.6 Linux Kernel for the Ar Drone 2 platform"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ardrone2 = "1"
DEFAULT_PREFERENCE_ardrone2-installer = "1"
DEFAULT_PREFERENCE_ardrone2-updater = "1"

COMPATIBLE_MACHINE = "(ardrone2|ardrone2-installer|ardrone2-updater)"

inherit kernel
DEPENDS +="plftool-native"

PR = "r10"
KV = "${PV}-9"

SRCREV = "37"
SRC_URI = "svn://devel.hds.utc.fr/svn/ardrone2_src/trunk;module=${PN}-${KV};protocol=https"
S = "${WORKDIR}/${PN}-${KV}"

do_configure() {

        rm -f ${S}/.config || true

        oe_runmake ardrone2_defconfig
	if [ "${MACHINE}" = "ardrone2" ];then
		oe_runmake ardrone2_defconfig
    elif [ "${MACHINE}" = "ardrone2-installer" ];then
		oe_runmake ardrone2_installer_defconfig
	elif [ "${MACHINE}" = "ardrone2-updater" ];then
		oe_runmake ardrone2_installer_defconfig
	fi
}

do_install_append() {
	# Necessary for building modules like dsp
	if [ -f include/linux/bounds.h ]; then
		cp include/linux/bounds.h $kerneldir/include/linux/bounds.h
	fi
}


