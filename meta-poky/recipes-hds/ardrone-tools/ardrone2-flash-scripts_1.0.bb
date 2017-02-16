SUMMARY = "flash scripts for ardrone2"
DESCRIPTION = "This package provides tools to flash ardrone2"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r0"


COMPATIBLE_MACHINE = "(ardrone2|ardrone2-updater)"

#make it machine specific
PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "file://updater_functions.sh \
           file://update_kernel.sh \
"

SRC_URI_append_ardrone2 = "file://install_updater_rootfs.sh \
                           file://launch_update.sh \
"

SRC_URI_append_ardrone2-updater = "file://update.sh \
"

do_install_ardrone2 () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/*.sh ${D}${bindir}/
}

do_install_ardrone2-updater () {
	install -d ${D}${bindir}
	install -m 0755 ${WORKDIR}/*.sh ${D}${bindir}/
}
