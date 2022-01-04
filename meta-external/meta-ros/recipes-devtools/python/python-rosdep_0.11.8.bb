DESCRIPTION = "rosdep package manager abstraction tool for ROS"
SECTION = "devel/python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://PKG-INFO;beginline=8;endline=8;md5=e910b35b0ef4e1f665b9a75d6afb7709"
SRCNAME = "rosdep"

DEPENDS = "python-nose"

SRC_URI = "https://pypi.python.org/packages/source/r/rosdep/rosdep-${PV}.tar.gz"
SRC_URI[md5sum] = "28a18dd497599010bb01107f832e7831"
SRC_URI[sha256sum] = "8185ee4114f860140672c9f311e11b0eb5b2dcfbb6eb524bb7ff03b6ceb887ae"

S = "${WORKDIR}/${SRCNAME}-${PV}"

RDEPENDS_${PN} += "python-catkin-pkg python-rospkg python-pyyaml python-netclient"

inherit setuptools

BBCLASSEXTEND += "native nativesdk"
