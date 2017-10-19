SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "6688a13b5872eff2b0ec1782fe0dbcd3"
SRC_URI[sha256sum] = "79ad3e36a4db52ec122224c9699b6228c4028b422ddfb7660f41de18b0881de0"

DEPENDS += "cpp-common roscpp-serialization message-generation message-runtime std-msgs"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "common_msgs"
