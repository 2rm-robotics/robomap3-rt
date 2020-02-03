kernel_do_deploy_append() {

create_plf_ini

#boot params file
echo "${BOOT_PARAMS}" | tr -d '\n' > ${S}/bootparams.txt

#create plf file
plftool -b ${S}/kernel.ini -o ${DEPLOYDIR}/${KERNEL_IMAGE_BASE_NAME}.plf

#add 0x00000000 to the begining (Parrot's bootloader expect it...)
sed  -i '1s/^/\x00\x00\x00\x00/' ${DEPLOYDIR}/${KERNEL_IMAGE_BASE_NAME}.plf

#make symoblic links
cd ${DEPLOYDIR}
ln -sf ${KERNEL_IMAGE_BASE_NAME}.plf zImage.plf

}
