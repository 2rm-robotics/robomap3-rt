SUMMARY = "Atheros 7K Wifi configuration utility"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://wmiconfig.c;endline=19;md5=c86f3e019e6b6b0f47b6418a54611b54"

SECTION = "console/network"

SRC_URI = "git://chromium.googlesource.com/chromiumos/third_party/atheros;protocol=http;branch=0.15.877.B \
    file://disable_pal.patch \
"

S = "${WORKDIR}/git/files/host/tools/wmiconfig/"

#The following source revision corresponds to the most recent branch
SRCREV = "81b519cdad02b3afe43cbf59977f0d546b237aee"
PR = "r0"

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 wmiconfig ${D}${bindir}
}

