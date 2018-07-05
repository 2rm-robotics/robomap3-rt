# The robomap3-rt projet

Important note : this project was used for specific goals and is shared without any modifications. It's root (sorry...), we hope that we will have time to improve it and make the project more user-friendly.

[Other note : if Yocto, open embedded or Poky means nothing for you, please check first what it is !](https://www.yoctoproject.org/)

### Initial project

The robomap3-rt project is an initiative of French research laboratories to share a layer for the Yocto platform builder for TI's OMAP3 processor. It includes recipes to build images, kernels and cross-toolchains.

The initial git for the project uses [SourceSup](https://sourcesup.renater.fr/projects/robomap3-rt/), a French forge dedicated to higher eduction and research.

More recently the project has been used to target a Raspberry Pi using ROS. Those recipes already exists but we use them to generate a cross-toolchain in order to cross-compile ROS programs without the need of all the bitbake stuff.

### About the layers

As a lot of Yocto projects, robomap3-rt uses a lot of other recipes developed by the community (thanks all !). To avoid version conflicts and in order to manage and modify the recipes without problems, all of them are shared directly in the project (no submodules here).

All these layers are stored in the meta-external folder. Excluding all openembedded and common layers (python, Qt), we use a [layer for ROS](https://github.com/bmwcarit/meta-ros) (thanks so much for sharing these recipes !) and two layers for Raspberry Pi ([here](https://github.com/agherzan/meta-raspberrypi) and [here](https://github.com/jumpnow/meta-rpi)).

Then we have the meta-kernel-omap and meta-xenomai-omap which contains the recipes for the OMAP3 processor.

Finally the last folder, meta-poky, contains all the extra recipes to build a Poky version we want to use.

### About flair, hds and other strange words

As said in introduction, the initial project was very specific and used to fly ardrone2 uavs and other stuff using IGEPv2 boards.

What we wall "flair" is in fact the name of a framework developed by the Heudiasyc laboratory to fly uavs (more information [here](https://devel.hds.utc.fr/software/flair/wiki)).

"hds" names the Heudiasyc laboratory. You will find a lot of "recipes-hds" folders which contains recipes developed for the needs of the Heudiasyc platforms.

You may found some other strange things like that, don't panic, they won't beat you ;)

### About the cross-compilation folder

For now the project just uses other layers from the community to build a specific system using OMAP3 processor. Sharing is a good thing, but where the real added value ? The cross-compilation folder.

Indeed the cross-compilation folder contains the packages to let you cross-compile ROS nodes, using a generated toolchain instead of the whole system (no bitbake here !) and the correct stuff to make your cross-compiled nodes work on your target.

For more information, see the section [below](#ros-nodes-cross-compilation). 

## Quick start

### Installation

TODO setup init-env, machine...

**Important** : all the recipes have been tested with Poky Krogoth v2.1.3.

### Compilation

Once everything is installed and setup just launch bitbake.

##### Image

To generate the Raspberry Pi image, which includes ROS : 

```
bitbake core-image-flair-ros
```

You can find the generated image in build/tmp/deploy/images...

Or you can download directly the toolchain :

```
wget https://uav.hds.utc.fr/src/rpi/latest/core-image-flair-ros-rpi-hds.rpi-sdimg
```

##### Toolchain

To generate the cross-toolchain, which enables you to cross-compile ROS nodes for your Raspberry Pi system :

```
bitbake meta-toolchain-flair-ros
```

You can find the generated toolchain in build/tmp/deploy/sdk/...

Or you can download directly the toolchain :

```
wget https://uav.hds.utc.fr/src/toolchain/robomap3-glibc-x86_64-meta-toolchain-flair-ros-cortexa7hf-neon-vfpv4-2.1.3.sh
```

### Utilisation

For the image you just have to flash and SD card, for instance using dd utility (image.sdimage) or xzcat (image.xz).

For the toolchain you just have to execute it (chmod +x the file before) on your computer to install the toolchain (in /opt/robomap3/2.1.3/... by default). 

## ROS nodes cross-compilation

TODO packages, recipe modifications for cross-compilation, setup on host, setup on target, how to use, how to extend...