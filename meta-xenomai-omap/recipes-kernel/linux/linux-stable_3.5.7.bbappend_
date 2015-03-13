
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
XENO_VERSION="2.6.3"

# Additional patch, to undo a change from Xenomai.
SRC_URI += " file://undo_patch_0028.patch \
	file://0037-add-possibility-to-disable-classic-uart-driver-from-.patch \
	file://0038-add-possibility-to-disable-classic-i2c-driver-from-u.patch \
 "

#xenomai source (prepare_kernel.sh script)
SRC_URI += "http://download.gna.org/xenomai/stable/xenomai-${XENO_VERSION}.tar.bz2"
SRC_URI[md5sum] = "9f83c39cfb10535df6bf51702714e716"
SRC_URI[sha256sum] = "4d0d09431f0340cf4c9e2745177f77f15b7b124f89982035d1d3586519d7afe9"


# Apply xenomai changes before will go patch stuff from main linux-stable file.
do_configure_prepend () {
    ${WORKDIR}/xenomai-${XENO_VERSION}/scripts/prepare-kernel.sh --arch=arm --linux=${S} --ipipe=${WORKDIR}/xenomai-${XENO_VERSION}/ksrc/arch/arm/patches/ipipe-core-${PV}-arm-6.patch
}

