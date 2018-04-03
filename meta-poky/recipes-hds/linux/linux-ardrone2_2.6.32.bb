DESCRIPTION = "2.6 Linux Kernel for the Ar Drone 2 platform"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ardrone2 = "1"
DEFAULT_PREFERENCE_ardrone2-installer = "1"
DEFAULT_PREFERENCE_ardrone2-updater = "1"

COMPATIBLE_MACHINE = "(ardrone2|ardrone2-installer|ardrone2-updater)"

inherit kernel autotools-brokensep
DEPENDS +="plftool-native"

PR = "r10"
KV = "${PV}-9"

SRCREV = "46"
SRC_URI = "svn://devel.hds.utc.fr/svn/ardrone2_src/trunk;module=${PN}-${KV};protocol=https"
S = "${WORKDIR}/${PN}-${KV}"

#test to use different cc
#KERNEL_CC ="/opt/robomap3/1.7.3/armv7a-neon/sysroots/x86_64-pokysdk-linux/usr/bin/arm-poky-linux-gnueabi/arm-poky-linux-gnueabi-gcc"

do_configure() {
	unset CFLAGS
	unset CPPFLAGS
	unset LDFLAGS

        rm -f ${S}/.config || true

        oe_runmake ardrone2_defconfig
	if [ "${MACHINE}" = "ardrone2" ];then
		oe_runmake ardrone2_defconfig
    elif [ "${MACHINE}" = "ardrone2-installer" ];then
		oe_runmake ardrone2_installer_defconfig
	elif [ "${MACHINE}" = "ardrone2-updater" ];then
		oe_runmake ardrone2_updater_defconfig
	fi
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

do_install_append() {
	# Necessary for building modules like dsp
	if [ -f include/linux/bounds.h ]; then
		cp include/linux/bounds.h ${STAGING_KERNEL_BUILDDIR}/include/linux/bounds.h
	fi
}


