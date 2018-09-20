DESCRIPTION = "armadillo"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=d273d63619c9aeaf15cdaf76422c4f87"
PR = "r0"

SRC_URI = "http://sourceforge.net/projects/arma/files/armadillo-9.100.5.tar.xz \
"

SRC_URI[md5sum] = "9d965598c9dc92d97afc5fba593cc4b5"
SRC_URI[sha256sum] = "7e7dc6f1e876b8243c27a003b037559663371b42885436b1087757e652db41cd"

inherit cmake

PACKAGES += "${PN}-share"
FILES_${PN}-share += "${datadir}/Armadillo/*"
