DESCRIPTION = "Packaging for URG Helper / URG Widget / URG Library / urg_c"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/urg_c-${PV}/current/COPYRIGHT.txt;md5=999342bef191ffe25d4c5b0db6ec3fd1"

SRC_URI = "https://github.com/ros-drivers/urg_c/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "e52ba5a6abde224d418ed410062439ef"
SRC_URI[sha256sum] = "9d1cd3cb8eafaec29d267196b375b33fa4f3ee035cd3f52888122a78f54e483f"

inherit catkin

S="${WORKDIR}/urg_c-${PV}"
