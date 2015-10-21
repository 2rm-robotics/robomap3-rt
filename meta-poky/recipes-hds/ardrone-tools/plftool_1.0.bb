DESCRIPTION = "plftools"
PR = "r2"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRCREV = "29"
SRC_URI = "svn://ardrone-tool.googlecode.com/svn/projects;module=plftool;protocol=http \
 	file://plf.patch;pnum=0 \
	file://Makefile.patch;pnum=0"

S = "${WORKDIR}/plftool/trunk"

BBCLASSEXTEND = "native"

DEPENDS= "libplf"

EXTRA_OEMAKE = "'CC=${CC} -Wno-error=unused-but-set-variable -Wno-error=format= -I${STAGING_INCDIR_NATIVE}/libplf '"

do_install() {
	install -d ${D}${bindir}
	install plftool  ${D}${bindir}
}
