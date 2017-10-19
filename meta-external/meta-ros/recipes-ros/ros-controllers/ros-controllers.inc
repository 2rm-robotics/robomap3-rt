SRC_URI = "https://github.com/ros-controls/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "70f7913f9ef1e43cd077ec60d1c4aa39"
SRC_URI[sha256sum] = "c11b5cb6c5cf1f3078595dfd94c891f98a64f5af8f31491e3a0375f09a0d10af"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "ros_controllers"
