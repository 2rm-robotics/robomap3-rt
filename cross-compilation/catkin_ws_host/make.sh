#!/bin/bash

# This is a simple script to launch the compilation process. It just launches catkin_make with the correct environment and the correct options for cross-compilation.

# Source ROS cross compilation environment
. setup.sh

# Launch catkin_make install
catkin_make -DCMAKE_TOOLCHAIN_FILE=/opt/robomap3/2.1.3/cortexa7thf-neon-vfpv4/toolchain.cmake \
-DPYTHON_EXECUTABLE=/opt/robomap3/2.1.3/cortexa7thf-neon-vfpv4/sysroots/x86_64-pokysdk-linux/usr/bin/python \
-DNOSETESTS=/opt/robomap3/2.1.3/cortexa7thf-neon-vfpv4/sysroots/x86_64-pokysdk-linux/usr/bin/nodetests install
