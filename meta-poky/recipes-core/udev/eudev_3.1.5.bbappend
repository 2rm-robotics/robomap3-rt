FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
PR := "${PR}.1"

SRC_URI += "  file://0014-Revert-rules-remove-firmware-loading-rules.patch \
                file://Revert-udev-remove-userspace-firmware-loading-suppor.patch \
"
