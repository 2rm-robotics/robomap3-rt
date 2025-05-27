SUMMARY = "Templatized C++ Command Line Parser"
HOMEPAGE = "http://tclap.sourceforge.net/" 
LICENSE = "MIT" 
LIC_FILES_CHKSUM = "file://COPYING;md5=c8ab0ff134bcc584d0e6b5b9f8732453"

SRCREV = "3627d9402e529770df9b0edf2aa8c0e0d6c6bb41"
SRC_URI = "https://downloads.sourceforge.net/project/${BPN}/${BP}.tar.gz \
    file://Makefile.am-disable-docs.patch \
" 
SRC_URI[md5sum] = "eb0521d029bf3b1cc0dcaa7e42abf82a"
SRC_URI[sha256sum] = "9f9f0fe3719e8a89d79b6ca30cf2d16620fba3db5b9610f9b51dd2cd033deebb"

inherit autotools

BBCLASSEXTEND = "native nativesdk"

ALLOW_EMPTY_${PN} = "1"
