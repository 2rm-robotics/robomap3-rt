DESCRIPTION = "Utilities for generating documentation from source code"
HOMEPAGE = "http://www.doxygen.org/"
SECTION = "console/utils"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=b380c86cea229fa42b9e543fc491f5eb"

DEPENDS = "flex-native bison-native"

SRC_URI = "https://downloads.sourceforge.net/project/${BPN}/rel-${PV}/${BP}.src.tar.gz \
	   file://patch-flex_2_6_0.diff;striplevel=0"
SRC_URI[md5sum] = "3d1a5c26bef358c10a3894f356a69fbc"
SRC_URI[sha256sum] = "d4ab6e28d4d45d8956cad17470aade3fbe2356e8f64b92167e738c1887feccec"

PR = "r1"

# Avoid compile problems when --disable-static does not exists
DISABLE_STATIC = ""

EXTRA_OECONF = "--prefix ${prefix}"

do_configure () {
	echo "prefix = ${prefix}"
	echo "extra = ${EXTRA_OECONF}"
	./configure ${EXTRA_OECONF}

	# TODO on rebuilds will repeatedly append.  Change logic to include a
	# separate file and overwrite that file?
        echo "TMAKE_CC=${CC}" >> tmake/lib/linux-g++/tmake.conf
        echo "TMAKE_CXX=${CXX}" >> tmake/lib/linux-g++/tmake.conf
        echo "TMAKE_CFLAGS=${CFLAGS}" >> tmake/lib/linux-g++/tmake.conf
        echo "TMAKE_CXXFLAGS=${CXXFLAGS}" >> tmake/lib/linux-g++/tmake.conf
        echo "TMAKE_LINK=${CXX}" >> tmake/lib/linux-g++/tmake.conf
        echo "TMAKE_LFLAGS=${LDFLAGS}" >> tmake/lib/linux-g++/tmake.conf
}

do_install() {
	oe_runmake install DESTDIR=${D} MAN1DIR=share/man/man1
}

BBCLASSEXTEND = "native nativesdk"

