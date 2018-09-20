DESCRIPTION = "Dynamixel SDK"
SECTION = "devel"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${WORKDIR}/DynamixelSDK-3.5.3/LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "https://github.com/ROBOTIS-GIT/DynamixelSDK/archive/3.5.3.tar.gz"
SRC_URI[md5sum] = "1c8693adaaa90cf6e3168a562f1a48e4"
SRC_URI[sha256sum] = "70fdfcbc187717bd28e18d5c7a0bd2e4be24506c84b388ab41c915bd90e259ba"

inherit catkin

S="${WORKDIR}/DynamixelSDK-3.5.3/c++"
