DESCRIPTION = "Shared communication types for the concert framework."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=7;endline=7;md5=5ee5b8b046ae48ad94a2037ca953a67b"

DEPENDS = "message-generation std-msgs uuid-msgs gateway-msgs rocon-app-manager-msgs rocon-std-msgs"

require rocon-msgs.inc

RDEPENDS_${PN} = "message-runtime"
