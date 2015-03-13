DESCRIPTION = "Target packages for the standalone framework uav SDK"
PR = "r2"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit packagegroup

RDEPENDS_${PN} = "\
    libxml2-dev \
    cmake-dev \
    xenomai-dev \
    tcl-dev \
    bluez4-dev \
    iir-dev \
    udt-dev \
    vrpn-dev \
    opencv-dev \
    nmealib-dev \
    tclap-dev \
    irrlicht-dev \
    "
