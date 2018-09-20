DESCRIPTION = "Serial Communication Library"
SECTION = "devel"
LICENSE = "mit"
LIC_FILES_CHKSUM = "file://${WORKDIR}/serial-${PV}/README.md;beginline=57;endline=65;md5=e580ffe9f91d283eca58a137a9434ef3"

SRC_URI = "https://github.com/wjwwood/serial/archive/${PV}.tar.gz;downloadfilename=${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "b6d9ebdf821654715656577652b61b64"
SRC_URI[sha256sum] = "0c2a789ce485a83ed640c777a7d1cd1256976890ece4c126f93751a08643917a"

inherit catkin

S="${WORKDIR}/serial-${PV}"
