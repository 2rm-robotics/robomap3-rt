Yocto layer for Xenomai Real-time support for various SoCs. 

This layer implements xenomai on the following SoCs:

== gumstix overo ==

Layer depends on:

		URI: git://git.yoctoproject.org/poky.git
		branch: daisy
		revision: HEAD

		URI: git://github.com/augustinmanecy/meta-rt-mag-overo.git
		branch: master
		revision: HEAD
		
		meta-xenomai layer maintainer: Augustin Manecy <augustin.manecy@gmail.com>

Before build, modify your bblayers.conf:
	1. add meta-xenomai layer
	2. Add: 
	BBMASK = "meta-xenomai/recipes-images/images/core-xenomai-rpi-image|meta-xenomai/recipes-kernel/linux/linux-xenomai-raspberrypi_3.8.13"
to prevent errors from RPi recipes.

To build:
	bitbake rt-mag-image.

Image tested with Overo AirSTORM.

