DESCRIPTION = "ROS-ified version of gmapping SLAM."
SECTION = "devel"
LICENSE = "CC-BY-NC-SA-2.0"
LIC_FILES_CHKSUM = "file://package.xml;beginline=6;endline=6;md5=11e24f757f025b2cbebd5b14b4a7ca19"

SRC_URI = "https://github.com/ros-perception/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "9a3c7cd4edb0643c72b0580c59260f5b"
SRC_URI[sha256sum] = "4c497772104d2821325447b6782cc921a1a8639a0f46b90cdf68d5c95ecb0315"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
