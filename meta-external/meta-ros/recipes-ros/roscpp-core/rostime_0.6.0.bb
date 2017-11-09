DESCRIPTION = "Time and Duration implementations for C++ libraries, including roscpp."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=8;endline=8;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "boost cpp-common console-bridge"
DEPENDS_class-nativesdk += "nativesdk-cpp-common nativesdk-console-bridge"

require roscpp-core.inc
