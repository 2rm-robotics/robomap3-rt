DESCRIPTION = "ROS package for TeraRanger modules"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${WORKDIR}/teraranger-${PV}/LICENSE;md5=4ed4d37530fa16edfb2299f9069c3465"

SRC_URI = "https://github.com/Terabee/teraranger/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "b2467175342328875dd6375e314d2889"
SRC_URI[sha256sum] = "14ece06be4b62b9dd73de352a39091d8bd53dd55976b35d70f1084fc820ce096"

inherit catkin

S="${WORKDIR}/teraranger-${PV}"

#RDEPENDS_${PN} = "rosconsole roscpp rostime roscpp-serialization dynamic-reconfigure boost-system"
