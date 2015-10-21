IMAGE_INSTALL += "mtd-utils-ubifs bash coreutils-minimal init-ardrone2-installer"
DEPENDS +="plftool-native virtual/kernel"

BOOT_PARAMS="mtdparts=omap2-nand.0:512K(Pbootloader),8M(Pmain_boot),8M(Pfactory),114176K(Psystem) console=ttyO3,115200 loglevel=8 parrot_force_usbd g_serial.use_acm=0 "
# g_serial.idVendor=0x19cf g_serial.idProduct=0x1000 g_serial.iManufacturer="Parrot SA"

IMAGE_CMD_cpio_append () {
#boot params file
echo "${BOOT_PARAMS}" | tr -d '\n' > ${S}/bootparams.txt

#create ini file for plftool
IMAGE_SIZE=$(stat ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.cpio --format="%s")
let OFFSET=4+${IMAGE_SIZE}+0x81000000

cat > ${S}/installer.ini <<EOF
[file]
Type=kernel
EntryPoint=${OFFSET}
HdrVersion=11
VersionMajor=5
VersionMinor=8
VersionBugfix=0x1b
TargetPlat=0x5
TargetAppl=0x59
HwCompatibility=0x1d400a4e
LanguageZone=0

[zImage]
LoadAddr=${OFFSET}
File=${DEPLOY_DIR_IMAGE}/zImage

[InitRD]
LoadAddr=0x81000000
File=${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.cpio

[BootParams]
LoadAddr=0x80700000
File=${S}/bootparams.txt
EOF

#create plf file
plftool -b ${S}/installer.ini -o ${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.plf

#make symoblic links
cd ${DEPLOY_DIR_IMAGE}
rm -f ${PN}-${MACHINE}.plf
ln -sf ${IMAGE_NAME}.plf ${PN}-${MACHINE}.plf
}
