SUMMARY = "Atheros 7K Wifi configuration utility"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://wmiconfig.c;endline=19;md5=c86f3e019e6b6b0f47b6418a54611b54"

SECTION = "console/network"

SRC_URI[md5sum] = "ad5896389412100177710466ffc0b547"
SRC_URI[sha256sum] = "407f4629ca3c1166e56f9476b394865adad14070536882defcaffd39b8f376ba"

SRC_URI = "https://chromium.googlesource.com/chromiumos/third_party/atheros/+archive/0.15.877.B.tar.gz \
    file://disable_pal.patch \
"

S = "${WORKDIR}/files/host/tools/wmiconfig/"

PR = "r0"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 wmiconfig ${D}${bindir}
}

