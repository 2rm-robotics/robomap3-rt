DESCRIPTION = "2.6 Linux Kernel for IGEP based platforms"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"


DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_uav = "1"
DEFAULT_PREFERENCE_airbox = "1"
DEFAULT_PREFERENCE_overo = "1"
DEFAULT_PREFERENCE_pacpus = "1"

COMPATIBLE_MACHINE = "(airbox|uav|overo|pacpus)"

inherit kernel autotools-brokensep
#require recipes-kernel/linux/linux.inc
#do_configure[depends] += "virtual/kernel:do_shared_workdir"

PR = "r18"
KV = "${PV}-9"

SRC_URI = "git://gitlab.utc.fr/uav-hds/igep/linux.git;branch=linux-omap-2.6.37-y;protocol=https \
	   file://disable-gcc5-sra-optimization.patch \
"
SRCREV = "1c813d59dcf7fa0c610e428b2ec608f76b724432"

S = "${WORKDIR}/git"

do_configure() {
	rm -f ${S}/.config || true

	if [ "${MACHINE}" = "uav" ];then
		oe_runmake igep00x0_uav_defconfig
        elif [ "${MACHINE}" = "overo" ];then
		oe_runmake igep00x0_uav_defconfig
        elif [ "${MACHINE}" = "pacpus" ];then
		oe_runmake igep00x0_defconfig
	elif [ "${MACHINE}" = "airbox" ];then
		oe_runmake igep00x0_airbox_defconfig
	fi
}

do_compile_prepend() {
#	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	unset LDFLAGS
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

do_install_prepend() {
        unset LDFLAGS
}
