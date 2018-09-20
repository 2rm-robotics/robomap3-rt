DESCRIPTION = "interactive_markers"
SECTION = "devel"
LICENSE = "willow_garage"
LIC_FILES_CHKSUM = "file://${WORKDIR}/interactive_markers-${PV}/src/interactive_marker_client.cpp;beginline=1;endline=30;md5=470bfce5463dff0dda166c8ace1f6cc3"

SRC_URI = "https://github.com/ros-visualization/interactive_markers/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "d70f33d95223e66b157a6543bac55711"
SRC_URI[sha256sum] = "85f9fc72045cac4ef873f80fca9ce2d7dcfee8762024dd07427431b32760551a"

inherit catkin

S="${WORKDIR}/interactive_markers-${PV}"

RDEPENDS_${PN} = "rosconsole roscpp rostime tf roscpp-serialization boost-thread boost-system"
