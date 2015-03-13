SUMMARY = "Basic can init script and configuration file"
DESCRIPTION = "This package provides tools to configure can interfaces"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r1"

inherit update-rc.d

INITSCRIPT_NAME = "can"
INITSCRIPT_PARAMS = "start 99 S . stop 99 0 6 1 ."

SRC_URI = "file://init \
           file://can.conf \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/can
	install -m 0644 ${WORKDIR}/can.conf ${D}${sysconfdir}/can.conf
}

RDEPENDS_${PN} = "iproute2"
CONFFILES_${PN} = "${sysconfdir}/can.conf"
