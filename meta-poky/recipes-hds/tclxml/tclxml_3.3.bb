DESCRIPTION = "The TclXML project is a collection of tools and libraries for handling XML documents with the Tcl scripting language"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/BSD;md5=3775480a712fc46a69647678acb234cb"
SECTION = "devel/tcltk"
HOMEPAGE = "http://tclxml.sourceforge.net/tclxml.html"
DEPENDS = "tcl-native tcl libxml2 libxslt"

PR = "r1"

SRCREV = "11"
SRC_URI = "svn://svn.code.sf.net/p/tclxml/svn/;module=trunk;protocol=https"

S = "${WORKDIR}/trunk"

inherit autotools-brokensep

EXTRA_OECONF = "--with-tcl=${STAGING_BINDIR_CROSS} --with-tclinclude=${STAGING_INCDIR}/tcl8.6/  --with-xml2-config=${STAGING_BINDIR_CROSS}/xml2-config"

#do_configure() {
#	gnu-configize
#	oe_runconf
#}

#do_compile() {
#    oe_runmake
#}

FILES_${PN} += "/usr/bin /usr/lib/TclxmlConfig.sh /usr/lib/Tclxml3.3/*.so* /usr/lib/Tclxml3.3/*.tcl"
FILES_${PN}-dbg +="/usr/lib/Tclxml3.3/.debug/*"
FILES_${PN}-staticdev +="/usr/lib/Tclxml3.3/*.a" 
