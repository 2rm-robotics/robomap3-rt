IMAGE_INSTALL += "mtd-utils-ubifs"

DEPENDS+="plftool-native"


IMAGE_CMD_cpio_append () {
plftool	
}
