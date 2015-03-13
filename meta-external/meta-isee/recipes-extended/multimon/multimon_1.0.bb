DESCRIPTION = "Linux Radio Transmission Decoder"
SUMMARY = "The multimon software can decode a variety of digital transmission modes \
commonly found on UHF radio. A standard PC soundcard is used to acquire \
the signal from a transceiver. The decoding is done completely in software."
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=3bf50002aefd002f49e7bb854063f7e7"

PR = "r1"

RDEPENDS_${PN} += "sox"

SRC_URI = "https://launchpad.net/ubuntu/+archive/primary/+files/multimon_1.0.orig.tar.gz;name=tarball \
	https://launchpad.net/ubuntu/+archive/primary/+files/multimon_1.0-5ubuntu1.diff.gz;name=patch \
	file://cross.patch \
	"

SRC_URI[tarball.md5sum] = "549e7452b6c47160882fece5076a7208"
SRC_URI[tarball.sha256sum] = "1b3ff4dfe44bf271b938983eef2e3ffcff53211e714b2828f2def31f78bbf19d"

SRC_URI[patch.md5sum] = "1c773395464c751616d91bcb74998d7f"
SRC_URI[patch.sha256sum] = "7ce5311f95e79b219d8acc1b5658eab08820396356aa312671cf5c5d39f554a9"

LDFLAGS += "-lm"

FILES_${PN} = "${bindir}"

do_install() {
        install -d ${D}${bindir}
        install -m 755 ${S}/bin/multimon ${D}/${bindir}
        install -m 755 ${S}/bin/gen ${D}/${bindir}
}

