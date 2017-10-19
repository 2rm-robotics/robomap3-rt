DESCRIPTION = "This package allows you to publish the state of a robot to tf."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=18;endline=18;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "cmake-modules libeigen kdl-parser orocos-kdl rosconsole roscpp rostime sensor-msgs tf tf2-ros tf2-kdl"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "4e27ed1998cb51612fb13c7db1717fbc"
SRC_URI[sha256sum] = "b4bc99d4ba01ca59dffd81aac99d3420a1035f960fdf5eac17d1aa96790ce28d"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
