DESCRIPTION = "The Snack Sound Toolkit is designed to be used with a scripting language such as Tcl/Tk or Python"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../BSD.txt;md5=345e7fd49911f277e63d436abf6145b1"
SECTION = "devel/tcltk"
HOMEPAGE = "http://www.speech.kth.se/snack/"
DEPENDS = "tcl-native tcl tk alsa-lib"

PR = "r3"

SRC_URI = "http://www.speech.kth.se/snack/dist/snack2.2.10.tar.gz \
	file://math.patch \
	file://Makefile.patch \
"

SRC_URI[md5sum] = "98da0dc73599b3a039cba1b7ff169399"
SRC_URI[sha256sum] = "4bfe764547ab92ba58f43b77366dbb7c7b3512d65a27cdbf9e585a9cb64ce81e"

S = "${WORKDIR}/snack2.2.10/unix"

inherit autotools-brokensep

EXTRA_OECONF = "--with-tcl=${STAGING_BINDIR_CROSS} --with-tk=${STAGING_LIBDIR} --enable-alsa"

# Handle patches manually as the defaut do_patch expects ${S} to be the source root directory
do_patch() {
	(cd ${WORKDIR} && shopt -s nullglob && \
	 for p in *.{patch,diff}; do patch -f -p0 < $p && rm -f $p; done) || exit 1
}
	
do_configure() {
	gnu-configize
	oe_runconf
	#remove host directory from tclconfig.sh and tkconfig.sh
	sed -i 's/-L\/usr\/lib//g' Makefile
	sed -i 's/-L=\/usr\/lib//g' Makefile	
}

do_compile() {
    oe_runmake
}

FILES_${PN} += "/usr/lib/snack2.2/*so* /usr/lib/snack2.2/*.tcl"
FILES_${PN}-dbg +="/usr/lib/snack2.2/.debug/*" 
