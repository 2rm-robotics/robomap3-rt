PR := "${PR}.1"

EXTRA_OECONF_class-nativesdk = "--without-python --without-legacy --with-catalog --without-docbook --with-c14n --without-lzma"

inherit autotools pkgconfig binconfig pythonnative ptest
