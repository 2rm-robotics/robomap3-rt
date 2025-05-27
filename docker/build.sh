#! /bin/bash

set -e

if ! grep 'Ubuntu 16.04' /etc/lsb-release > /dev/null
then
    echo "Script not started in ubuntu 16.04. Please run this script in a docker container as described in the repo's README." >&2
    exit
fi

declare -rA TARGETS=(
    [ardrone2]=core-image-flair
    [uav]=core-image-flair
    [mambo]=core-image-flair
    [bebop]=core-image-flair
    [beaglebone-blue]=core-image-flair
    [beaglebone-hds]=core-image-flair

    [ardrone2-updater]=core-image-minimal-mtdutils
    [ardrone2-installer]=core-image-minimal-mtdutils

    [rpi-hds]=core-image-flair-ros

    [toolchain-ardrone2]=meta-toolchain-flair
    [toolchain-uav]=meta-toolchain-flair
    [toolchain-mambo]=meta-toolchain-flair
    [toolchain-bebop]=meta-toolchain-flair
    [toolchain-genericx86-64]=meta-toolchain-flair

    [toolchain-rpi-hds]=meta-toolchain-flair-ros
)

TARGET=$1

if [[ -z "$TARGET" ]]
then
    echo "Missing argument. Please provide a target name within ${!TARGETS[@]}" >&2
    exit 1
fi
shift
if [[ -z "${TARGETS[${TARGET}]}" ]]
then
    echo "Unknown target '$TARGET'. Please provide a target name within ${!TARGETS[@]}" >&2
    exit 1
fi

sudo mkdir -p /workdir/build/conf
sudo chmod 777 /workdir/build{,/conf}

if [ ! -f /workdir/build/conf/bblayers.conf ]
then
    cp /workdir/build/conf/bblayers.conf{.sample,}
    cp /workdir/build/conf/local.conf{.sample,}

    if [ -d /workdir/meta-hds/conf ]
    then
        sed -i 's@  "@  /workdir/meta-hds \\ \n  "@g' /workdir/build/conf/bblayers.conf
    fi
fi

ARCH=${TARGET#toolchain-*}
sed -i 's@^MACHINE=".*"'@MACHINE="\"$ARCH\""@g /workdir/build/conf/local.conf

source /workdir/poky-krogoth-15.0.3/oe-init-build-env
bitbake -k "${TARGETS[$TARGET]}"
