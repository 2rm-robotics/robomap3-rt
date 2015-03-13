DESCRIPTION = "Poky feed configuration"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

PR = "r1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
INHIBIT_DEFAULT_DEPS = "1"

SRC_URI = " file://isee.biz.repo \
	file://macros.jffs2"

do_install () {
	install -d ${D}${sysconfdir}/zypp/repos.d/
	install -m 0644  ${WORKDIR}/isee.biz.repo ${D}${sysconfdir}/zypp/repos.d/
	# due lacking support for MMAP read/write on jffs2 filesystem, it
	# requires a workaround
	install -d ${D}${sysconfdir}/rpm/
	install -m 0755 ${WORKDIR}/macros.jffs2 ${D}${sysconfdir}/rpm/
}

FILES_${PN} = "${sysconfdir} "
