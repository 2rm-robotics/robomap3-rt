DESCRIPTION = "A console-only image that fully supports the target device \
hardware."

LICENSE = "MIT"

inherit core-image


IMAGE_FEATURES += "package-management"
IMAGE_INSTALL += "${CORE_IMAGE_BASE_EXTRA_INSTALL}"

# Set the default Window System for integration with Qt/Embedded
rootfs_update_powervr_windowsystem () {
	echo "[default]" >${IMAGE_ROOTFS}/etc/powervr.ini
	echo "WindowSystem=libpvrQWSWSEGL.so.1" >>${IMAGE_ROOTFS}/etc/powervr.ini
}
