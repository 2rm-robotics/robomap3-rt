DESCRIPTION = "Convex Decomposition Tool for Robot Model"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=58e54c03ca7f821dd3967e2a2cd1596e"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "c54a9db2151be29b6f16e5905075628c"
SRC_URI[sha256sum] = "7b9121ed8fc34748eaf80c9b32af7df92f8e8f24966f080754194017d75409f9"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin
