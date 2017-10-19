DESCRIPTION = "control_msgs contains base messages and actions useful for controlling robots. It \
provides representations for controller setpoints and joint and cartesian trajectories."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=12;endline=12;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "message-generation std-msgs trajectory-msgs geometry-msgs actionlib-msgs"

SRC_URI = "https://github.com/ros-controls/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "d1caa38cf7d6dd5966e262685d4916df"
SRC_URI[sha256sum] = "8f74d76d620cbe952ad31d37c95a595e05c23ef74ef8397151a0eece0bbbfb85"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin
