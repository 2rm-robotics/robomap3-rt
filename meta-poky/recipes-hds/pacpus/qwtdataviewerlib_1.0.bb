DESCRIPTION = "QwtDataViewerLib"

LICENSE = "CECILL-C"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/CECILL-C;md5=de61e8e359a18f44efdb91f6d020e73b"

DEPENDS = "qwt"

PR = "r11"
SRCREV = "25"
SRC_URI = "svn://devel.hds.utc.fr/svn/pacpushmi;module=trunk;protocol=https "

S = "${WORKDIR}/build"

inherit qt4x11 cmake

OECMAKE_SOURCEPATH="${WORKDIR}/trunk/QwtDataViewerLib/standalone"

#put findqwt at the "right" place
do_configure_prepend() {
	cp -r ${WORKDIR}/trunk/cmake ${WORKDIR}/trunk/QwtDataViewerLib/standalone
}
