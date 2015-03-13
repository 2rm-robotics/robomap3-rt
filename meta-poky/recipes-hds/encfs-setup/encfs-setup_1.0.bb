SUMMARY = "Automatic opening of encrpyted directory at login"
DSCRIPTION = "This package provides a .profile file for the root user that automatically tries to open an encrypted directory"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PR = "r1"

SRC_URI = "file://encfs-profile \
"

do_install () {
	install -d ${D}/home/root
	install -d ${D}/home/root/.crypt
	install -d ${D}/home/secure
	install -m 644 ${WORKDIR}/encfs-profile ${D}/home/root/.profile
}

RDEPENDS_${PN} = "encfs"

FILES_${PN} = "/home/root/.crypt \
	       /home/secure \
	       /home/root/.profile \
"



