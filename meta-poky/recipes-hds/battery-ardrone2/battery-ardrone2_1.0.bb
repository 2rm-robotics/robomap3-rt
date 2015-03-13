DESCRIPTION = "battery voltage measurement for ardrone2"
SECTION = "programs"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

PROVIDES = "virtual/battery-voltage"
 
SRC_URI = "file://main.c"
 
S = "${WORKDIR}"

COMPATIBLE_MACHINE = "(ardrone2|parrot_chroot)"

PR = "r0"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} main.c -o voltage
}
 
do_install() {
    install -d ${D}${bindir}
    install -m 0755 voltage ${D}${bindir}
}
