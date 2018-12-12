# The robomap3-rt projet

Important note : this project was used for specific goals and is shared without any modifications. It's root (sorry...), we hope that we will have time to improve it and make the project more user-friendly.

[Other note : if Yocto, OpenEmbedded or Poky means nothing for you, please check first what it is !](https://www.yoctoproject.org/)

Summary :
  * [Introduction](#introduction)
  * [Quick start](#quick-start)
  * [ROS nodes cross-compilation](#ros-nodes-cross-compilation)

## Contributors

  * Thomas Fuhrmann (@furmi), Heudiasyc Laboratory : tomesman@gmail.com
  * Guillaume Sanahuja, Heudiasyc Laboratory
  * Gildas Bayard, Heudiasyc Laboratory
  * Gerald Dherbomez (@gdherbom), CRIStAL Laboratory: gerald.dherbomez@univ-lille.fr

## Introduction

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

What we call "flair" is in fact the name of a framework developed by the Heudiasyc laboratory to fly uavs (more information [here](https://devel.hds.utc.fr/software/flair/wiki)).

"hds" names the Heudiasyc laboratory. You will find a lot of "recipes-hds" folders which contains recipes developed for the needs of the Heudiasyc platforms.

You may found some other strange things like that, don't panic, they won't beat you ;)

### About the cross-compilation folder

For now the project just uses other layers from the community to build a specific system using OMAP3 processor. Sharing is a good thing, but where the real added value ? The cross-compilation folder.

Indeed the cross-compilation folder contains the packages to let you cross-compile ROS nodes, using a generated toolchain instead of the whole system (no bitbake here !) and the correct stuff to make your cross-compiled nodes work on your target.

For more information, see the section [below](#ros-nodes-cross-compilation).

## Quick start

Everything has been tested on Ubuntu 16.04 / Mint 18.

If you are on a newer version, please consider using docker (otherwise build will fail); see below.


### Additionnal packages

Install the following packages

```
sudo apt install git texinfo chrpath g++
```

### Using docker on newer linux distros

If you don't have it, install docker CE from https://docs.docker.com/glossary/?term=installation

Create a directory for poky stuffs (avoid using a directory within an ecryptfs like home), for exemple:
```
mkdir -p /opt/poky/workdir
```
And be sure your user owns this directory. This directory will be accessible from your host and from the container.

then run the container: (do it each time you need to build robomap3)
```
sudo docker run --net=host --rm -it -v /opt/poky/workdir:/workdir crops/poky:ubuntu-16.04 --workdir=/workdir
```

and then follow the tutorial, using the workdir directory to share files between host and container.

### Installation

If you don't have an OpenEmbedded system installed, [please consult this documentation to setup your system first](https://www.yoctoproject.org/docs/2.4.2/yocto-project-qs/yocto-project-qs.html).

**Important** : all the recipes have been tested with **Poky 2.1.3 (Krogoth-15.0.3)**. You can find the download links [here](https://lists.yoctoproject.org/pipermail/yocto-announce/2017-July/000117.html). A recent version will not work because we use the toolchain to compile ardrone uav kernel which is a 2.6.x version and starting from Poky Morty, it supports only 3.x kernel versions.

We suppose that all your developments are located in the same place and we will use the variable ${YOUR_SRC_PATH} to refer to this path.

Once you get the Poky recipes, just clone this repository in order to get all recipes. You won't need more recipes in order to compile.

```
cd ${YOUR_SRC_PATH}
git clone https://github.com/2rm-robotics/robomap3-rt.git
```

Next step, source your environment (among other features, it will permit you to use the bitbake command):

```
source ${YOUR_SRC_PATH}/poky-krogoth-15.0.3/oe-init-build-env
```

We don't use a system to generate automatically all stuff to launch bitbake from here. You have to add the layers and some config in build/conf files.

#### Setup the layers for the Raspberry Pi target

Modify the build/conf/bblayers.conf to add all the layers. Don't forget to replace ${YOUR_SRC_PATH} by the path you clone Poky and robomap3-rt.

```
BBLAYERS ?= " \
  ${YOUR_SRC_PATH}/poky-krogoth-15.0.3/meta \
  ${YOUR_SRC_PATH}/poky-krogoth-15.0.3/meta-poky \
  ${YOUR_SRC_PATH}/poky-krogoth-15.0.3/meta-yocto-bsp \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-oe \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-qt4 \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-python \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-ros \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-isee \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-xenomai-omap \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-kernel-omap \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-poky \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-raspberrypi \
  ${YOUR_SRC_PATH}/robomap3-rt/meta-external/meta-rpi \
  "
```

#### Setup the compilation configuration for the Raspberry Pi target

Edit the build/conf/local.conf file to adjust some configuration parameters.

The machine we build is called rpi-hdsn which is based on the raspberrypi machine.

```
MACHINE="rpi-hds"
DISTRO ?= "robomap3"
PACKAGE_CLASSES ?= "package_ipk"
```

We have some extra distro features, maybe you don't need it but by default, you should consider putting them for your first compilation :

```
DISTRO_FEATURES_DEFAULT ?= "alsa argp bluetooth largefile usbgadget usbhost wifi xattr nfs"
DISTRO_FEATURES_DEFAULT_genericx86-64 ?="alsa argp bluetooth largefile usbgadget usbhost wifi xattr nfs x11"
```

If you are short in disk space, you can add a configuration to remove all build files (it saves a lot of space !) :

```
INHERIT += "rm_work"
```

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

### How to use it ?

For the image you just have to flash and SD card, for instance using dd utility (image.sdimage) or xzcat (image.xz).

For the toolchain you just have to execute it (chmod +x the file before) on your computer to install the toolchain (in /opt/robomap3/2.1.3/... by default).
If you don't use the default path, consider to modify a lot of things for [the nodes cross-compilation](#ros-nodes-cross-compilation).

## ROS nodes cross-compilation

TODO packages, recipe modifications for cross-compilation, setup on host, setup on target, how to use, how to extend...

### ROS nodes cross-compilation on host

All stuff can be found in the cross-compilation/catkin_ws_host folder.
In order to use it you should copy the folder elsewhere.

For instructions about how to use it, please read [the corresponding README file](cross-compilation/catkin_ws_host/README.md).

### ROS cross-compiled nodes on target

On the target you just need to create a folder where you will add your cross-compiled nodes and to adapt your environment so that ROS can find your cross-compiled nodes.

We choose to create a folder named **ros_packages** in the home folder of the default user on the Raspberry Pi (it's root and not pi in our generated image). You can rename the folder or put it elsewhere, just adapt the environment path and the upload script (see [the corresponding README file](cross-compilation/catkin_ws_host/README.md)).

Please add this in your bashrc file (an example of the .profile and .bashrc files is available in the cross-compilation/ros_packages_target folder) :

```bash
# ROS environment
export ROS_PACKAGES_WORKSPACE=/home/root/ros_packages
export ROS_DISTRO=kinetic
export ROS_ROOT=/opt/ros/$ROS_DISTRO
export ROS_PACKAGE_PATH=$ROS_ROOT/share:$ROS_PACKAGES_WORKSPACE/share
export PATH=$PATH:$ROS_ROOT/bin
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$ROS_ROOT/lib:$ROS_PACKAGES_WORKSPACE/lib
export PYTHONPATH=$ROS_ROOT/lib/python2.7/site-packages:$ROS_PACKAGES_WORKSPACE/lib/python2.7/dist-packages
export CMAKE_PREFIX_PATH=$ROS_ROOT
export PKG_CONFIG_PATH=$ROS_PACKAGES_WORKSPACE/lib/pkgconfig:$ROS_ROOT/lib/pkgconfig
export CMAKE_PREFIX_PATH=$ROS_PACKAGES_WORKSPACE:$ROS_ROOT
export ROS_ETC_DIR=$ROS_ROOT/etc/ros
export ROS_MASTER_URI=http://localhost:11311
```

Once it's done you just have to put your cross-compiled ROS nodes (see [the cross-compilation on host for more details](#ros-nodes-cross-compilation-on-host)) and to launch your system (rosrun or roslaunch) !

### Limitations

For now the system is working but it faces plenty of limitations :
  * The toolchain and the Raspberry Pi images have only ROS packages we need, it's far from a complete ROS installation
  * The Raspberry Pi image is kind of poor, we just add the minimal stuff we need
  * There is no auto-completion of ROS stuff on the Raspberry Pi image (this is really annoying !!)
  * All the process can be more automated and reliable

### How to extend ?

That's the real question, how to extend the current system in particular to add more ROS nodes in toolchain and image ?

It depend on what you want to extend. The easy part is to had some stuff in the Raspberry Pi image. The hard part could be to add some stuff in the toolchain, depending on what you want to add.

We created a packagegroup dedicated for the host toolchain, for the target toolchain and for the generated image.

#### Add Things in the host toolchain

The file you have to modify is : **robomap3-rt/meta-external/meta-ros/recipes-hds/meta/nativesdk-packagegroup-ros-toolchain-host.bb**

In this file you can see a list of ROS components, just add yours at the end of the file.

Don't forget the "nativesdk-" prefix !

On the other side, the recipe you want to add may not be ready for nativesdk. You have to modify the recipe directly and to add, at least :

```
DEPENDS_class-nativesdk += "nativesdk-catkin"
BBCLASSEXTEND += "nativesdk"
```

After this modification it's the hard part : it can work just like that or not, because of other dependencies. From here, it's up to you to make it work ;)

#### Add Things in the target toolchain

The file you have to modify is : **robomap3-rt/meta-external/meta-ros/recipes-hds/meta/packagegroup-ros-toolchain-target.bb**

In this file you can see a list of ROS components, just add yours at the end of the file.

Don't forget the "-dev" suffix, as the packages in the target toolchain will be used for the cross-compilation. So if you want to cross-compile a node depending on an other ROS package, you have to install this other package in your target toolchain (with "-dev") in order to have all the stuff for compilation (like cmake files).

#### Add Things in Raspberry Pi image

The image we build is a core-image-flair-ros (file : robomap3-rt/meta-poky/recipes-hds/images/core-image-flair-ros.bb), which is based on the packagegroup-ros-mavlink packagegroup (file : robomap3-rt/meta-external/meta-ros/recipes-ros/packagegroups/packagegroup-ros-mavlink.bb).

So the simplest thing to do is to add the packages you want in the packagegroup-ros-mavlink file if it concerns ROS or in the core-image-flair-ros if not.

### What has been modified to make it work ?

Except all the nativesdk directives to add in a lot of ROS packages and to resolve some associated problems, it's not enough to make the cross-compilation work. In fact with these modifications you can just generate the toolchain. And even if you have the correct toolchain file for cmake and the correct paths (see [the cross-compilation on host section](#ros-nodes-cross-compilation-on-host)), it won't work.

In fact there is 2 major problems : the cmake files for ROS components generated for the host and target don't have correct paths (they use those from the machine which generated the toolchain), the consequence is that at cross-compilation time, the ROS packages and libraries will ot be located.
The second problem comes from ROS Python build tools which don't source the correct Python binary, due to the generation of a cross-compilation toolchain.

We resolve all these problems when the toolchain is installed and not directly by changing ROS recipes (or ROS source). See the shar file : robomap3-rt/meta-poky/recipes-hds/meta/files/toolchain-shar-template.sh.
Somewhere you can find a comment like that : #ROS CROSS-COMPILATION STUFF. Below this comment you have some bash stuff to replace wrong paths and resolve the problems for cross-compilation.
