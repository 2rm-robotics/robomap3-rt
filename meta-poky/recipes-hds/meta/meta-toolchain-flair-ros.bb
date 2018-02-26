QTNAME = "qte"
QTNAME_genericx86-64 = "qt"
QTNAME_genericx86 = "qt"

TOOLCHAIN_HOST_TASK = "nativesdk-packagegroup-${QTNAME}-toolchain-host packagegroup-cross-canadian-${MACHINE} nativesdk-cmake nativesdk-pkgconfig nativesdk-doxygen"
TOOLCHAIN_HOST_TASK_uav = "nativesdk-packagegroup-${QTNAME}-toolchain-host packagegroup-cross-canadian-${MACHINE} nativesdk-cmake nativesdk-pkgconfig nativesdk-doxygen nativesdk-plftool nativesdk-ardrone2-usbload"
TOOLCHAIN_HOST_TASK_rpi-hds = "nativesdk-packagegroup-${QTNAME}-toolchain-host packagegroup-cross-canadian-${MACHINE} nativesdk-cmake nativesdk-pkgconfig nativesdk-doxygen nativesdk-packagegroup-ros-toolchain-host"

#TOOLCHAIN_TARGET_TASK_BASE = "packagegroup-${QTNAME}-toolchain-target packagegroup-core-standalone-sdk-target packagegroup-framework-uav-toolchain-target packagegroup-ros-mavlink packagegroup-ros-toolchain-target"
TOOLCHAIN_TARGET_TASK_BASE = "packagegroup-${QTNAME}-toolchain-target packagegroup-core-standalone-sdk-target packagegroup-flair-toolchain-target packagegroup-ros-toolchain-target"
TOOLCHAIN_TARGET_TASK = "${TOOLCHAIN_TARGET_TASK_BASE}"
TOOLCHAIN_TARGET_TASK_genericx86-64 = "${TOOLCHAIN_TARGET_TASK_BASE} qt-mobility-x11-dev qwt-dev qtserialport-dev qwtdataviewerlib-dev irrlicht-dev libxshmfence"
TOOLCHAIN_TARGET_TASK_genericx86    = "${TOOLCHAIN_TARGET_TASK_BASE} qt-mobility-x11-dev qwt-dev qtserialport-dev qwtdataviewerlib-dev irrlicht-dev libxshmfence"

TOOLCHAIN_OUTPUTNAME = "${SDK_NAME}-${DISTRO_VERSION}"

require meta-toolchain.bb

QT_DIR_NAME_genericx86 = "qt"
QT_DIR_NAME_genericx86-64 = "qt"
QT_DIR_NAME = "qtopia"

QT_TOOLS_PREFIX = "${SDKPATHNATIVE}${bindir_nativesdk}"

SRC_URI = "file://toolchain-shar-template.sh "

DEPENDS = "\
    nativesdk-catkin\
"

create_shar_prepend() {
	cp ../toolchain-shar-template.sh ./
}

create_sdk_files_append_genericx86-64() {
    mkdir -p ${SDK_OUTPUT}${SDKPATHNATIVE}/environment-setup.d/
    script=${SDK_OUTPUT}${SDKPATHNATIVE}/environment-setup.d/${QT_DIR_NAME}.sh

    echo 'export OE_QMAKE_CFLAGS="$CFLAGS"' > $script
    echo 'export OE_QMAKE_CXXFLAGS="$CXXFLAGS"' >> $script
    echo 'export OE_QMAKE_LDFLAGS="$LDFLAGS"' >> $script
    echo 'export OE_QMAKE_CC=$CC' >> $script
    echo 'export OE_QMAKE_CXX=$CXX' >> $script
    echo 'export OE_QMAKE_LINK=$CXX' >> $script
    echo 'export OE_QMAKE_AR=$AR' >> $script
    echo 'export OE_QMAKE_LIBDIR_QT=$OECORE_TARGET_SYSROOT${libdir}' >> $script
    echo 'export OE_QMAKE_INCDIR_QT=$OECORE_TARGET_SYSROOT${includedir}/${QT_DIR_NAME}' >> $script
    echo 'export OE_QMAKE_MOC=${QT_TOOLS_PREFIX}/moc4' >> $script
    echo 'export OE_QMAKE_UIC=${QT_TOOLS_PREFIX}/uic4' >> $script
    echo 'export OE_QMAKE_UIC3=${QT_TOOLS_PREFIX}/uic34' >> $script
    echo 'export OE_QMAKE_RCC=${QT_TOOLS_PREFIX}/rcc4' >> $script
    echo 'export OE_QMAKE_QDBUSCPP2XML=${QT_TOOLS_PREFIX}/qdbuscpp2xml4' >> $script
    echo 'export OE_QMAKE_QDBUSXML2CPP=${QT_TOOLS_PREFIX}/qdbusxml2cpp4' >> $script
    echo 'export OE_QMAKE_QT_CONFIG=$OECORE_TARGET_SYSROOT${datadir}/${QT_DIR_NAME}/mkspecs/qconfig.pri' >> $script
    echo 'export QMAKESPEC=$OECORE_TARGET_SYSROOT${datadir}/${QT_DIR_NAME}/mkspecs/linux-g++' >> $script
    echo 'export QT_CONF_PATH=$OECORE_NATIVE_SYSROOT${sysconfdir}/qt.conf' >> $script

    # make a symbolic link to mkspecs for compatibility with Qt SDK
    # and Qt Creator
    (cd ${SDK_OUTPUT}/${SDKPATHNATIVE}${bindir_nativesdk}/..; ln -s ${SDKTARGETSYSROOT}/usr/share/${QT_DIR_NAME}/mkspecs mkspecs;)

    #creation du ficher qt.conf
    echo '[Paths]' >> ${SDK_OUTPUT}/${QT_TOOLS_PREFIX}/qt.conf
    echo 'Prefix =${SDKTARGETSYSROOT}/usr' >> ${SDK_OUTPUT}/${QT_TOOLS_PREFIX}/qt.conf
    echo 'Binaries = ${QT_TOOLS_PREFIX}' >> ${SDK_OUTPUT}/${QT_TOOLS_PREFIX}/qt.conf
}
