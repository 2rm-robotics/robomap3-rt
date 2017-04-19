FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR := "${PR}.1"

INITSCRIPT_PACKAGES += "${PN}-server-config"

INITSCRIPT_NAME_${PN}-server-config = "dhcp-server"
INITSCRIPT_PARAMS_${PN}-server-config = "defaults 20"

inherit update-rc.d
