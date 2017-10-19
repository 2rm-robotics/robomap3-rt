DESCRIPTION = "A console-only image that fully supports the target device hardware, with ROS"

LICENSE = "MIT"

inherit core-image

IMAGE_FEATURES += "package-management"
IMAGE_INSTALL += "${CORE_IMAGE_BASE_EXTRA_INSTALL} packagegroup-flair packagegroup-ros-mavlink"
