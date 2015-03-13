DESCRIPTION = "opencv"
PN= "opencv"
PR = "r7"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=656583178500e0c44c78e42aef9582bf"

DEPENDS = "v4l-utils libtool jpeg glib-2.0 automake tiff"
#zlib
DEPENDS_class-nativesdk = "libtool glib-2.0 automake tiff"

SRCREV = "592"
SRC_URI = "svn://devel.hds.utc.fr/svn/uav_lib/trunk;module=opencv-1.1.0;protocol=https"
S = "${WORKDIR}/opencv-1.1.0"

inherit autotools

EXTRA_OECONF = "--disable-debug --without-gthread --without-python --without-swig --disable-apps --without-gtk --prefix=${S}"
EXTRA_OECONF_virtclass-nativesdk = "--disable-debug --without-gthread --without-python --without-swig --disable-apps --without-gtk --without-v4l"

export BUILD_SYS

export HOST_SYS

do_compile() {
    oe_runmake
}

do_install() {
	oe_runmake 'DESTDIR=${D}' 'SUDO=/bin/true' install
}


PACKAGES = "${PN}-dev ${PN} ${PN}-share ${PN}-dbg"
# Fixes QA Error - Non -dev package contains symlink .so
FILES_${PN}-share += "${datadir}/opencv/*"
FILES_${PN}-dev += "/usr/lib/*.so"
FILES_${PN}-dbg += "/usr/lib/.debug/* /usr/src/debug/opencv/*"
FILES_${PN} = "${libdir}/*.so*"

BBCLASSEXTEND = "nativesdk"
