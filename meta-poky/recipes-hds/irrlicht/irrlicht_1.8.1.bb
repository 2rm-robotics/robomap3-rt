DESCRIPTION = "Irrlicht 3D rendering engine "
LICENSE = "zlib"
LIC_FILES_CHKSUM = "file://../../doc/irrlicht-license.txt;md5=6fba02eec4e78d1b2fd8ad368437a502"
DEPENDS = "virtual/libx11 libxxf86vm mesa"

PN = "irrlicht"
PV = "1.8.1"
PR = "r2"

SRC_URI = "http://downloads.sourceforge.net/project/irrlicht/Irrlicht%20SDK/1.8/${PV}/irrlicht-${PV}.zip\
		file://Makefile.patch;pnum=0"

SRC_URI[md5sum] = "f4f7fa33bd1060eb0dd51dcd66b0f6e3"
SRC_URI[sha256sum] = "814bb90116d5429449ba1d169e2cbff881c473b7eada4c2447132bc4f4a6e97b"

S = "${WORKDIR}/${PN}-${PV}/source/Irrlicht"

EXTRA_OEMAKE = "sharedlib NDEBUG=1"

do_configure(){
#glext.h does not include good definitions...
	cp ${PKG_CONFIG_SYSROOT_DIR}/usr/include/GL/glext.h ./
}

do_install() {
	export INSTALL_DIR=${D}${libdir}
	oe_runmake install
}
