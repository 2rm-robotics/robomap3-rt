DESCRIPTION = "2.6 Linux Kernel for the mambo platform"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_mambo = "1"

COMPATIBLE_MACHINE = "mambo"

inherit kernel autotools-brokensep
DEPENDS +="plftool-native"

SRCREV = "10"
SRC_URI = "svn://devel.hds.utc.fr/svn/mambo_src/trunk;module=linux-${PV};protocol=https"
S = "${WORKDIR}/linux-${PV}"


do_configure() {
	unset CFLAGS
	unset CPPFLAGS
	unset LDFLAGS

        rm -f ${S}/.config || true

        oe_runmake mambo_defconfig
}

# Override the do_install of kernel because of compilation problems with poky migration
# Indeed, the installation directories use the wrong paths and this works whithout it
do_install() {
		#
        # First install the modules
        #
        unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS MACHINE
        if (grep -q -i -e '^CONFIG_MODULES=y$' .config); then
                oe_runmake DEPMOD=echo INSTALL_MOD_PATH="${D}" modules_install
                rm "${D}/lib/modules/${KERNEL_VERSION}/build"
                rm "${D}/lib/modules/${KERNEL_VERSION}/source"
                # If the kernel/ directory is empty remove it to prevent QA issues
                rmdir --ignore-fail-on-non-empty "${D}/lib/modules/${KERNEL_VERSION}/kernel"
        else
                bbnote "no modules to install"
        fi
}

#necessary for plf-kernel.bbclass
create_plf_ini() {

#create ini file for plftool
cat > ${S}/kernel.ini <<EOF
[file]
Type=kernel
EntryPoint=0x40800000
HdrVersion=13
VersionMajor=0
VersionMinor=0
VersionBugfix=0
TargetPlat=0x4
TargetAppl=0x82
HwCompatibility=0x955371ec
LanguageZone=0

[zImage]
LoadAddr=0x40800000
File=${DEPLOYDIR}/zImage

[BootParams]
LoadAddr=0x40700000
File=${S}/bootparams.txt
EOF
}
