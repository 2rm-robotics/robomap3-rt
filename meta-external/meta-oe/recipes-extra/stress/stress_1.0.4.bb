DESCRIPTION = "Deliberately simple workload generator for POSIX systems. It \
imposes a configurable amount of CPU, memory, I/O, and disk stress on the system."
HOMEPAGE = "http://weather.ou.edu/~apw/projects/stress/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d32239bcb673463ab874e80d47fae504"
 
SRC_URI = "ftp://ftp.hu.debian.org/pub/sunfreeware/SOURCES/stress-1.0.4.tar.gz \
            file://texinfo.patch \
           "
 
SRC_URI[md5sum] = "4a66a0f8d5c2e8cfaf3b00626d57b2d8"
SRC_URI[sha256sum] = "59712a6942b5d616788da06fefe162d1f716e8bb2ed810b9f7a3492c6fc34980"

inherit autotools
