DESCRIPTION = "A hub acts as a shared key-value store for multiple ros systems (primarily used by gateways)."
SECTION = "devel"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://package.xml;beginline=11;endline=11;md5=d566ef916e9dedc494f5f793a6690ba5"

DEPENDS = "roslint"

require rocon-multimaster.inc

RDEPENDS_${PN} = "gateway-msgs python-cryptography rospy rocon-hub-client \
  rocon-console rocon-python-comms rocon-python-redis rocon-gateway-utils \
  rocon-python-utils rocon-python-wifi roslib rostopic rosservice rosgraph \
  std-srvs zeroconf-msgs zeroconf-avahi"
