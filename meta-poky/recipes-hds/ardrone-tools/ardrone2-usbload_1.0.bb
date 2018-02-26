DESCRIPTION = "tool to send ardrone2-installer images to ardrone2"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

 
SRC_URI = "file://ardrone2-usbload.c"
 
S = "${WORKDIR}"

BBCLASSEXTEND = "native nativesdk"
RDEPENDS_${PN} = "libusb-compat"
RDEPENDS_nativesdk-${PN} = "nativesdk-libusb-compat"
DEPENDS_nativesdk-${PN}= "libusb-compat-dev"

PR = "r0"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} ardrone2-usbload.c -o ardrone2-usbload -I${STAGING_DIR_HOST}/${includedir} -lusb
}
 
do_install() {
    install -d ${D}${bindir}
    install -m 0755 ardrone2-usbload ${D}${bindir}
}
