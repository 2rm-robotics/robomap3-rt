SUMMARY = "Media controller control application"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://COPYING.LIB;md5=d749e86a105281d7a44c2328acebc4b0"

DEPENDS = "linux-libc-headers"

SRC_URI = "git://git.ideasonboard.org/media-ctl.git;protocol=git \
	 file://configure.patch \
"

#git rev is choosen to fit with ardrone2 parallel-stream.sh script
SRCREV = "b5a1c3048a028965efe5af46801b9c14959703a2"

PV = "0.0.1"
PR = "r4"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = "--with-kernel-headers=${STAGING_KERNEL_DIR}"

do_configure_prepend(){
	if [ -f ${S}/configure.in ];then
		mv ${S}/configure.in ${S}/configure.ac
	fi
}

PACKAGES =+ "libmediactl libv4l2subdev"
FILES_libmediactl = "${libdir}/libmediactl${SOLIBS}"
FILES_libv4l2subdev = "${libdir}/libv4l2subdev${SOLIBS}"

