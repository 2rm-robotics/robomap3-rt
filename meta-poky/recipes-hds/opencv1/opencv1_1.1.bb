DESCRIPTION = "opencv1"
PN= "opencv1"
PR = "r8"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=656583178500e0c44c78e42aef9582bf"

DEPENDS = "v4l-utils libtool jpeg glib-2.0 automake tiff"
DEPENDS_class-nativesdk = "libtool glib-2.0 automake tiff"
RDEPENDS_${PN}-share= "python"

SRC_URI = "git://gitlab.utc.fr/uav-hds/framework-uav/uav-lib;branch=main;protocol=https \
	   file://fix_narrowing_conversions_from_unsigned_int_to_int.patch \
"
SRCREV = "29c2af66fa1537272ffd1810258d596f55194365"
S = "${WORKDIR}/git/opencv-1.1.0"

inherit autotools

#prefix = "/usr/"
#libdir = "${prefix}/lib"
#includedir = "${prefix}/include/opencv1"

EXTRA_OECONF = "--disable-debug --without-gthread --without-python --without-swig --disable-apps --without-gtk"
EXTRA_OECONF_virtclass-nativesdk = "--disable-debug --without-gthread --without-python --without-swig --disable-apps --without-gtk --without-v4l"

export BUILD_SYS
export HOST_SYS

do_compile() {
    oe_runmake
}

do_install() {
	oe_runmake 'DESTDIR=${D}' 'SUDO=/bin/true' install
    #avoid problems with opencv2 or 3
    mv ${D}/usr/lib/pkgconfig/opencv.pc ${D}/usr/lib/pkgconfig/opencv1.pc 
    mv ${D}/usr/include/opencv ${D}/usr/include/opencv1
}

PACKAGES = "${PN}-dev ${PN} ${PN}-share ${PN}-dbg"
# Fixes QA Error - Non -dev package contains symlink .so
FILES_${PN}-share += "${datadir}/opencv/*"
FILES_${PN}-dbg += "${libdir}/.debug/* /usr/src/debug/opencv/*"
FILES_${PN}-dev += "${libdir}/*.so"
FILES_${PN} = "${libdir}/*.so*"

BBCLASSEXTEND = "nativesdk"
