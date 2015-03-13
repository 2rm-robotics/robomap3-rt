DESCRIPTION = "encfs"
PR = "r1"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libtool automake boost fuse rlog"

SRC_URI = "http://encfs.googlecode.com/files/encfs-1.7.4.tgz \
	file://fuse.patch"

SRC_URI[sha256sum] = "282ef0f04f2dd7ba3527b45621fab485b7cc510c2ceee116600d0348dc2170a8"
SRC_URI[md5sum] = "ac90cc10b2e9fc7e72765de88321d617"

EXTRA_OECONF = "--with-boost-libdir=${STAGING_LIBDIR} --with-boost=${STAGING_DIR_TARGET}"

inherit autotools-brokensep gettext pkgconfig
