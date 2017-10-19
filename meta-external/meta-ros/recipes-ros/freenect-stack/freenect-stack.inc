SRC_URI = "https://github.com/ros-drivers/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "0ef1852bda88529dbee553f362f5cdbb"
SRC_URI[sha256sum] = "27bd18e234e0f96d2ce93ba7e59d3e3de8a2e251c202b618d31cb9bf73ac2901"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "freenect_stack"
