include udev.inc

PR = "r17"

SRC_URI += "file://udev-166-v4l1-1.patch \
            file://include_resource.patch \
		file://10-persistent-net-generator.rules \
           "
SRC_URI[md5sum] = "fddac2d54761ea34865af9467377ca9f"
SRC_URI[sha256sum] = "c12e66280b5e1465f6587a8cfa47d7405c4caa7e52ce5dd13478d04f6ec05e5c"
