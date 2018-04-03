DESCRIPTION = "udt"
SECTION = "libs"
PN ="udt"
PR = "r8"

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=567e8c8da655afbe9ad8ec73696cc31e"

SRC_URI = "http://downloads.sourceforge.net/project/udt/udt/4.11/udt.sdk.4.11.tar.gz \
		file://Makefile.patch \
		file://stack.patch"

SRC_URI[md5sum] = "30b1556e5cf0afe179e40a53a1371b08"
SRC_URI[sha256sum] = "aa25b6d7cbac474ca05b7c7b36f59e9a3cd5c61faed8bf1b7174ac118c3de1db"

S = "${WORKDIR}/udt4/src"

EXTRA_OEMAKE = "-e os=LINUX"
EXTRA_OEMAKE_genericx86-64 = "-e os=LINUX arch=IA32"
EXTRA_OEMAKE_genericx86 = "-e os=LINUX arch=IA32"

do_install_append () {
 	install -d ${D}${libdir}/
	install -d ${D}${includedir}/udt
 	install -m 0755 ${S}/libudt.so* ${D}${libdir}/
	install -m 0644 ${S}/udt.h ${D}${includedir}/udt/
    install -m 0644 ${S}/ccc.h ${D}${includedir}/udt/
    install -m 0644 ${S}/packet.h ${D}${includedir}/udt/
	cd ${D}${libdir}/
	ln -s libudt.so.${PV} libudt.so
}
