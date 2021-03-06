DESCRIPTION = "This is a set of tools for recording from and playing back ROS message without \
relying on the ROS client library."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=9;endline=9;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost bzip2 console-bridge cpp-common roscpp-serialization roscpp-traits roslz4 rostime"
DEPENDS_class-nativesdk = "nativesdk-boost bzip2 nativesdk-console-bridge nativesdk-cpp-common nativesdk-roscpp-serialization nativesdk-roscpp-traits nativesdk-roslz4 nativesdk-rostime nativesdk-lz4"

require ros-comm.inc

ROS_PKG_SUBDIR = "tools"
