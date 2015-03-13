SUMMARY = "Utility to display or set the kernel time variables"
DESCRIPTION = "This package provides the adjtimex utility, the adjtimexconfig script and the corresponding startup scripts"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"
PR = "r1"

SRC_URI = "http://ftp.debian.org/debian/pool/main/a/adjtimex/adjtimex_1.29.orig.tar.gz \
	   file://adjtimex.init \
 	   file://adjtimex.default \
	   file://adjtimexconfig"

SRC_URI[md5sum] = "7ff7731baf829fdf6ad9af963a526cda"
SRC_URI[sha256sum] = "04b9e8b66e77276ed07e78de89af37fd1aa12725923de853480827c4fafd176a"

DEPENDS = "automake"

INITSCRIPT_NAME = "adjtimex"
INIISCRIPT_PARAMS = "defaults"

CONFFILES_${PN} = "${sysconfdir}/default/adjtimex"

S = "${WORKDIR}/${PN}-${PV}"

inherit autotools-brokensep update-rc.d

# Override the do_install - Another solution would be to patch the configure.in to
# work with libtool.
do_install() {
    install -d ${D}${sysconfdir}/init.d
    install -m 755 ${WORKDIR}/adjtimex.init ${D}${sysconfdir}/init.d/adjtimex
    install -d ${D}${sysconfdir}/default
    install -m 644 ${WORKDIR}/adjtimex.default ${D}${sysconfdir}/default/adjtimex
    install -d ${D}${sbindir}
    install -m 755 ${WORKDIR}/adjtimexconfig ${D}${sbindir}
    install -d ${D}${base_sbindir}
    install -m 755 adjtimex ${D}${base_sbindir}
}








