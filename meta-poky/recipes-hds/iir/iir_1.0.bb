SECTION = "libs"
DESCRIPTION = "iir"
PR = "r7"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://COPYING;md5=e53b77ffc086d81addcf475344cbddb3"

SRCREV = "2903146bc69e527f16371a9c707f5f5866d54011"
SRC_URI = "git://github.com/berndporr/iir1.git;protocol=https \
		file://cascade_public.patch"

S = "${WORKDIR}/git"

inherit autotools

do_compile() {
    oe_runmake 'DESTDIR=${D}'
}

do_install_append() {
	rm -rf ${D}/${libdir}/iir
}



BBCLASSEXTEND = "native nativesdk"
