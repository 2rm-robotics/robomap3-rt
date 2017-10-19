SRC_URI = "https://github.com/ros-planning/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "3c1f3fd96651655504ace12358f87b97"
SRC_URI[sha256sum] = "3974bb52af21cf0a6e4da8eb231b3101b4c5da700793bdac1cdc97e69d6e5cac"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "navigation"
