SUMMARY = "a scheduler benchmark/stress test program"
SECTION = "benchmark"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/GPL-2.0;md5=801f80980d171dd6425610833a22dbe6"

SRC_URI = "file://hackbench.c"

S = "${WORKDIR}"

do_compile() {
	${CC} -g -Wall hackbench.c -lpthread -o hackbench
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 hackbench ${D}${bindir}
}

