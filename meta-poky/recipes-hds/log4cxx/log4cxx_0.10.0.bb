DESCRIPTION = "Apache logging framework for C++ library"
SECTION = "libs"
DEPENDS = "apr apr-util expat gdbm"
LICENSE = "Apache-2.0"
HOMEPAGE = "http://logging.apache.org/log4cxx/"
LIC_FILES_CHKSUM = "file://LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

# PV = "0.10.0"
PR = "r4"

SRC_URI = "http://archive.apache.org/dist/logging/log4cxx/0.10.0/apache-log4cxx-0.10.0.tar.gz \
           file://log4cxx010.patch;patch=1"

#build this:
S = "${WORKDIR}/apache-${PN}-${PV}"

inherit autotools pkgconfig

#do_install() {
#autotools_stage_all
#}

SRC_URI[md5sum] = "b30ffb8da3665178e68940ff7a61084c"
SRC_URI[sha256sum] = "0de0396220a9566a580166e66b39674cb40efd2176f52ad2c65486c99c920c8c"
