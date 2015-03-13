DESCRIPTION = "The Tcl UDP extension provides a simple library to support UDP socket in Tcl."
LICENSE = "Columbia University"
LIC_FILES_CHKSUM = "file://license.terms;md5=5f94cd289c546cf308271804775ddc10"
SECTION = "devel/tcltk"
HOMEPAGE = "http://sourceforge.net/projects/tcludp/"
DEPENDS = "tcl-native tcl tcllib"

PR = "r0"

SRC_URI = "http://sourceforge.net/projects/tcludp/files/tcludp/${PV}/${PN}-${PV}.tar.gz \
	file://no_dtplite.patch \
	"

SRC_URI[md5sum] = "945ea7afd1df9e46090733ffbfd724a1"
SRC_URI[sha256sum] = "a8a29d55a718eb90aada643841b3e0715216d27cea2e2df243e184edb780aa9d"

S = "${WORKDIR}/${PN}"

inherit autotools

EXTRA_OECONF = "--with-tcl=${STAGING_BINDIR_CROSS} --with-tclinclude=${STAGING_INCDIR}/tcl8.6"

FILES_${PN} += "/usr/bin /usr/lib/udp1.0.10/*.so*"
FILES_${PN} += "/usr/lib/udp1.0.10/*.tcl"
FILES_${PN}-dbg +="/usr/lib/udp1.0.10/.debug/*" 
