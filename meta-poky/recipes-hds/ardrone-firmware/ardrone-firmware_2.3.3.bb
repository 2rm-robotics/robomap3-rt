DESCRIPTION = "ardrone-firmware"
PR = "r0"

COMPATIBLE_MACHINE = "ardrone2"

LICENSE = "Proprietary"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/Proprietary;md5=0557f9d92cf58f2ccdd50f62f8ac0b28"

SRC_URI = "http://durrans.com/ardrone/firmware/${PV}/ardrone2_update.plf"
SRC_URI[md5sum] = "e779504b2fb362a1a727415290a9a914"
SRC_URI[sha256sum] = "d62f231d5961406f847e3d1d3e62c2e87f88fb2d64f81018d2691971fbfbde2a"

DEPENDS ="plftool-native"

S = "${WORKDIR}"

do_extract_firmware() {
	export LD_LIBRARY_PATH=${STAGING_LIBDIR_NATIVE}
	plftool -e nice -i ./ardrone2_update.plf -o out/
}

addtask extract_firmware after do_configure before do_install


do_install() {
	mkdir -p ${D}${base_libdir}/firmware
	cp -a out/lib/firmware/* ${D}${base_libdir}/firmware
}

INHIBIT_PACKAGE_STRIP = "1"
PACKAGES =+ "${PN}-atheros"
INSANE_SKIP_${PN}-atheros = "ldflags"
FILES_${PN}-atheros = "/lib/firmware/*"
