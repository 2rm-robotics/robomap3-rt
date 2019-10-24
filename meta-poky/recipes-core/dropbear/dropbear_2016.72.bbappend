FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#change default ssh port for beaglebone-blue (chroot), as sshd is already running on debian
SRC_URI_append_beaglebone-blue = "\ 
	file://dropbear.default \
"

do_install_append_beaglebone-blue() {
    install -m 0755 ${WORKDIR}/dropbear.default ${D}${sysconfdir}/default/dropbear
}
