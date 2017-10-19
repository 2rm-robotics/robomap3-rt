DESCRIPTION = "A common repository for CMake Modules which are not \
distributed with CMake but are commonly used by ROS packages."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=d566ef916e9dedc494f5f793a6690ba5"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "30a7c51b104d0e059c32c50059356731"
SRC_URI[sha256sum] = "8d95669b37c6b9a3bef85d6c374cfd1d7ea55aef3951b870d135ed91c458a95c"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
