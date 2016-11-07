LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/LGPL-2.0;md5=9427b8ccf5cf3df47c29110424c9641a"
PR = "r2"

SRC_URI = "http://downloads.sourceforge.net/project/nmea/NmeaLib/nmea-0.5.x/nmealib-0.5.3.zip \
		file://Makefile \
		file://additional_sentances.patch;pnum=0 \
		"

SRC_URI[md5sum] = "147fd471e1d05191196b6ec69145d1ca"
SRC_URI[sha256sum] = "41e9fbb8fd5cb1836ff727355b3debe98662b39beb7493af9b78bc4bd4a2ad7d"

DEPENDS = "automake"
inherit autotools-brokensep
S = "${WORKDIR}/nmealib"

do_compile_prepend() {
	cp ../Makefile ./
}

#BBCLASSEXTEND = "nativesdk"

PACKAGES = "${PN}-dbg ${PN}-dev ${PN}"
FILES_${PN} += "/usr/lib/*.so*"
FILES_${PN}-dev += "/usr/lib/*.so /usr/include/*"
FILES_${PN}-dbg += "/usr/src/debug/* /usr/lib/.debug/*"
