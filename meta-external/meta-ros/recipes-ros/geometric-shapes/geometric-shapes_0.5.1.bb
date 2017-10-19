DESCRIPTION = "This package contains generic definitions of geometric shapes and bodies."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=10;endline=10;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost shape-msgs shape-tools octomap assimp libeigen qhull console-bridge random-numbers eigen-stl-containers resource-retriever"

SRC_URI = "https://github.com/ros-planning/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "dd5d447926ebedcbe313a298051ea7f2"
SRC_URI[sha256sum] = "b6e94edd138c77be3be50d5b8fcaa3591d56983bcee72dbadeb7b97ccde16ad1"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
