SUMMARY = "Host packages for the utcoupe host toolchain"
PR = "r1"
LICENSE = "MIT"

inherit packagegroup nativesdk

#PACKAGEGROUP_DISABLE_COMPLEMENTARY = "1"

DEPENDS = "\
    nativesdk-catkin\
"

RDEPENDS_${PN} = "\
    "
