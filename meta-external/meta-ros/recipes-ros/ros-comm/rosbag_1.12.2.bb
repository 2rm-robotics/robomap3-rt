DESCRIPTION = "This is a set of tools for recording from and playing back to ROS topics.  It is \
intended to be high performance and avoids deserialization and reserialization of the messages."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=10;endline=10;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost cpp-common python-imaging rosbag-storage rosconsole roscpp roscpp-serialization \
    topic-tools xmlrpcpp"
DEPENDS_class-nativesdk += "nativesdk-boost nativesdk-cpp-common nativesdk-python-imaging nativesdk-rosbag-storage nativesdk-rosconsole nativesdk-roscpp nativesdk-roscpp-serialization \
    nativesdk-topic-tools nativesdk-xmlrpcpp"

require ros-comm.inc

ROS_PKG_SUBDIR = "tools"

RDEPENDS_${PN} = "python-compression python-threading python-pyyaml python-io \
    genmsg genpy roslib rospy"
