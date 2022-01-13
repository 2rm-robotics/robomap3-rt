DESCRIPTION = "a library for performing fast approximate nearest neighbor searches in high dimensional spaces"
AUTHOR = "Marius Muja and David G. Lowe"
HOMEPAGE = "http://www.cs.ubc.ca/~mariusm/index.php/FLANN/FLANN"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=040a44ba915aa6b3b099ea189c7b7e20"

SRC_URI = "https://github.com/flann-lib/flann/archive/refs/tags/1.8.4.zip"
SRC_URI[md5sum] = "11490b77a2583d5b0c1b33eafe421e77"
SRC_URI[sha256sum] = "22dfa2ef97b696f7ea5bf5b44aaa8590067244cddcfa1f6837f0c0d8c88c6e5c"

S = "${WORKDIR}/flann-${PV}"

inherit cmake
