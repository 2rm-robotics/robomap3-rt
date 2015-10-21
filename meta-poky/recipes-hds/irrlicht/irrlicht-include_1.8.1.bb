DESCRIPTION = "Irrlicht 3D rendering engine, include file only for arm "
LICENSE = "zlib"
LIC_FILES_CHKSUM = "file://../../doc/irrlicht-license.txt;md5=6fba02eec4e78d1b2fd8ad368437a502"

PN = "irrlicht-include"
PV = "1.8.1"
PR = "r0"

ALLOW_EMPTY_${PN} = "1"

SRC_URI = "http://downloads.sourceforge.net/project/irrlicht/Irrlicht%20SDK/1.8/${PV}/irrlicht-${PV}.zip"

SRC_URI[md5sum] = "f4f7fa33bd1060eb0dd51dcd66b0f6e3"
SRC_URI[sha256sum] = "814bb90116d5429449ba1d169e2cbff881c473b7eada4c2447132bc4f4a6e97b"

S = "${WORKDIR}/irrlicht-${PV}/source/Irrlicht"

do_configure(){
}

do_compile(){
}

do_install() {
	install -d ${D}${includedir}/irrlicht
	install -m 0644 ${S}/../../include/* ${D}${includedir}/irrlicht/
}
