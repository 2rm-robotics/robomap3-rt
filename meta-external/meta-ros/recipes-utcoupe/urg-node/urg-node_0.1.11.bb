DESCRIPTION = "ROS wrapper for the Hokuyo urg_c library."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/urg_node-${PV}/src/getID.cpp;beginline=1;endline=33;md5=c6915cbaf62a78a3a3b483f3dd7c96e8"

SRC_URI = "https://github.com/ros-drivers/urg_node/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "60b55c1c716a9d02f47ddb700ea7266a"
SRC_URI[sha256sum] = "b2c9fb64d3a84eda7d034b70ea4b71a3d95028e5480624d5039e0c0af1f62f8a"

inherit catkin

S="${WORKDIR}/urg_node-${PV}"

DEPENDS="urg-c"
