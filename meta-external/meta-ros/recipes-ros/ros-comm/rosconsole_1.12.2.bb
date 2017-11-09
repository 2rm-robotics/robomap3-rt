DESCRIPTION = "ROS console output library."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=6;endline=6;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "apr boost cpp-common log4cxx rostime rosunit"
DEPENDS_class-nativesdk += "nativesdk-apr nativesdk-boost nativesdk-cpp-common nativesdk-log4cxx nativesdk-rostime nativesdk-rosunit"

require ros-comm.inc

ROS_PKG_SUBDIR = "tools"
