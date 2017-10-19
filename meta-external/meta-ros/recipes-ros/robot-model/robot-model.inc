SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "09882288e26a1655ad95c00c6bd9db28"
SRC_URI[sha256sum] = "e40bd4519fd4010bcde0cb179611a19c04c3a32073830a675090671b4cea0ee5"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "robot_model"
