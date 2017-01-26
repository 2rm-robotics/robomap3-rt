kernel_do_deploy_append() {

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
