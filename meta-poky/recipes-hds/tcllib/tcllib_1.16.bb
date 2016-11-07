DESCRIPTION = "This is the core development home for the tcllib standardized Tcl library"
LICENSE = "Ajuba Solutions"
LIC_FILES_CHKSUM = "file://license.terms;md5=1686715e5b7e0689a0dec6f1bc6fe2c2"
SECTION = "devel/tcltk"
HOMEPAGE = "http://tcllib.sourceforge.net/"
DEPENDS = "tcl-native"

PR = "r0"

SRC_URI = "http://sourceforge.net/projects/tcllib/files/tcllib/1.16/tcllib-1.16.tar.bz2"

SRC_URI[md5sum] = "e13012a41b495d260d8ea24794659108"
SRC_URI[sha256sum] = "033334306d3ffc499c8830d393bf7528227e49c68077b3e9247c4171b15be012"

S = "${WORKDIR}/Tcllib-${PV}"

inherit autotools

FILES_${PN} = "/usr/lib/${PN}${PV}/* /usr/bin/*"
