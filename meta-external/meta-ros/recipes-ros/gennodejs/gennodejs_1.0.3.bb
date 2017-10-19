DESCRIPTION = "Javascript ROS message and service generators."
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=3dce4ba60d7e51ec64f3c3dc18672dd3"

DEPENDS = "genmsg"

SRC_URI = "https://github.com/RethinkRobotics-opensource/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "569edadc97ca808b2245da83fbf608b7"
SRC_URI[sha256sum] = "8ed131991e313f234a9fc88367db671a4b3b3b23f397105d94e14eb88d3bb0d6"

inherit catkin
