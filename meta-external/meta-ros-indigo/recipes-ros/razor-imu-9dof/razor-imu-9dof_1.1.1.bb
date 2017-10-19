DESCRIPTION = "Provides a ROS driver for the Sparkfun Razor IMU 9DOF. \
It also provides Arduino firmware that runs on the Razor board, and which must be \
installed on the Razor board for the system to work. \
A node which displays the attitude (roll, pitch and yaw) of the Razor board \
(or any IMU) is provided for testing."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=15;endline=15;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "dynamic-reconfigure ${PYTHON_PN}-rospkg"

SRC_URI = "https://github.com/KristofRobot/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "4712cbe1d9d8bacd9eebfd51825ada36"
SRC_URI[sha256sum] = "9917e7ae713016c63786511736bbdc1b671a9685ea0ddfc87839251ca7513c95"

S = "${WORKDIR}/${ROS_SP}"

inherit catkin

RDEPENDS_${PN} = "rospy tf sensor-msgs ${PYTHON_PN}-pyserial"
