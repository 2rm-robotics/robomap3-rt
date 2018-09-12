DESCRIPTION = "vrpn"
LICENSE = "BSL-1.0"
LIC_FILES_CHKSUM = "file://README.Legal;md5=a346ececf6ce1f9b8f6342a42ea115b7"
PR = "r0"

SRC_URI = "https://github.com/vrpn/vrpn/releases/download/v07.33/vrpn_07_33.zip \
		file://lib.patch \
		file://no_python.patch \
		file://version.patch \
		file://install.patch \
		file://fix_glibc_union_wait_removal.patch \
"

SRC_URI[md5sum] = "634ea3d80504d6b861c5e7758ba21a52"
SRC_URI[sha256sum] = "3cb9e71f17eb756fbcf738e6d5084d47b3b122b68b66d42d6769105cb18a79be"

S = "${WORKDIR}/vrpn"

inherit cmake

EXTRA_OECMAKE = "-DBUILD_SHARED_LIBS=1 -DCMAKE_BUILD_TYPE=Release -DVRPN_BUILD_CLIENT_LIBRARY=ON -DVRPN_BUILD_SERVER_LIBRARY=OFF -DVRPN_BUILD_CLIENTS=OFF -DVRPN_BUILD_SERVERS=OFF"
OECMAKE_SOURCEPATH= "${S}"
OECMAKE_BUILDPATH= "${S}/build"

BBCLASSEXTEND = "nativesdk"

#add a symlink, because in old recipes robomap3 for poky dizzy, version.patch was wrong and declared the lib as 7.31
#so keep a symlink for compatibility
#should be removed when nobody uses 1.7.3 toolchain anylonger
do_install_append () {
	cd ${D}${libdir}/
	ln -s libvrpn.so.7.33 libvrpn.so.7.31
    ln -s libquat.so.7.33 libquat.so.7.31
}

