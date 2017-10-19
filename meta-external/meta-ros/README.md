This is a layer to provide ROS Kinetic Kame in an OpenEmbedded Linux system.
It provides a stable cross-compilation build system for many common ROS packages. 
Currently, this layer is still under continuous development.

This is an experimental local branch for the kinetic distribution at:

    https://github.com/bulwahn/meta-ros/tree/kinetic-experimental

I, Lukas Bulwahn, have tested to build it, and currently, most packages build
without issues. The packages that fail on kinetic have already failed to build
(with indigo) on the current master.

Still please remember that this is an experimental local branch:

  - First, do not expect that this branch has been tested with many different build
    configurations, so possibly, your build configuration could cause build failure
    we are unaware of.
  - Second, this branch is work in progress, and hence, the commits of this branch
    will be rebased and modified, and the git history will change in a non-monotonic
    way in the future.

Nevertheless, go ahead and try this branch and report issues if you encounter them.

## IMPORTANT RESOURCES ##

  * Source Code Repository: https://github.com/bmwcarit/meta-ros.git
  * Issue Tracker: https://github.com/bmwcarit/meta-ros/issues
  * Mailing List: https://groups.google.com/forum/#!forum/meta-ros
  * Installation Guide: http://wiki.ros.org/hydro/Installation/OpenEmbedded
  * Development Guides:
    * https://github.com/bmwcarit/meta-ros/wiki/Guidelines-for-ROS-recipes
    * https://github.com/bmwcarit/meta-ros/wiki/Developer-Guidelines


## MAINTAINERS ##

  * Lukas Bulwahn, BMW Car IT GmbH <lukas.bulwahn@oss.bmw-carit.de>
  * Stefan Herbrechtsmeier, Bielefeld University <stefan@herbrechtsmeier.net>
  * Kristof Robot <krirobo@gmail.com>


## CONTRIBUTORS ##

  * Lukas Bulwahn, BMW Car IT GmbH <lukas.bulwahn@oss.bmw-carit.de>
  * Stefan Herbrechtsmeier, Bielefeld University <stefan@herbrechtsmeier.net>
  * Tobias Weigl, BMW Car IT GmbH <tobias.weigl@bmw-carit.de>
  * Kartik Mohta <kartikmohta@gmail.com>
  * Koen Kooi <koen@dominion.thruhere.net>
  * Victor Mayoral Vilches <v.mayoralv@gmail.com>
  * Sergey 'Jin' Bostandzhyan <jin@dev.digitalstrom.org>
  * Andreas Hildisch, BMW Car IT GmbH <andreas.hildisch@bmw-carit.de>
  * William <william@friedcircuits.us>
  * Kristof Robot <krirobo@gmail.com>
  * Khem Raj <raj.khem@gmail.com>
  * Esteve Fernandez <esteve@apache.org>
  * Jonas Sticha <jonas.sticha@bmw-carit.de>
  * Pascal Bach <pascal.bach@siemens.com>
  * Andreas Galauner <andreas@galauner.de>
  * Peter S. Housel <housel@acm.org>
  * Gauthier Monserand <simkim@simkim.net>
  * Timo Mueller <timo.mueller@bmw-carit.de>
  * Andreas Baak <andreas.baak@bmw-carit.de>


## HOW TO CONTRIBUTE ##

  We are still working on this development and are interested in other use cases.
  If you are interested in this project, please contact us via email.
  The more people are interested, the more we will be pushing this project.
  If you want to contribute, please contact us and we can discuss open issues
  and how to join forces.


## DEPENDENCIES ##

  This layer depends on:

  **openembedded-core**  
  URI: git://git.openembedded.org/openembedded-core  
  subdirectory: meta  
  branch: master  
  revision: HEAD  

  **meta-openembedded (meta-oe)**  
  URI: git://git.openembedded.org/meta-openembedded  
  subdirectory: meta-oe  
  branch: master  
  revision: HEAD  

  **meta-python** (since a6d7ba92645a112af358efd94ff3aa0c74985a51@meta-openembedded)  
  URI: git://git.openembedded.org/meta-openembedded  
  subdirectory: meta-python  
  branch: master  
  revision: HEAD  
  
  **meta-multimedia** (since fe44ac167a2a76531af3519f3889fce92024567b@meta-openembedded)  
  URI: git://git.openembedded.org/meta-openembedded  
  subdirectory: meta-multimedia  
  branch: master  
  revision: HEAD  

  **bitbake** > 1.20


## DEPENDENCIES ON RECENT COMMITS ##

  Here, we list which parts of this layer depend on recent commits in the
  bitbake, openembedded-core and meta-openembedded repositories. Using
  `git blame` on the lines below, you can find the commits in meta-ros that
  rely on the remote commits. In case of porting to other versions, you must
  possibly revert some of those commits.

  cv-bridge and dependent recipes, e.g., the image-transport recipes, depend on
  7568bfdd114597956a1da68746f207ec7f93a48d@openembedded-core.
  For native python-numpy support, these recipes also depend on
  c13c5f40160d289bb62538a16900fed30621cb22@meta-openembedded or on
  9bf355cceaec6ebacdcbcc35f9713ff73e1c85da@openembedded-core.

  Some recipes that need the Eigen library, e.g., the pcl-ros recipe, depend on
  424e3c1b930c0103c2cedfd4df1671e84a5256d5@meta-openembedded.

  Using wildcards in bbappend versions part in name depends on
  31bc9af9cd56e7b318924869970e850993fafc5f@bitbake and
  991cbeedbde8bd25ce08c669b1bfac8b99e33149@bitbake.

  octomap-ros and collada-urdf depend on
  43073569cb67d98c11aa71211d77b566b64f9145@openembedded-core and
  783fb88f476c94d5d4f4b954f7053464d9a6dff5@openembedded-core.

  urdfdom-py depends on the meta-python layer since
  a6d7ba92645a112af358efd94ff3aa0c74985a51@meta-openembedded.

  Since 3edf08b38b0af93cef0933b061349264dc86d54c@openembedded-core,
  recipes relying on opencv need to activate the DISTRO\_FEATURE
  opengl.

