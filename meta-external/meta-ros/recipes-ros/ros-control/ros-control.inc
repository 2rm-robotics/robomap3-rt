SRC_URI = "https://github.com/ros-controls/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "d145372bc0df8c60a4ac63a1fea0cd31"
SRC_URI[sha256sum] = "72cf017ce1de3a645e62c7f1c034c4acaa2d3bc1d81da73b9c3b6027f89a48e8"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "ros_control"
