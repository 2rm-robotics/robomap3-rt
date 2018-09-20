DESCRIPTION = "Converts representations of sensor_msgs/LaserScan and sensor_msgs/MultiEchoLaserScan"
SECTION = "devel"
LICENSE = "willow_garage"
LIC_FILES_CHKSUM = "file://${WORKDIR}/laser_proc-${PV}/src/laser_proc.cpp;beginline=1;endline=30;md5=3a5bf2a5d75457c265053d1994324d85"

SRC_URI = "https://github.com/ros-perception/laser_proc/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "14eb01b63a41e416a15f8a3c16887999"
SRC_URI[sha256sum] = "9dde874a6ae9de30ea59e37aa0d962a35e4da3ebed7e5a639225b901fe4523bd"

inherit catkin

S="${WORKDIR}/laser_proc-${PV}"

RDEPENDS_${PN} = "rosconsole boost-system class-loader nodelet rostime roscpp-serialization console-bridge roscpp"
