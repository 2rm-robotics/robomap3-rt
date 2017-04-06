IMAGE_FEATURES += "splash ssh-server-openssh"

IMAGE_INSTALL = "\
    ${CORE_IMAGE_BASE_INSTALL} \
    packagegroup-core-full-cmdline \
    packagegroup-core-lsb \
    packagegroup-core-qt4-libs \
    "

inherit core-image

inherit distro_features_check
REQUIRED_DISTRO_FEATURES = "x11"
