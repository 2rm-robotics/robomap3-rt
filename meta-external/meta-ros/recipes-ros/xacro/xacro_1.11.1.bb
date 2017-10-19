DESCRIPTION = "Xacro is an XML macro language."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=12;endline=12;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "roslint"

SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "c1e7027ca11dbadd1a19a9af4846e5ae"
SRC_URI[sha256sum] = "eb60c2590e2a8efc6b3d5321ca38755dbf6ddd951bc567afc307d5de3214df1b"

inherit catkin
