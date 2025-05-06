#! /bin/bash

set -e

if ! grep 'Ubuntu 16.04' /etc/lsb-release > /dev/null
then
    echo "Script not started in ubuntu 16.04. Please run this script in a docker container as described in the repo's README." >&2
    exit
fi

declare -rA SUPPORTED_DRONES=(
    [ardrone2]=core-image-flair
    [uav]=core-image-flair
    [mambo]=core-image-flair
    [bebop]=core-image-flair
    [beaglebone-blue]=core-image-flair
    [beaglebone-hds]=core-image-flair
    [ardrone2-updater]=core-image-minimal-mtdutils
    [ardrone2-installer]=core-image-minimal-mtdutils
    [rpi-hds]=core-image-flair-ros
)

drone=$1

if [[ -z "$drone" ]]
then
    echo "Missing argument. Please provide a drone name within ${!SUPPORTED_DRONES[@]}" >&2
    exit 1
fi
shift
if [[ -z "${SUPPORTED_DRONES[${drone}]}" ]]
then
    echo "Unknown drone '$drone'. Please provide a drone name within ${!SUPPORTED_DRONES[@]}" >&2
    exit 1
fi

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

sed -i 's@^MACHINE=".*?"'@MACHINE="$drone"@g /workdir/build/conf/local.conf

source /workdir/poky-krogoth-15.0.3/oe-init-build-env
bitbake "${SUPPORTED_DRONES[$drone]}"
