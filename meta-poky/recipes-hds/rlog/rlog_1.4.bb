DESCRIPTION = "librlog"
PR = "r0"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=e77fe93202736b47c07035910f47974a"

DEPENDS = "libtool automake"


SRC_URI = "https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/rlog/rlog-1.4.tar.gz"
SRC_URI[md5sum] = "c29f74e0f50d66b20312d049b683ff82"
SRC_URI[sha256sum] = "a938eeedeb4d56f1343dc5561bc09ae70b24e8f70d07a6f8d4b6eed32e783f79"

#EXTRA_OECONF = "--with-boost-libdir=${STAGING_LIBDIR} --with-boost=${STAGING_DIR_TARGET}"


inherit autotools-brokensep

