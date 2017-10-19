DESCRIPTION = "This library contains wrappers for generating floating point values, integers, quaternions using boost libraries."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=12;endline=12;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost"

SRC_URI = "https://github.com/ros-planning/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "95cc49dbbdcb977785c49da42e7e6473"
SRC_URI[sha256sum] = "9377e6b05d5336b8293b9dc45bc8e7bcdee90becb0e0be31caa35c8d970807a8"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
