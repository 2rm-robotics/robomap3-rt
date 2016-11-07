DESCRIPTION = "plftools"
PR = "r2"

LICENSE = "GPLv3"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-3.0;md5=c79ff39f19dfec6d293b95dea7b07891"

SRC_URI[md5sum] = "b6b859c59a738fde659032fbae8c4d6d"
SRC_URI[sha256sum] = "df9137ef37202676ba94b58f6f8a36ae61e32ace2692f72401b4cdd9dd303df1"
SRC_URI = "https://storage.googleapis.com/google-code-archive-source/v2/code.google.com/ardrone-tool/source-archive.zip \
 	file://plf.patch;pnum=0 \
	file://Makefile.patch;pnum=0"

S = "${WORKDIR}/ardrone-tool/projects/plftool/trunk"

BBCLASSEXTEND = "native"

DEPENDS= "libplf"

EXTRA_OEMAKE = "'CC=${CC} -Wno-error=unused-but-set-variable -Wno-error=format= -I${STAGING_INCDIR_NATIVE}/libplf '"

do_install() {
	install -d ${D}${bindir}
	install plftool  ${D}${bindir}
}
