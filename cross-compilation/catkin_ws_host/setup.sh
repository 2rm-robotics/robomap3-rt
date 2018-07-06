#!/bin/sh

# ROS environment variables for cross-compilation

export ROS_DISTRO=kinetic
export ROBOMAP_SYSROOTS=/opt/robomap3/2.1.3/cortexa7thf-neon-vfpv4/sysroots
# Host part
export ROS_ROOT_HOST=$ROBOMAP_SYSROOTS/x86_64-pokysdk-linux/opt/ros/$ROS_DISTRO
export PYTHONPATH=$ROS_ROOT_HOST/lib/python2.7/site-packages
export ROS_ETC_DIR=$ROS_ROOT_HOST/etc/ros
# Target part
export ROS_ROOT_TARGET=$ROBOMAP_SYSROOTS/cortexa7hf-neon-vfpv4-poky-linux-gnueabi/opt/ros/$ROS_DISTRO
export ROS_PACKAGE_PATH=$ROS_ROOT_TARGET/share
export CMAKE_PREFIX_PATH=$ROS_ROOT_TARGET
export LD_LIBRARY_PATH=$ROS_ROOT_TARGET/lib
export PKG_CONFIG_PATH=$ROS_ROOT_TARGET/lib/pkgconfig
export PATH=$PATH:$ROS_ROOT_TARGET/bin
export ROS_MASTER_URI=http://localhost:11311