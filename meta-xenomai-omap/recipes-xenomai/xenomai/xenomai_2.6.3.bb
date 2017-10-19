DESCRIPTION = "Provides userspace xenomai support and libraries needed to for \
real-time applications using the xenomai RTOS implementation"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://include/COPYING;md5=60faa041c8d4a75ab87e115a9469d94d"
SECTION = "xenomai"
HOMEPAGE = "http://www.xenomai.org/"
PR = "r0"

SRC_URI = "https://xenomai.org/downloads/xenomai/stable/xenomai-${PV}.tar.bz2 \
		file://rti2c.h"

SRC_URI[md5sum] = "9f83c39cfb10535df6bf51702714e716"
SRC_URI[sha256sum] = "4d0d09431f0340cf4c9e2745177f77f15b7b124f89982035d1d3586519d7afe9"

S = "${WORKDIR}/xenomai-${PV}"

inherit autotools

includedir = "/usr/include/xenomai"

do_install_append() {
	#remove unused files
	rm -rf ${D}/dev
	cp ${WORKDIR}/rti2c.h ${D}/usr/include/xenomai/rtdm/
}

# create different packages
PACKAGES = "${PN}-dbg ${PN}-dev ${PN}-staticdev ${PN}-doc ${PN}"

FILES_${PN}-dbg += "/usr/bin/regression/posix/.debug \
 	/usr/bin/regression/native/.debug \
 	/usr/bin/regression/native+posix/.debug \
 "

FILES_${PN} += "/usr/bin/* \
	/usr/lib/*.so \
	/usr/sbin/* \
	/usr/share/doc/* \
	/usr/share/man/* \
 "
 
FILES_${PN}-dev += "/usr/include/* \
	/usr/lib/*.so \
	/usr/lib/posix.wrappers \
 "
 
FILES_${PN}-staticdev += "/usr/lib/*.a"

FILES_${PN}-doc += "/usr/share/doc/* \
	/usr/share/man/* \
 "

# add the files to the SDK machine
BBCLASSEXTEND = "nativesdk"

