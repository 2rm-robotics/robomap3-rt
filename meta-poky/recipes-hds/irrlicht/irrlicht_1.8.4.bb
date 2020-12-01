DESCRIPTION = "Irrlicht 3D rendering engine "
LICENSE = "zlib"
LIC_FILES_CHKSUM = "file://../../doc/irrlicht-license.txt;md5=6fba02eec4e78d1b2fd8ad368437a502"
DEPENDS = "virtual/libx11 libxxf86vm mesa"

PN = "irrlicht"
PV = "1.8.4"
PR = "r0"

SRC_URI = "http://downloads.sourceforge.net/project/irrlicht/Irrlicht%20SDK/1.8/${PV}/irrlicht-${PV}.zip\
		file://Makefile.patch;pnum=0"

SRC_URI[md5sum] = "9401cfff801395010b0912211f3cbb4f"
SRC_URI[sha256sum] = "f42b280bc608e545b820206fe2a999c55f290de5c7509a02bdbeeccc1bf9e433"

S = "${WORKDIR}/${PN}-${PV}/source/Irrlicht"

EXTRA_OEMAKE = "sharedlib NDEBUG=1"

#do_configure(){
#glext.h does not include good definitions...
#	cp ${PKG_CONFIG_SYSROOT_DIR}/usr/include/GL/glext.h ./
#}

do_install() {
	export INSTALL_DIR=${D}${libdir}
	oe_runmake install
}
