SUMMARY = "Atheros 7K Wifi configuration utility"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://wmiconfig.c;endline=19;md5=c86f3e019e6b6b0f47b6418a54611b54"

SECTION = "console/network"

#SRC_URI[md5sum] = "ab85e649b2e7e2c3aed21ea411acb5d8"
#SRC_URI[sha256sum] = "9816c6134fdffbfab8b61d0c80954dd1e0ee0788df5fc6643b2faed2c28ced42"

SRC_URI = "git://chromium.googlesource.com/chromiumos/third_party/atheros/;protocol=http;branch=0.15.877.B \
    file://disable_pal.patch \
"
SRCREV="81b519cdad02b3afe43cbf59977f0d546b237aee"

S = "${WORKDIR}/git/files/host/tools/wmiconfig/"

PR = "r0"

# Adds in poky 2.1 migration
CLEANBROKEN = "1"
EXTRA_OEMAKE = "-e MAKEFLAGS="

TARGET_CC_ARCH += "${LDFLAGS}"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 wmiconfig ${D}${bindir}
}

