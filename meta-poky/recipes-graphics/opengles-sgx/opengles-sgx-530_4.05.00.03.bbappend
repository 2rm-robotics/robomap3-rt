PR := "${PR}.1"

do_install_append() {
	#remove unused files
	rm \
		${D}${libdir}/pvrsrvinit \
		${D}${libdir}/libffgen.a \
		${D}${libdir}/glsltest1_fragshaderA.txt \
		${D}${libdir}/install.sh \
		${D}${libdir}/pds_mte_state_copy.h \
		${D}${libdir}/ovg_unit_test \
		${D}${libdir}/sgx_render_flip_test \
		${D}${libdir}/libusp.a \
		${D}${libdir}/sgx_flip_test \
		${D}${libdir}/eglinfo \
		${D}${libdir}/xgles2test1 \
		${D}${libdir}/xovg_unit_test \
		${D}${libdir}/gles1test1 \
		${D}${libdir}/libuseasm.a \
		${D}${libdir}/install_dri.sh \
		${D}${libdir}/pdsasm \
		${D}${libdir}/vhd2inc \
		${D}${libdir}/pds_aux_vtx_sizeof.h \
		${D}${libdir}/39d3a0a9246e453261ca741af1185f61.md5 \
		${D}${libdir}/uselink \
		${D}${libdir}/sgx_blit_test \
		${D}${libdir}/pvr2d_test \
		${D}${libdir}/glsltest1_vertshader.txt \
		${D}${libdir}/rc_dri.pvr \
		${D}${libdir}/pds_aux_vtx.h \
		${D}${libdir}/libsrv_um_dri.so \
		${D}${libdir}/services_test \
		${D}${libdir}/sgx_clipblit_test \
		${D}${libdir}/rc.pvr \
		${D}${libdir}/pixelevent_sizeof.h \
		${D}${libdir}/gles2test1 \
		${D}${libdir}/glsltest1_fragshaderB.txt \
		${D}${libdir}/pds_mte_state_copy_sizeof.h \
		${D}${libdir}/sgx_init_test \
		${D}${libdir}/libpvrPVR2D_DRIWSEGL.so \
		${D}${libdir}/xgles1test1 \
		${D}${libdir}/pixelevent.h \
		${D}${libdir}/xmultiegltest \
		${D}${libdir}/eglinfo_x \
		${D}${libdir}/pvr_drv.so \
		${D}${libdir}/useasm \
}


