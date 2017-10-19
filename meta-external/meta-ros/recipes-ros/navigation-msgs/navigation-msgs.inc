SRC_URI = "https://github.com/ros-planning/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "7da13c0adfe4371bff2de5a9914c1847"
SRC_URI[sha256sum] = "09bba8fb7a60135183830dec4381cd3187244794aab9759ac50bbc6007e04a61"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "navigation_msgs"
