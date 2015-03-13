DESCRIPTION = "Target packages for pacpus"
PR = "r0"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} = "canutils init-can gpsd ppp ntp ldd xenomai log4cxx  \
            packagegroup-core-qt4e qt4-embedded-plugin-gfxdriver-gfxpvregl libqt-embeddedpvrqwswsegl4 coreutils vpnc pacpusframework"
#tcl tcludp libtcl"
