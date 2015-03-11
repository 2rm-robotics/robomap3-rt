SUMMARY = "a deliberately simple workload generator for POSIX systems"
HOMEPAGE = "http://people.seas.harvard.edu/~apw/stress/"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "http://people.seas.harvard.edu/~apw/stress/stress-1.0.4.tar.gz"
SRC_URI[md5sum] = "890a4236dd1656792f3ef9a190cf99ef"
SRC_URI[sha256sum] = "057e4fc2a7706411e1014bf172e4f94b63a12f18412378fca8684ca92408825b"

S = "${WORKDIR}/stress-1.0.4"

inherit autotools

