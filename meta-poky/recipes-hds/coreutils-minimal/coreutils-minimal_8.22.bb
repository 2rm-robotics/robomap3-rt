SUMMARY = "The basic file, shell and text manipulation utilities"
DESCRIPTION = "The GNU Core Utilities provide the basic file, shell and text \
manipulation utilities. These are the core utilities which are expected to exist on \
every system."
HOMEPAGE = "http://www.gnu.org/software/coreutils/"
BUGTRACKER = "http://debbugs.gnu.org/coreutils"
LICENSE = "GPLv3+"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504\
                    file://src/ls.c;beginline=5;endline=16;md5=38b79785ca88537b75871782a2a3c6b8"
DEPENDS = "gmp libcap"


inherit autotools gettext texinfo

SRC_URI = "${GNU_MIRROR}/coreutils/coreutils-${PV}.tar.xz \
           file://remove-usr-local-lib-from-m4.patch \
           file://dummy_help2man.patch \
           file://fix-for-dummy-man-usage.patch \
           file://fix-selinux-flask.patch \
          "

SRC_URI[md5sum] = "8fb0ae2267aa6e728958adc38f8163a2"
SRC_URI[sha256sum] = "5b3e94998152c017e6c75d56b9b994188eb71bf46d4038a642cb9141f6ff1212"

S = "${WORKDIR}/coreutils-${PV}"

EXTRA_OECONF_class-target = "--enable-install-program=arch --libexecdir=${libdir}"

# acl is not a default feature
#
PACKAGECONFIG_class-target ??= "${@bb.utils.contains('DISTRO_FEATURES', 'acl', 'acl', '', d)}"

# with, without, depends, rdepends
#
PACKAGECONFIG[acl] = "--enable-acl,--disable-acl,acl,"

bindir_progs = "head timeout"
base_bindir_progs = "dd"

# Let aclocal use the relative path for the m4 file rather than the
# absolute since coreutils has a lot of m4 files, otherwise there might
# be an "Argument list too long" error when it is built in a long/deep
# directory.
acpaths = "-I ./m4"

# Deal with a separate builddir failure if src doesn't exist when creating version.c/version.h
do_compile_prepend () {
	mkdir -p ${B}/src
}

do_install_append() {
	install -d ${D}${base_bindir}

#keep some programs, part1
	for i in ${base_bindir_progs}; do mv ${D}${bindir}/$i ${D}${base_bindir}/$i.${BPN}; done
	for i in ${bindir_progs}; do mv ${D}${bindir}/$i ${D}/$i; done
	

#remove all others
	rm -rf ${D}${bindir}/*
	rm -rf ${D}${datadir}
	rm -rf ${D}${libdir}

#keep some programs, part2
	for i in ${bindir_progs}; do mv ${D}/$i ${D}${bindir}/$i.${BPN}; done
}

python __anonymous() {
	for prog in d.getVar('base_bindir_progs', True).split():
		d.setVarFlag('ALTERNATIVE_LINK_NAME', prog, '%s/%s' % (d.getVar('base_bindir', True), prog))
}

inherit update-alternatives

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "${bindir_progs} ${base_bindir_progs}"
