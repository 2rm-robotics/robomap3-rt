DESCRIPTION = "Just do a firmware release"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

require conf/distro/release.inc

PR = "r2.${ISEE_SUBVERSION}"

EXCLUDE_FROM_WORLD = "1"

FWIMAGE = "demo-image-sato"
FWFILE = "${FWIMAGE}-${MACHINE}.tar.bz2"
FWPATH = "igep_firmware-yocto-${DISTRO_VERSION}-${ISEE_SUBVERSION}${EXTRAVERSION}"

DEPLOY_DIR_BINARY = "${DEPLOY_DIR}/binary"

SRCREV = "465ad82d6ad6514f8821f6f9e5f25ce978898e53"

SRC_URI = "git://git.isee.biz/pub/scm/igep-tools.git;protocol=git \
	file://README \
	file://CHANGELOG \
"

S = "${WORKDIR}/git"

do_install[depends] += "${FWIMAGE}:do_rootfs"
do_install[nostamp] = "1"
do_install() {
	install -d ${D}/${FWPATH}
	install -m 0644 ${WORKDIR}/README ${D}/${FWPATH}/README
	install -m 0644 ${WORKDIR}/CHANGELOG ${D}/${FWPATH}/CHANGELOG
	install -m 0644 ${DEPLOY_DIR_IMAGE}/${FWFILE} ${D}/${FWPATH}
	install -m 0644 ${S}/scripts/e-functions ${D}/${FWPATH}
	install -m 0755 ${S}/scripts/igep-media-create ${D}/${FWPATH}
}

do_populate_firmware () {
	install -d ${DEPLOY_DIR_BINARY}
	# Package it up
	cd ${D}
	tar -cj --file=${DEPLOY_DIR_BINARY}/${FWPATH}.tar.bz2 ${FWPATH} 
}

do_populate_firmware[nostamp] = "1"
addtask do_populate_firmware after do_install before do_build 

do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"
do_populate_sysroot[noexec] = "1"
do_package[noexec] = "1"
do_package_write[noexec] = "1"
do_package_write_ipk[noexec] = "1"
do_package_write_deb[noexec] = "1"
do_package_write_rpm[noexec] = "1"
