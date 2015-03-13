PR := "${PR}.1"

# look for files in the layer
FILESEXTRAPATHS_append := "${THISDIR}/${PN}:"

SRC_URI_append = " file://qgeomapping_cache.patch \
		"
#only build location module
QTM_MODULES_LIST = "location"
#fix plugin dir
qtm_plugins	= "/usr/lib/${qtm_dir}/plugins/"
