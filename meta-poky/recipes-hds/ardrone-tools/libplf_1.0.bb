DESCRIPTION = "libplf"
PR = "r1"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRCREV = "29"
SRC_URI = "svn://ardrone-tool.googlecode.com/svn/projects;module=libplf;protocol=http \
	file://plf.patch;pnum=0 \
	file://Makefile.patch;pnum=0 \
"

S = "${WORKDIR}/libplf/trunk"

inherit autotools-brokensep

BBCLASSEXTEND = "native"

DEPENDS ="zlib"

do_install() {
	install -d ${D}${libdir}
	install -d ${D}${includedir}/libplf
	install libplf.so  ${D}${libdir}
	install *.h ${D}${includedir}/libplf
}
