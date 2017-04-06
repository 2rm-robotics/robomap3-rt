FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

PR := "${PR}.1"

#COMPATIBLE_MACHINE = "(airbox|pacpus|uav|overo)"

SRC_URI_append = " file://0001-wsegl2-support.patch \
        file://002_pvrqwswsegl.c.patch\
	file://powervr.conf "

DEPENDS += "opengles-sgx-530"

# To use OpenVG with QWS we need to have the corresponding OpenVG
# screen driver plugin. Last time when I checked the sources, Qt didn't
# have the OpenVG screen driver plugin for SGX. I'm not aware of the
# state or existence of OpenVG screen driver plugin in Qt now.
# QT_GLFLAGS += -openvg

QT_GLFLAGS = "-opengl es2 -depths 16,24,32 -plugin-gfx-powervr"

QT_CONFIG_FLAGS += " \
 -exceptions \
"

do_overwrite_linux_conf() {
	# overwrite linux.conf
	cp -f ${WORKDIR}/powervr.conf ${WORKDIR}/linux.conf
	# set version 2 of wsegl by default (pvr2d.h and wegl.h)
	cp -f ${S}/src/3rdparty/powervr/wsegl2/* ${S}/src/3rdparty/powervr/
	# remove wsegl2 directory to avoid confusion
	rm -fr ${S}/src/3rdparty/powervr/wsegl2/
}

addtask do_overwrite_linux_conf after do_patch before do_configure
