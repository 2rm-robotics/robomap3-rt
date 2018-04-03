SUMMARY = "Basic cam init script and configuration file"
DESCRIPTION = "This package provides tools to configure ardrone2 cameras"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

COMPATIBLE_MACHINE = "ardrone2"
RDEPENDS_${PN}="media-ctl yavta"

inherit update-rc.d

INITSCRIPT_NAME = "init-cam-ardrone2"
INITSCRIPT_PARAMS = "start 99 S . stop 99 0 6 1 ."

SRC_URI = "file://init \
           file://parallel-stream.sh \
"

do_install () {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/init-cam-ardrone2
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/parallel-stream.sh ${D}${bindir}/
}
