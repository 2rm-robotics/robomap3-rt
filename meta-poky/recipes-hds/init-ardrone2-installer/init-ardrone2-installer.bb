SUMMARY = "installer script"
DESCRIPTION = "This package provides tools to configure can interfaces"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

COMPATIBLE_MACHINE = "ardrone2-installer"

inherit update-rc.d

INITSCRIPT_NAME = "init-ardrone2-installer"
INITSCRIPT_PARAMS = "start 99 S . stop 99 0 6 1 ."

SRC_URI = "file://init \
           file://flash.sh \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d
#	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/flash.sh ${D}${bindir}/
}
