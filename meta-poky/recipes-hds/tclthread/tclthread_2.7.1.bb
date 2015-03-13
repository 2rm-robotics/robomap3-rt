DESCRIPTION = "Tcl Thread Package."
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://license.terms;md5=69c1739c291b7bb78d88c7cb3d247177"
SECTION = "devel/tcltk"
HOMEPAGE = "http://sourceforge.net/projects/tcl/files/Thread%20Extension/"
DEPENDS = "tcl-native tcl"

PR = "r0"

SRC_URI = "http://sourceforge.net/projects/tcl/files/Thread%20Extension/${PV}/thread${PV}.tar.gz"

SRC_URI[md5sum] = "86f9e727123f88510d00fac5cdd2a070"
SRC_URI[sha256sum] = "0982027928adcca8a0ea7b47ea41b7d2529b10bb59ccb2d6b9181af7de98bab0"

S = "${WORKDIR}/thread${PV}"

inherit autotools-brokensep

EXTRA_OECONF = "--with-tcl=${STAGING_BINDIR_CROSS} --with-tclinclude=${STAGING_INCDIR}/tcl8.6/"

do_configure() {
	gnu-configize
	oe_runconf
}


do_compile() {
    oe_runmake
}

FILES_${PN} += "/usr/bin /usr/lib/thread${PV}/*so* /usr/lib/thread${PV}/*.tcl"
FILES_${PN}-dbg +="/usr/lib/thread${PV}/.debug/*" 