## INSTALLATION ##

  The repository contains a layer for ROS that builds on top of the
  OpenEmbedded Core layer and the meta-oe layer.

  We try our best to keep up with the development of the current HEAD
  of the layers mentioned above. If you notice any problems with the
  current HEAD, please report this in our issue tracker.

  You can use this layer with earlier versions of the layers mentioned above
  and their release branches, dora and daisy, with a few minor adjustments.
  The section "dependencies on recent commits" in this file provides a few
  pointers to adjust this layer for other versions.


## USAGE ##

  Initialize the build environment:

    source oe-init-build-env

  Add the required layers (see **DEPENDENCIES**) by modifying ``./conf/bblayers.conf`` (adjust ``/home/me/devel`` as necessary): 

    BBLAYERS ?= " \
    /home/me/devel/openembedded-core/meta \
    /home/me/devel/meta-openembedded/meta-oe \
    /home/me/devel/meta-openembedded/meta-python \
    /home/me/devel/meta-openembedded/meta-multimedia \
    /home/me/devel/meta-ros \
    "

  Compile package:

    bitbake <package-name>

  The recipe core-image-ros-roscore provides a minimal Linux system that runs
  roscore.

  You can compile the minimal Linux system with

    bitbake core-image-ros-roscore

  Then for example, you start this system in the qemu virtual machine with

    runqemu <MACHINE> core-image-ros-roscore
  
  On the Linux system, ensure that the own host's name in resolved by adding
  
    127.0.0.1	<HOSTNAME>.localdomain		<HOSTNAME>

  to the /etc/hosts file, and set up the environment with

    export ROS_ROOT=/opt/ros/indigo
    export ROS_DISTRO=indigo
    export ROS_PACKAGE_PATH=/opt/ros/indigo/share
    export PATH=$PATH:/opt/ros/indigo/bin
    export LD_LIBRARY_PATH=/opt/ros/indigo/lib
    export PYTHONPATH=/opt/ros/indigo/lib/python2.7/site-packages
    export ROS_MASTER_URI=http://localhost:11311
    export CMAKE_PREFIX_PATH=/opt/ros/indigo
    touch /opt/ros/indigo/.catkin

  Finally, you can start roscore with

    roscore


## CROSS-COMPILING ALL ROS PACKAGES IN meta-ros
  
  The meta-ros layers only includes a subset of the officially released ROS
  packages, available from packages.ros.org. The here provided ROS packages
  are mainly driven by the current users' needs.
  
  For ROS packages that depend on cv-bridge, the commercial license flag
  must be whitelisted, as cv-bridge depends on opencv, and opencv by
  default depends on libav that has special terms and conditions when used
  commercially.
  This can be done by adding to the local.conf the line: 

    LICENSE_FLAGS_WHITELIST = "commercial"

  Alternatively, the dependency from opencv on libav can be explicit excluded
  by modifying the PACKAGECONFIG setting.


## LICENSE ##

  All metadata is MIT licensed unless otherwise stated. Source code included
  in tree for individual recipes is under the LICENSE stated in each recipe
  (.bb file) unless otherwise stated.
  The descriptions in the recipes of ROS packages have been extracted from
  the ROS wiki (http://www.ros.org/wiki/) and are licensed under 
  Creative Commons Attribution 3.0 (http://creativecommons.org/licenses/by/3.0/)
  unless otherwise noted.

  This README document is Copyright (C) 2012 BMW Car IT GmbH.


## REDISTRIBUTIONS ##

  The log4cxx recipe originated from the recipe in the OpenEmbedded (Classic) Development
  (cf. http://cgit.openembedded.org/openembedded/tree/recipes/log4cxx)
  licensed with the MIT License.

  The yaml-cpp recipe originated from the recipe in Kartik Mohta's OpenEmbedded layer
  (cf. https://github.com/kartikmohta/meta-km/blob/master/recipes-devtools/yaml-cpp/yaml-cpp_0.3.0.bb)
  licensed with the MIT License.

  The libpoco recipe originated from the recipe in digitalSTROM's OpenEmbedded layer
  (cf. https://gitorious.digitalstrom.org/dss-oe/dss-oe/blobs/master/dS/meta-dss11-production/recipes-support/poco/poco_1.3.6p2.bb)
  licensed with the MIT License.

  The core-image-ros-* recipes originated from the core-image-minimal recipe in OpenEmbedded Core
  (cf. http://cgit.openembedded.org/openembedded-core/tree/meta/recipes-core/images/core-image-minimal.bb)
  licensed with the MIT License.

  The original or modified files are redistributed here under the same MIT License.
