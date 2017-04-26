DESCRIPTION = "ISEE bootloader for IGEP based platforms"
HOMEPAGE = "http://www.isee.biz"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://lib/board.c;beginline=19;endline=32;md5=6120559432ceee352e4ddc8961780fb7"
SECTION = "bootloader"

COMPATIBLE_MACHINE = "(airbox|uav|overo|pacpus)"

VER = "${PV}-2"
PR = "r6"

SRCREV = "507"
SRC_URI = "svn://devel.hds.utc.fr/svn/igep_src/trunk;module=igep-x-loader;protocol=https \
            file://igep.ini"

inherit deploy

# Solve compile issues with yocto 2.1 (6.9.4. Makefile Environment Changes)
EXTRA_OEMAKE = "-e MAKEFLAGS="

do_compile() {
	unset LDFLAGS
	unset CFLAGS
	unset CPPFLAGS

	# Ensure signGP program is built for host machine
	${BUILD_CC} contrib/signGP.c -o contrib/signGP

        if [ "${MACHINE}" = "uav" ];then
		oe_runmake CROSS_COMPILE=${TARGET_PREFIX} igep00x0_config
        elif [ "${MACHINE}" = "overo" ];then
		oe_runmake CROSS_COMPILE=${TARGET_PREFIX} overo_config
	elif [ "${MACHINE}" = "airbox" ];then
		oe_runmake CROSS_COMPILE=${TARGET_PREFIX} igep00x0_config
        elif [ "${MACHINE}" = "pacpus" ];then
		oe_runmake CROSS_COMPILE=${TARGET_PREFIX} igep00x0_config
	fi
	oe_runmake CROSS_COMPILE=${TARGET_PREFIX}
	${S}/contrib/signGP x-load.bin
}

do_install() {
	install -d ${D}/boot
	cp ${S}/x-load.bin.ift ${D}/boot/MLO-${VER}
	ln -sf MLO-${VER} ${D}/boot/MLO
        cp ${WORKDIR}/igep.ini ${D}/boot/
}

XLOADER_IMAGE_BASE_NAME ?= "${PN}-${PV}-${PR}-${MACHINE}-${DATETIME}"
XLOADER_IMAGE_SYMLINK_NAME ?= "MLO"

INI_BASE_NAME ?= "igep-${PV}-${PR}-${MACHINE}-${DATETIME}.ini"
INI_SYMLINK_NAME ?= "igep.ini"

# Exclude DATETIME from the checksum to avoid bitbake errors
do_deploy[vardepsexclude] += "DATETIME"
XLOADER_IMAGE_BASE_NAME[vardepsexclude] += "DATETIME"
INI_BASE_NAME[vardepsexclude]	+= "DATETIME"

do_deploy() {
	install -m 0644 ${S}/x-load.bin.ift ${DEPLOYDIR}/${XLOADER_IMAGE_BASE_NAME}
        install -m 0644 ${WORKDIR}/igep.ini ${DEPLOYDIR}/${INI_BASE_NAME}
	
	cd ${DEPLOYDIR}
	rm -f ${XLOADER_IMAGE_SYMLINK_NAME}
	ln -sf ${XLOADER_IMAGE_BASE_NAME} ${XLOADER_IMAGE_SYMLINK_NAME}

        rm -f ${INI_SYMLINK_NAME}
	ln -sf ${INI_BASE_NAME} ${INI_SYMLINK_NAME}
}
addtask deploy before do_build after do_compile

FILES_${PN} = "/boot"
PACKAGE_ARCH = "${MACHINE_ARCH}"

S = "${WORKDIR}/${PN}"
