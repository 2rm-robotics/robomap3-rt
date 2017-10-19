SRC_URI = "https://github.com/ros-drivers/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "727fbcfd95b3b1fe28f695b8c8fb4da8"
SRC_URI[sha256sum] = "6845d7d1449e62b13c5e2f8c33505f6759cb947cdd2dc300537ca499fc862cb8"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "audio_common"
