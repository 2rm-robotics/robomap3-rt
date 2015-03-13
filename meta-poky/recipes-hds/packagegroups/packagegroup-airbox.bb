DESCRIPTION = "Target packages for the airbox"
PR = "r4"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=4d92cd373abda3937c2bc47fbc49d690 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
ALLOW_EMPTY_${PN} = "1"

RDEPENDS_${PN} = "canutils init-can gpsd ppp ntp ntpdate encfs encfs-setup fuse \
		  util-linux make tcl tk-lib tk tcludp tcllib tclxml tclthread \
  		  libsnack2 coreutils \
		  adjtimex bridge-utils cronie iputils-ping ntp-utils tcpdump \
		  xauth inetutils-rsh socat"
