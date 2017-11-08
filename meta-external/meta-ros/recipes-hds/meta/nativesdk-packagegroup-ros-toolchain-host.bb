SUMMARY = "Host packages for the ROS host toolchain"
PR = "r1"
LICENSE = "MIT"

inherit packagegroup nativesdk

#PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

DEPENDS = "\
    nativesdk-catkin\
"

RDEPENDS_${PN} = "\
    nativesdk-rosbash \
    nativesdk-rosboost-cfg \
    nativesdk-rosbuild \
    nativesdk-rosclean \
    nativesdk-roscreate \
    nativesdk-roslang \
    nativesdk-roslib \
    nativesdk-rosmake \
    nativesdk-rosunit \
    nativesdk-rospack \
    nativesdk-cmake-modules \
    nativesdk-python-rosdep \
    nativesdk-python-empy \
    nativesdk-python-future \
    nativesdk-python-pyyaml \
    nativesdk-python-rosdistro \
    nativesdk-python-rospkg \
    nativesdk-python-netifaces \
    nativesdk-python-nose \
    nativesdk-python-modules \
    nativesdk-rosbag \
    nativesdk-rosconsole \
    nativesdk-rosgraph \
    nativesdk-rosgraph-msgs \
    nativesdk-roslaunch \
    nativesdk-rosmaster \
    nativesdk-rosmsg \
    nativesdk-rosnode \
    nativesdk-rosparam \
    nativesdk-rosservice \
    nativesdk-rostest \
    nativesdk-rostopic \
    nativesdk-roswtf \
    nativesdk-rospack \
    nativesdk-xacro \
    nativesdk-rostime \
    nativesdk-log4cxx \
    nativesdk-roslint \
    nativesdk-genpy \
    nativesdk-genmsg \
    nativesdk-genlisp \
    nativesdk-gencpp \
    nativesdk-message-generation \
    nativesdk-message-runtime \
    nativesdk-std-msgs \
    nativesdk-console-bridge \
    "
