DESCRIPTION = "CMake lint commands for ROS packages"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=11;endline=11;md5=d566ef916e9dedc494f5f793a6690ba5"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "c4d4809768121be1dc710c637cfe6e39"
SRC_URI[sha256sum] = "ef220c5d03834f07ea458dc07604b518458aeef96e79e5bb804b3089082830ce"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin

RDEPENDS_${PN} += "bash"
