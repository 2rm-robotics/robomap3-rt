DESCRIPTION = "tf2_web_republisher"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/tf2_web_republisher-${PV}/LICENSE;md5=95566b67f073443d41ac08c31eb9de26"

SRC_URI = "https://github.com/RobotWebTools/tf2_web_republisher/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "59e13af28f16fc454a56bd69958c3c82"
SRC_URI[sha256sum] = "a0a6278937fe72eb6e30b62ad39c8f52f9cf5ddc6b61e43490d0d63749037a29"

inherit catkin

S="${WORKDIR}/tf2_web_republisher-${PV}"

RDEPENDS_${PN} = "rosconsole boost-system rostime tf2-ros tf2 roscpp-serialization actionlib boost-thread roscpp"
