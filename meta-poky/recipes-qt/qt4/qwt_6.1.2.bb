DEPENDS = "qt4-x11-free"

inherit qt4x11

LICENSE = "LGPLv2.1"
LIC_FILES_CHKSUM = "file://COPYING;md5=dac2743472b0462ff3cfb4af42051c88"

SRC_URI = "http://downloads.sourceforge.net/project/qwt/qwt/6.1.2/qwt-6.1.2.tar.bz2"

SRC_URI[md5sum] = "9c88db1774fa7e3045af063bbde44d7d"
SRC_URI[sha256sum] = "2b08f18d1d3970e7c3c6096d850f17aea6b54459389731d3ce715d193e243d0c"

PR = "r0"

do_install() {
	oe_runmake install INSTALL_ROOT=${D}
	mkdir -p ${D}/usr/include/
	mv ${D}/usr/local/qwt-6.1.2/include/ ${D}/usr/include/qwt
	mv ${D}/usr/local/qwt-6.1.2/lib/ ${D}/usr/lib
}

PACKAGES = "${PN}-dbg ${PN} ${PN}-dev ${PN}-doc"

# Otherwise an error of QA Check in poky Krogoth
INSANE_SKIP_${PN}-dev += "dev-elf"

FILES_${PN}-dbg += "/usr/local/qwt-6.1.2/lib/.debug/* /usr/local/qwt-6.1.2/plugins/designer/.debug/"
FILES_${PN} +="/usr/local/qwt-6.1.2/features/*"
FILES_${PN}-dev += "/usr/local/qwt-6.1.2/plugins/designer/libqwt_designer_plugin.so"
FILES_${PN}-doc = "/usr/local/qwt-6.1.2/doc/*"
