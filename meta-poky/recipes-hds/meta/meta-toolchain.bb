SUMMARY = "Meta package for building a installable toolchain"
LICENSE = "MIT"

PR = "r13"

SRC_URI = "file://toolchain-shar-template.sh "

LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

inherit populate_sdk_flair

create_shar_prepend() {
	cp ../toolchain-shar-template.sh ./
}
