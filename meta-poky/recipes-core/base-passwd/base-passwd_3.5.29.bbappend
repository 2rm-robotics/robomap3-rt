FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PR := "${PR}.1"

SRC_URI += "file://root-home.patch"
