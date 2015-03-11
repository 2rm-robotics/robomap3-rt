FILESEXTRAPATHS_prepend := "${THISDIR}/u-boot-2014.01:"

SRC_URI += " \
			file://0009-Make-800MHz-the-default-mpurate.patch \
         file://0010-Disable-classic-uart-driver-for-uart-1-ttyO0-and-3-t.patch \
         file://0011-Disable-classic-i2c-driver-for-i2c-3-via-u-boot-argu.patch \
         file://0012-Disable-classic-spi-driver-spidev-for-SPI1-CS0-and-C.patch \
"

