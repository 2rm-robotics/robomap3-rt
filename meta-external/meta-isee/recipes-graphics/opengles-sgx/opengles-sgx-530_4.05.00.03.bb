DESCRIPTION = "PowerVR SGX libraries"
SECTION = "libs"
HOMEPAGE = "http://software-dl.ti.com/dsps/dsps_public_sw/sdo_sb/targetcontent/gfxsdk"
LICENSE = "TI TSPA"
LIC_FILES_CHKSUM = "file://TSPA.txt;md5=c0d5d9c1e38b41677144c4e24d6ddee1"

RDEPENDS_${PN} = "devmem2 powervr-sgx-530 fbset"

PR = "r2"

SRC_URI = "http://meta-igep.googlecode.com/files/${PN}-${PV}.tar.gz \
	file://init \
	"

SRC_URI[md5sum] = "d5f53b1b4243d7da958137ff735b2eac"
SRC_URI[sha256sum] = "46ff5b2bfb9ccdb2ad9d2aa04d8334fda4ba313c1e5d2c5a72299c87d1637598"

inherit update-rc.d

INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME_${PN} = "pvr.sh"
INITSCRIPT_PARAMS_${PN} = "defaults 30"

do_compile() {
	:
}

do_install() {
	# Create init script and runlevel links
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/pvr.sh

	install -d ${D}${libdir}/
	cp -pR ${S}/gfx_rel_es5.x/* ${D}${libdir}/
	install -d ${D}${bindir}
	cp -p ${S}/gfx_rel_es5.x/pvrsrvinit ${D}${bindir}/ 

	install -d ${D}${includedir}
	cp -pR ${S}/GFX_Linux_SDK/OGLES/SDKPackage/Builds/OGLES/Include/* ${D}${includedir}/
	cp -pR ${S}/GFX_Linux_SDK/OGLES2/SDKPackage/Builds/OGLES2/Include/* ${D}${includedir}/	
	cp -pR ${S}/GFX_Linux_SDK/OVG/SDKPackage/Builds/OVG/Include/VG ${D}${includedir}/
}

RDEPENDS_${PN} = "libegl1-sgx-530 libgles1-sgx-530 libgles2-sgx-530 libopenvg1-sgx-530"

PACKAGES = " \
	${PN} \
	${PN}-dev \
	libegl1-sgx-530 \
	libgles1-sgx-530 \
	libgles2-sgx-530 \
	libopenvg1-sgx-530 \
"

FILES_${PN} = "${sysconfdir} \
	${bindir} \
	${libdir}/libglslcompiler.so* \
	${libdir}/libIMGegl.so* \
	${libdir}/libpvr2d.so* \
	${libdir}/libpvrPVR2D_BLITWSEGL.so* \
	${libdir}/libpvrPVR2D_FLIPWSEGL.so* \
	${libdir}/libpvrPVR2D_FRONTWSEGL.so* \
	${libdir}/libpvrPVR2D_LINUXFBWSEGL.so* \
	${libdir}/libpvrPVR2D_X11WSEGL.so* \
	${libdir}/libPVRScopeServices.so* \
	${libdir}/libsrv_um.so* \
	${libdir}/libusc.so* \
	${libdir}/libsrv_init.so* \
"

FILES_${PN}-dev = "${includedir}"

FILES_libegl1-sgx-530 = " \
	${libdir}/libEGL.so* \
	${libdir}/libEGL.so* \
"

FILES_libgles1-sgx-530 = " \
	${libdir}/libGLES_CM.so* \
	${libdir}/libGLESv1_CM.so* \
	${libdir}/libGLES_CM.so* \
	${libdir}/libGLESv1_CM.so* \
"

FILES_libgles2-sgx-530 = " \
	${libdir}/libGLESv2.so* \
	${libdir}/libGLESv2.so* \
"

FILES_libopenvg1-sgx-530 = " \
	${libdir}/libOpenVG.so* \
	${libdir}/libOpenVGU.so* \
"

INHIBIT_PACKAGE_STRIP = "1"
#HACK! These are binaries, so we can't guarantee that LDFLAGS match :(
INSANE_SKIP_${PN} = "ldflags dev-so"
INSANE_SKIP_libegl1-sgx-530 = "ldflags dev-so"
INSANE_SKIP_libgles1-sgx-530 = "ldflags dev-so"
INSANE_SKIP_libgles2-sgx-530 = "ldflags dev-so"
INSANE_SKIP_libopenvg1-sgx-530 = "ldflags dev-so"
