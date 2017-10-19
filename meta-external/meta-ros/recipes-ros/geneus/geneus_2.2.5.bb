DESCRIPTION = "EusLisp ROS message and service generators"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "genmsg"

SRC_URI = "https://github.com/jsk-ros-pkg/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "0f3ff42e23e8805c6205d0b6c5c80e04"
SRC_URI[sha256sum] = "fc18dcca7185623b7059936e938bd4a93e3c8fd6a5b7f0eb80cf88625e0df49b"

inherit catkin
