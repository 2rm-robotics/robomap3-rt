# look for files in the layer first
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR := "${PR}.1"

SRC_URI_append_igep00x0 = " file://authorized_keys"

do_install_append_igep00x0() {
	install -d ${D}/home/root/.ssh
	install -m 0644 ${WORKDIR}/authorized_keys ${D}/home/root/.ssh
}

PACKAGES =+ "${PN}-root-tweaks"

FILES_${PN}-root-tweaks = "/home/root/.ssh"
