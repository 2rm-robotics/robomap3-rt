DESCRIPTION = "Package modeling the build-time dependencies for generating language bindings of messages."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=7;endline=7;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "gencpp geneus genlisp gennodejs genmsg genpy"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "ad1a4efafc35318e99a8acae23d56301"
SRC_URI[sha256sum] = "e47304c394acd6594403f10cfd52930cab6fb436213839e91bedad5b90a4c186"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
