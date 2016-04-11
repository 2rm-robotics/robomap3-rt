DESCRIPTION = "Target packages for the uav"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} = "udt ldd opencv xenomai i2c-omap uart-omap libxml2 iir vrpn nmealib dsp filelib"
#capture-radio-omap
