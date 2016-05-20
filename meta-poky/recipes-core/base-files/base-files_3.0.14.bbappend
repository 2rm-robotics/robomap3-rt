require conf/distro/release_robomap3.inc

PR := "${PR}.${ROBOMAP3_VERSION}"

BASEFILESISSUEINSTALL = "do_basefilesissue"
FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

do_basefilesissue () {
	if [ -n "${MACHINE}" -a "${hostname}" = "openembedded" ]; then
		echo ${MACHINE} > ${D}${sysconfdir}/hostname
	else
		echo ${hostname} > ${D}${sysconfdir}/hostname
	fi

	install -m 644 ${WORKDIR}/issue*  ${D}${sysconfdir}

        echo -n "${DISTRO_NAME}, " >> ${D}${sysconfdir}/issue
	echo -n "${DISTRO_NAME}, " >> ${D}${sysconfdir}/issue.net
	echo -n "${DISTRO_VERSION}, " >> ${D}${sysconfdir}/issue
	echo -n "${DISTRO_VERSION}, " >> ${D}${sysconfdir}/issue.net
        echo -n "RobOMAP3 ${ROBOMAP3_VERSION} " >> ${D}${sysconfdir}/issue
	echo -n "RobOMAP3 ${ROBOMAP3_VERSION} " >> ${D}${sysconfdir}/issue.net
	echo "\n \l" >> ${D}${sysconfdir}/issue
	echo >> ${D}${sysconfdir}/issue
	echo "%h"    >> ${D}${sysconfdir}/issue.net
	echo >> ${D}${sysconfdir}/issue.net
}
