SECTION = "base"
DESCRIPTION = "xenomai"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://include/COPYING;md5=60faa041c8d4a75ab87e115a9469d94d"
PN = "xenomai"
PV = "2.6.1"
PR = "r6"

inherit autotools

COMPATIBLE_MACHINE = "(uav|overo|pacpus|genericx86-64|ardrone2|genericx86)"

SRCREV = "154"
SRC_URI = "http://download.gna.org/xenomai/stable/${PN}-${PV}.tar.bz2"

SRC_URI[md5sum] = "c6b067de392b1441da6528f1e503bf0a"
SRC_URI[sha256sum] = "0433153cb4a017ef275fa7fdb01ff389e290fd2c4013491b776d893139311f29"


S = "${WORKDIR}/xenomai-${PV}"

EXTRA_OECONF = "--prefix=/usr --includedir=/usr/include/xenomai CFLAGS="-march=armv7-a" LDFLAGS="-march=armv7-a" --disable-doc-install --build=i686-pc-linux-gnu --host=arm-poky-linux-gnueabi"
EXTRA_OECONF_genericx86-64 = "--prefix=/usr --includedir=/usr/include/xenomai --enable-x86-sep --enable-smp CFLAGS="-O2 -fno-omit-frame-pointer" --disable-doc-install"
EXTRA_OECONF_genericx86 = "--prefix=/usr --includedir=/usr/include/xenomai --enable-x86-sep --enable-smp CFLAGS="-O2 -fno-omit-frame-pointer" --disable-doc-install"


do_install() {
	oe_runmake 'DESTDIR=${D}' 'SUDO=/bin/true' install
	#remove unused files
	rm -f ${D}${libdir}/posix.wrappers
	rm -rf ${D}/dev
}

PACKAGES = "${PN}-dbg ${PN}-dev ${PN}-staticdev ${PN}"

# Fixes QA Error - Non -dev package contains symlink .so
FILES_${PN}-dbg += "/usr/bin/regression/posix/.debug/* /usr/bin/regression/native/.debug/* /usr/bin/regression/native+posix/.debug/*"
FILES_${PN} += "/usr/bin/* /usr/lib/*.so*"
FILES_${PN}-staticdev += "/usr/lib/*.a"
FILES_${PN}-dev += "/usr/lib/*.so /usr/include/*"
