SRC_URI = "https://github.com/ros-drivers/${ROS_SPN}/archive/${PV}.tar.gz;downloadfilename=${ROS_SP}.tar.gz"
SRC_URI[md5sum] = "499f06b971b1a72fb61159d606a0aac6"
SRC_URI[sha256sum] = "afe8fc4018805d97a70f959598c3928282d4a7b2513bcb7666196622018b1de9"

S = "${WORKDIR}/${ROS_SP}/${ROS_BPN}"

inherit catkin

ROS_SPN = "rosserial"
