DESCRIPTION = "Eigen is a C++ template library for linear algebra: matrices, vectors, numerical solvers, and related algorithms."
AUTHOR = "Benoît Jacob and Gaël Guennebaud and others"
HOMEPAGE = "http://eigen.tuxfamily.org/"
LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://COPYING.MPL2;md5=815ca599c9df247a0c7f619bab123dad"

SRC_URI = "https://gitlab.com/libeigen/eigen/-/archive/${PV}/eigen-${PV}.tar.bz2 \
           file://0001-CMakeLists.txt-install-FindEigen3.cmake-script.patch"
SRC_URI[md5sum] = "5fd398d656f10223f7ac12bf54689e60"
SRC_URI[sha256sum] = "6ab7f3d8ffe0bd409895728a8a287771da3d17a58a8c9a09b926d75b93ad30f2"

S = "${WORKDIR}/eigen-eigen-c58038c56923"

inherit cmake

EXTRA_OECMAKE += "-Dpkg_config_libdir=${libdir}"

FILES_${PN} = "${includedir} ${libdir}"
FILES_${PN}-dev = "${datadir}/cmake/Modules"

# ${PN} is empty so we need to tweak -dev and -dbg package dependencies
RDEPENDS_${PN}-dev = ""
RRECOMMENDS_${PN}-dbg = "${PN}-dev (= ${EXTENDPKGV})"
