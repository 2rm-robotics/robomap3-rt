DESCRIPTION = "Target packages for flair stuff"
PR = "r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
ALLOW_EMPTY_${PN} = "1"

#opencv1 is for compatibility with old flair programs, we don't have old programs on mambo
#nmealib is not usefull on mambo
RDEPENDS_${PN}_mambo = "udt ldd libxml2 iir vrpn nmealib filelib tcpdump"

RDEPENDS_${PN} = "udt ldd opencv1 libxml2 iir vrpn nmealib filelib tcpdump"
