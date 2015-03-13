DESCRIPTION = "pacpusframework"

LICENSE = "none"

LIC_FILES_CHKSUM = "file://../LICENSE.txt;md5=72463069d6b3d44b0e03c5fdf46534aa"

DEPENDS = "log4cxx boost"

COMPATIBLE_MACHINE = "pacpus"

PR = "r0"

SRCREV = "341"
SRC_URI = "svn://devel.hds.utc.fr/svn/pacpusframework/branches;module=${PV};protocol=https "

S = "${WORKDIR}/${PV}/build"

inherit qt4e cmake

EXTRA_OECMAKE = "../${PV} -DQT_LIBRARY_DIR=${OE_QMAKE_LIBDIR_QT} \
				-DQT_INSTALL_LIBS=${OE_QMAKE_LIBDIR_QT} \
				-DQT_INCLUDE_DIR=${OE_QMAKE_INCDIR_QT} \
				-DQT_HEADERS_DIR=${OE_QMAKE_INCDIR_QT} \
				-DQT_MOC_EXECUTABLE=${OE_QMAKE_MOC} \
				-DQT_UIC_EXECUTABLE=${OE_QMAKE_UIC} \
				-DQT_UIC3_EXECUTABLE=${OE_QMAKE_UIC3} \
				-DQT_RCC_EXECUTABLE=${OE_QMAKE_RCC} \
				-DQT_QMAKE_EXECUTABLE=${OE_QMAKE_QMAKE} \
				-DQT_QTCORE_INCLUDE_DIR=${OE_QMAKE_INCDIR_QT}/QtCore \
				-DQT_DBUSXML2CPP_EXECUTABLE=/usr/bin/qdbusxml2cpp \
				-DQT_DBUSCPP2XML_EXECUTABLE=/usr/bin/qdbuscpp2xml \
				-DQT_MKSPECS_DIR=${QMAKESPEC}/../ \
				-DPACPUS_INSTALL_DIR=/opt/pacpus \
				-DPACPUS_INSTALL_3RD=TRUE \
				-DPACPUS_USE_LOG=TRUE -DCMAKE_BUILD_TYPE=Release \
				-DPACPUS_INSTALL_WITH_VERSION_NUMBER=TRUE \
                "

PACKAGES = "${PN}-dev ${PN}-dbg ${PN}"
FILES_${PN}-dev = "/opt/pacpus/0.1.1/include/* /opt/pacpus/0.1.1/cmake/*"
FILES_${PN} = "/opt/pacpus/0.1.1/bin/* /opt/pacpus/0.1.1/lib/*.so*"
FILES_${PN}-dbg = "/opt/pacpus/0.1.1/bin/.debug/* /opt/pacpus/0.1.1/lib/.debug/* /usr/src/debug/*"
