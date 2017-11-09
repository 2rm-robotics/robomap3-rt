DESCRIPTION = "roscpp-serialization contains the code for serialization."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=11;endline=11;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "cpp-common roscpp-traits rostime"
DEPENDS_class-nativesdk += "nativesdk-cpp-common nativesdk-roscpp-traits nativesdk-rostime"

require roscpp-core.inc
