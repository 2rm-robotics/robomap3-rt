DESCRIPTION = "filelib"
SECTION = "libs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r0"

SRC_URI = "file://err.c \
	file://err.h \
	file://ioctl.h \
	file://io_hdfile.c \
	file://io_hdfile.h \
	file://CMakeLists.txt"

S = "${WORKDIR}"

inherit cmake
