SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "9ebf9774de8407f8843021238c940ab7"
SRC_URI[sha256sum] = "979de1c97dd3e8f62db49a41783e2bbc4d869492cd1506711f28ed539131c3c1"


S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "nodelet_core"
