DESCRIPTION = "x loader"
HOMEPAGE = "http://www.isee.biz"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "bootloader"

COMPATIBLE_MACHINE = "overo"

VER = "${PV}-1"
PR = "r0"

SRC_URI = "https://uav.hds.utc.fr/src/overo/4.0/x-load.bin.ift"
SRC_URI[md5sum] = "3accf36d7735c84fc9a6a0e9c5d8d74a"
SRC_URI[sha256sum] = "b8e453c3dd551aee89b4c3d8914e6d3b68304356931e4a4dbfe9869b7ffc7b47"

do_install() {
	install -d ${D}/boot
	cp ${WORKDIR}/x-load.bin.ift ${D}/boot/MLO
}

FILES_${PN} = "/boot"
