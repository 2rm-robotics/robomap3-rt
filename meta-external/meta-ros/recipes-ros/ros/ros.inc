SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "36a509c9ab7d79998960e8b0040369bd"
SRC_URI[sha256sum] = "2860d84fd72930c847e57ae8a0c6bdc960a010e9c01688759ff1382ec1a74be3"

ROS_PKG_SUBDIR ?= ""
S = "${WORKDIR}/${ROS_SP}/${ROS_PKG_SUBDIR}/${ROS_BPN}"

inherit catkin

ROS_SPN = "ros"
