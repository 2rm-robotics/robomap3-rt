
PR := "${PR}.1"

pkg_postinst_util-linux-blkid () {
	# workaround to fix package update
	if [ -f /sbin/blkid ]; then
		rm -f /sbin/blkid
	fi
	update-alternatives --install ${base_sbindir}/blkid blkid blkid.${PN} 100
}

pkg_prerm_util-linux-blkid () {
	# workaround to fix package update
	if [ -f /sbin/blkid ]; then
		rm -f /sbin/blkid
	fi
	update-alternatives --remove blkid blkid.${PN}
}
