SUMMARY = "updater script"
DESCRIPTION = "This package provides tools to flash ardrone2"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

COMPATIBLE_MACHINE = "ardrone2-updater"

inherit update-rc.d

INITSCRIPT_NAME = "init-ardrone2-updater"
INITSCRIPT_PARAMS = "start 99 S . stop 99 0 6 1 ."

SRC_URI = "file://init \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/${PN}
}
