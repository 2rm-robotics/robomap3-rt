SRC_URI = "https://github.com/ros-perception/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "72a3515177f049cd00b91996f35f5495"
SRC_URI[sha256sum] = "ad0b66e5523b6862ada185f5064b2d9979b28d46e2b2eb42308e260dabae8c83"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "vision_opencv"
