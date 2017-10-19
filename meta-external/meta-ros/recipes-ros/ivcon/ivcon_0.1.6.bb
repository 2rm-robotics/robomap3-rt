DESCRIPTION = "Mesh Conversion Utility used to generate '.iv' files from '.stl' files."
SECTION = "devel"
LICENSE = "GPL"
LIC_FILES_CHKSUM = "file://package.xml;beginline=18;endline=18;md5=162b49cfbae9eadf37c9b89b2d2ac6be"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "bd2eea792abbfaab65c4c3cc39d50f7f"
SRC_URI[sha256sum] = "f880b936c30fe07201fade652f5cd17d058451f92064cc03e726c8848e82387e"

inherit catkin
