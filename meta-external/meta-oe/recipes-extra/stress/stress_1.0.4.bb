DESCRIPTION = "Deliberately simple workload generator for POSIX systems. It \
imposes a configurable amount of CPU, memory, I/O, and disk stress on the system."
HOMEPAGE = "http://weather.ou.edu/~apw/projects/stress/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
 
SRC_URI = "ftp://ftp.hu.debian.org/pub/sunfreeware/SOURCES/stress-1.0.4.tar.gz \
            file://texinfo.patch \
           "
 
SRC_URI[md5sum] = "890a4236dd1656792f3ef9a190cf99ef"
SRC_URI[sha256sum] = "057e4fc2a7706411e1014bf172e4f94b63a12f18412378fca8684ca92408825b"

inherit autotools
