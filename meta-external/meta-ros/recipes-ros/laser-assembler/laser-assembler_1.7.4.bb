DESCRIPTION = "Provides nodes to assemble point clouds from either LaserScan or PointCloud messages"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=13;endline=13;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "message-generation sensor-msgs message-filters tf roscpp filters laser-geometry pluginlib"

SRC_URI = "https://github.com/ros-perception/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "26ca6cccc72815f6574443ee8a1f9990"
SRC_URI[sha256sum] = "427b80cb87835a55522c6646a4d7d922e473d349a9476f6ea58c9df53dc93205"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
