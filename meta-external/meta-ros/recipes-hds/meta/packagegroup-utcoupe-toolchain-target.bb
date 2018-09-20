DESCRIPTION = "Target packages for the standalone utcoupe SDK"
PR = "r1"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = "\
    laser-geometry-dev \
    libsdl-dev \
    sfml-dev \
    armadillo-dev \
    dynamixelsdk-dev \
    interactive-markers-dev \
    laser-proc-dev \
    teraranger-dev \
    "
