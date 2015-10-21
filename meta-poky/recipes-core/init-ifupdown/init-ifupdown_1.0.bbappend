FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}-${PV}:"

PR := "${PR}.7"

# On igep00x0 starting at 40 does not up the WLAN
INITSCRIPT_PARAMS = "start 80 S . stop 40 0 6 1 ."

SRC_URI += " \
	file://if-pre-up.d-wlan \
	file://if-up.d-wlan \
"

SRC_URI_append_ardrone2 = "\ 
	file://atheros \
	file://loadAR6000.sh \
"

do_install_append (){
	install -m 0755 ${WORKDIR}/if-pre-up.d-wlan ${D}${sysconfdir}/network/if-pre-up.d/wireless
	install -m 0755 ${WORKDIR}/if-up.d-wlan ${D}${sysconfdir}/network/if-up.d/wlan

	if [ "${MACHINE}" = "ardrone2" ];then
		mkdir -p ${D}/usr/sbin/
		install -m 0755 ${WORKDIR}/atheros ${D}${sysconfdir}/network/if-pre-up.d/
		install -m 0755 ${WORKDIR}/loadAR6000.sh ${D}/usr/sbin/
	fi
}
