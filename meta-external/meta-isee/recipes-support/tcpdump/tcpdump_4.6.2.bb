SUMMARY = "A sophisticated network protocol analyzer"
HOMEPAGE = "http://www.tcpdump.org/"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://LICENSE;md5=1d4b0366557951c84a94fabe3529f867"
SECTION = "console/network"
DEPENDS = "libpcap"

SRC_URI = " \
    http://www.tcpdump.org/release/${BP}.tar.gz \
    file://configure.patch \
    file://unnecessary-to-check-libpcap.patch \
    file://tcpdump-configure-dlpi.patch \
    file://tcpdump-cross-getaddrinfo.patch \
    file://add-ptest.patch \
    file://run-ptest \
"
SRC_URI[md5sum] = "74d0d3728b3cdc60db872d842e7f1598"
SRC_URI[sha256sum] = "524ee4d8e83a6c663f6879004216a9a5bcb1c68b11920d653eb87b79d008e0b8"
export LIBS=" -lpcap"

inherit autotools-brokensep ptest
CACHED_CONFIGUREVARS = "ac_cv_linux_vers=${ac_cv_linux_vers=2}"

PACKAGECONFIG ??= "openssl ipv6"
PACKAGECONFIG[openssl] = "--with-crypto=yes, --without-openssl --without-crypto, openssl"
PACKAGECONFIG[ipv6] = "--enable-ipv6, --disable-ipv6,"

EXTRA_AUTORECONF += " -I m4"

do_configure_prepend() {
    mkdir -p ${S}/m4
    if [ -f aclocal.m4 ]; then
        mv aclocal.m4 ${S}/m4
    fi
    # AC_CHECK_LIB(dlpi.. was looking to host /lib
    sed -i 's:-L/lib::g' ./configure.in
}
do_configure_append() {
    sed -i 's:-L/usr/lib::' ./Makefile
    sed -i 's:-Wl,-rpath,${STAGING_LIBDIR}::' ./Makefile
    sed -i 's:-I/usr/include::' ./Makefile
}

do_install_append() {
    # tcpdump 4.0.0 installs a copy to /usr/sbin/tcpdump.4.0.0
    rm -f ${D}${sbindir}/tcpdump.${PV}
}

do_compile_ptest() {
	oe_runmake buildtest-TESTS
}
