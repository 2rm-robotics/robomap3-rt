DESCRIPTION = "encfs"
PR = "r1"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"

DEPENDS = "libtool automake boost fuse rlog"

SRC_URI = "https://github.com/vgough/encfs/archive/v1.7.4.tar.gz \
	file://fuse.patch"

SRC_URI[md5sum] = "9c69266004419502e69b2da6138ced35"
SRC_URI[sha256sum] = "bc4ea171111e7f1e2e34e6fd64cd5dc0100e6a41c0157288602e663441b36f0a"


EXTRA_OECONF = "--with-boost-libdir=${STAGING_LIBDIR} --with-boost=${STAGING_DIR_TARGET}"

inherit autotools-brokensep gettext pkgconfig
