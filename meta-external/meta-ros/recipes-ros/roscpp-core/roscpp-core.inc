SRC_URI = "https://github.com/ros/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "f77cfefa90b0ab568beb3707651f3c70"
SRC_URI[sha256sum] = "352fdf2bce0efc562da70dd9ec72691145d8e9eb7a653a8da1b064553be3540b"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "roscpp_core"
