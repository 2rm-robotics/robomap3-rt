DESCRIPTION = "2.6 Linux Kernel for the Ar Drone 2 platform"
SECTION = "kernel"
LICENSE = "GPLv2"

LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"

DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_ardrone2 = "1"
DEFAULT_PREFERENCE_ardrone2-installer = "1"

COMPATIBLE_MACHINE = "(ardrone2|ardrone2-installer)"
DEPENDS +="plftool-native"

BOOT_PARAMS="mtdparts=omap2-nand.0:512K(Pbootloader),8M(Pmain_boot),8M(Pfactory),114176K(Psystem) console=ttyO3,115200 loglevel=8 earlyprintk=ttyO3,115200 ubi.mtd=Pfactory,2048 ubi.mtd=Psystem,2048 root=ubi1:rootfs rootfstype=ubifs parrot5.low_latency=1 mem=80M mpurate=1000 "

inherit kernel

PR = "r9"
KV = "${PV}-9"

SRCREV = "33"
SRC_URI = "svn://devel.hds.utc.fr/svn/ardrone2_src/trunk;module=${PN}-${KV};protocol=https"
S = "${WORKDIR}/${PN}-${KV}"

do_configure() {

        rm -f ${S}/.config || true

        oe_runmake ardrone2_defconfig
	if [ "${MACHINE}" = "ardrone2" ];then
		oe_runmake ardrone2_defconfig
        elif [ "${MACHINE}" = "ardrone2-installer" ];then
		oe_runmake ardrone2_installer_defconfig
	fi
}

do_install_append() {
	# Necessary for building modules like dsp
	if [ -f include/linux/bounds.h ]; then
		cp include/linux/bounds.h $kerneldir/include/linux/bounds.h
	fi
}

kernel_do_deploy_append_ardrone2() {

#boot params file
echo "${BOOT_PARAMS}" | tr -d '\n' > ${S}/bootparams.txt

#create ini file for plftool
cat > ${S}/kernel.ini <<EOF
[file]
Type=kernel
EntryPoint=0x81000000
HdrVersion=11
VersionMajor=5
VersionMinor=8
VersionBugfix=0x1b
TargetPlat=0x5
TargetAppl=0x59
HwCompatibility=0x1d400a4e
LanguageZone=0

[zImage]
LoadAddr=0x81000000
File=${DEPLOYDIR}/zImage

[BootParams]
LoadAddr=0x80700000
File=${S}/bootparams.txt
EOF

#create plf file
plftool -b ${S}/kernel.ini -o ${DEPLOYDIR}/${KERNEL_IMAGE_BASE_NAME}.plf

#add 0x00000000 to the begining (Parrot's bootloader expect it...)
sed  -i '1s/^/\x00\x00\x00\x00/' ${DEPLOYDIR}/${KERNEL_IMAGE_BASE_NAME}.plf

#make symoblic links
cd ${DEPLOYDIR}
ln -sf ${KERNEL_IMAGE_BASE_NAME}.plf zImage.plf

}
