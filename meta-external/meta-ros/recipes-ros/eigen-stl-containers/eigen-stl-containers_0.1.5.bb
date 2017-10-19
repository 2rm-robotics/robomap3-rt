DESCRIPTION = "This package provides a set of typedef's that allow using Eigen datatypes in STL containers."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=d566ef916e9dedc494f5f793a6690ba5"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "d94cb515fc9b839fd669e140ac9f4312"
SRC_URI[sha256sum] = "17bbf97e09486e84b3ccd36db1ef20e92dd24fb906717656d4e0eb52ad112366"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
