SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "19f0e9c037e80d5ba1fa3b2838d69142"
SRC_URI[sha256sum] = "693f5ed4fbaf537f104f7d279e267af5046b017126e6e2dd3ecb999192af80a7"

ROS_PKG_SUBDIR ?= ""
S = "${WORKDIR}/${ROS_SP}/${ROS_PKG_SUBDIR}/${ROS_BPN}"

inherit catkin

ROS_SPN = "ros_comm"
