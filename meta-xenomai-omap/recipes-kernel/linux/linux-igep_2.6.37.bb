DESCRIPTION = "2.6 Linux Kernel for IGEP based platforms"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"


DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_uav = "1"
DEFAULT_PREFERENCE_airbox = "1"
DEFAULT_PREFERENCE_overo = "1"
DEFAULT_PREFERENCE_pacpus = "1"

COMPATIBLE_MACHINE = "(airbox|uav|overo|pacpus)"

inherit kernel

PR = "r18"
KV = "${PV}-9"

SRCREV = "487"
SRC_URI = "svn://devel.hds.utc.fr/svn/igep_src/trunk;module=linux-omap-${KV};protocol=https"

do_configure() {

	rm -f ${S}/.config || true

	if [ "${MACHINE}" = "uav" ];then
		oe_runmake igep00x0_uav_defconfig
        elif [ "${MACHINE}" = "overo" ];then
		oe_runmake igep00x0_uav_defconfig
        elif [ "${MACHINE}" = "pacpus" ];then
		oe_runmake igep00x0_defconfig
	elif [ "${MACHINE}" = "airbox" ];then
		oe_runmake igep00x0_airbox_defconfig
	fi
}
           
S = "${WORKDIR}/linux-omap-${KV}"
