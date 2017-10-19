DESCRIPTION = "ROS Package Tool"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=6;endline=6;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost cmake-modules python-rospkg-native libtinyxml"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "28ace531cc14e49f84f6380d5fe87124"
SRC_URI[sha256sum] = "d54bf2f82cea3273a34c11d99fed5bb1d78a8e5c86046af425d646ebc94c627c"

inherit catkin

RDEPENDS_${PN} = "python-rosdep python-subprocess"
