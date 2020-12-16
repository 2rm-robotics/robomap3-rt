SECTION = "libs"
DESCRIPTION = "lib sumo"
PR = "r0"
LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=4fbd65380cdd255951079008b364516c"

SRCREV="cb8d020ffaf52933ae87a45aa0a4a3183124d2e8"
SRC_URI = "git://github.com/iloreen/libsumo.git \
	file://CMakeLists.patch;pnum=0"

S = "${WORKDIR}/git"

inherit cmake

do_install_append () {
	cd ${D}${libdir}/
	ln -s libsumo.so libsumo.so.${PV}
}

# create different packages
PACKAGES = "${PN}-dbg ${PN}-dev ${PN}"

 
FILES_${PN}-dev = "/usr/lib/*.so.* /usr/include/sumo/*"

FILES_${PN}+= "/usr/lib/*.so"
