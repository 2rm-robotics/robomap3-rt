SUMMARY = "chroot init script"
DESCRIPTION = "basic init for chrooted devices"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"

SRC_URI = "file://chroot_init \
"

do_install () {
	install -d ${D}${sysconfdir}/
	install -m 0755 ${WORKDIR}/chroot_init ${D}${sysconfdir}/
}
