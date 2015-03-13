DESCRIPTION = "u boot"
HOMEPAGE = "http://www.isee.biz"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
SECTION = "bootloader"

COMPATIBLE_MACHINE = "overo"

VER = "${PV}-1"
PR = "r0"

SRC_URI = "https://uav.hds.utc.fr/src/overo/4.0/u-boot.bin"
SRC_URI[md5sum] = "de5c44a609f1f471291421613c36ce79"
SRC_URI[sha256sum] = "95b72087e6f6c98ed0cee6559fd4e8095da80a696f48604df1c9418c66f1de5d"

do_install() {
	install -d ${D}/boot
	cp ${WORKDIR}/u-boot.bin ${D}/boot/u-boot.bin
}

FILES_${PN} = "/boot"
