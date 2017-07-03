DESCRIPTION = "Target packages for the standalone ROS SDK"
PR = "r1"
LICENSE = "MIT"

inherit packagegroup

RDEPENDS_${PN} = "\
    catkin-dev \
    catkin-runtime-dev \
    message-generation-dev \
    gencpp-dev \
    genmsg-dev \
    genlisp-dev \
    genpy-dev \
    std-msgs-dev \
    message-runtime-dev \
    cpp-common-dev \
    roscpp-serialization-dev \
    geometry-msgs-dev \
    roscpp-dev \
    rosconsole-dev \
    rosgraph-msgs-dev \
    roslang-dev \
    xmlrpcpp-dev \
    log4cxx-dev \
    mavros-dev \
    mavros-msgs-dev \
    mavros-extras-dev \
    tf-dev \
    sensor-msgs-dev \
    tf2-dev \
    tf2-geometry-msgs-dev \
    tf2-kdl-dev \
    tf2-msgs-dev \
    tf2-py-dev \
    tf2-tools-dev \
    python-empy \
    "
