SUMMARY = "Basic can init script and configuration file"
DESCRIPTION = "This package provides tools to configure can interfaces"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

COMPATIBLE_MACHINE = "ardrone2"

inherit update-rc.d

INITSCRIPT_NAME = "init-imu-ardrone2"
INITSCRIPT_PARAMS = "start 99 S . stop 99 0 6 1 ."

SRC_URI = "file://init \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/init-imu-ardrone2
}
