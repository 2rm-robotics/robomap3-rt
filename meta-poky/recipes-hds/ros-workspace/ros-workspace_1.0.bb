SUMMARY = "Miscellaneous files to make ROS work."
DESCRIPTION = "This package provides all the files needed to launch ROS process."
SECTION = "base"
PR = "r0"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI += "file://dot.catkin"

#S = "${WORKDIR}"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_install() {
	install -m 0755 -d ${D}/home/root/ros_packages
	install -m 0755 -d ${D}/opt/ros/${ROSDISTRO}/

	install -m 0644 ${WORKDIR}/dot.catkin ${D}/opt/ros/${ROSDISTRO}/.catkin
	install -m 0644 ${WORKDIR}/dot.catkin ${D}/home/root/ros_packages/.catkin
}

FILES_${PN} = " \
	/opt/ros/${ROSDISTRO}/.catkin \
	/home/root/ros_packages/.catkin \
	"

