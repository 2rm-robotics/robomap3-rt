
IMAGE_FEATURES += "package-management"

CORE_IMAGE_EXTRA_INSTALL += "\
	igep-x-loader \
	poky-feed-config-rpm \
	qt4-embedded-plugin-gfxdriver-gfxpvregl \
	libqt-embeddedpvrqwswsegl4 \
	"

# Set the default Window System for integration with Qt/Embedded
rootfs_update_powervr_windowsystem () {
	echo "[default]" >${IMAGE_ROOTFS}/etc/powervr.ini
	echo "WindowSystem=libpvrQWSWSEGL.so.1" >>${IMAGE_ROOTFS}/etc/powervr.ini
}

ROOTFS_POSTPROCESS_COMMAND += "rootfs_update_powervr_windowsystem ; "
