DESCRIPTION = "MAVLink message marshaling library"
SECTION = "libs"
LICENSE = "LGPLv3"
LIC_FILES_CHKSUM = "file://COPYING;md5=54ad3cbe91bebcf6b1823970ff1fb97f"

#use the same mavlink definitions as ardupilot
SRC_URI = "git://gitlab.utc.fr/uav-hds/intel-aero/ardupilot.git;branch=Copter-3.6;protocol=https"
SRCREV = "2719d84341788589d7958219b6d9fe43cb18c886"

S = "${WORKDIR}/git/modules/mavlink"

inherit pythonnative

DEPENDS = "${PYTHON_PN}-future-native"

#remove rdepends on dev package; otherwise ${PN}-dev depends on ${PN} but we don't have ${PN} package here
RDEPENDS_${PN}-dev = ""

do_compile() {
    python -m pymavlink.tools.mavgen --lang=C --wire-protocol=1.0 --output=generated/include/mavlink/v1.0 message_definitions/v1.0/common.xml
}

do_install() {
    mkdir -p ${D}/usr/include
    cp -r ${S}/generated/include/mavlink/ ${D}/usr/include
}

PACKAGES = "${PN}-dev"
