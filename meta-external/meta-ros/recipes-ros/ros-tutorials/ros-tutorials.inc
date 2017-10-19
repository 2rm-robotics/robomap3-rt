SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "7e1ba8c70ca5f1a7a9625dfbd2cf7459"
SRC_URI[sha256sum] = "38ed655e914de2990bfcf5f8a25b6ef99f1ed4287033bf153ee6383eeac2842d"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "ros_tutorials"
