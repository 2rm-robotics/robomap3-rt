DESCRIPTION = "The processing_lidar_objects package"
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/git/src/obstacle_extractor.cpp;beginline=1;endline=30;md5=6a6a60546777d85cc6a31cdae98294e3"

SRC_URI = "git://github.com/utcoupe/obstacle_detector.git;branch=without_rviz"
SRCREV = "8c1d22691e94f7ce6771c4c868b34dc4874412bb"


inherit catkin

S="${WORKDIR}/git"
