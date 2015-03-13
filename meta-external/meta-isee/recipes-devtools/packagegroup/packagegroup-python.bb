DESCRIPTION = "Tasks for python applications"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58 \
                    file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"
inherit allarch packagegroup

PYTHON_BASE = "\
	python-audio \
	python-bsddb \
	python-codecs \
	python-compile \
	python-compiler \
	python-compression \
	python-core \
	python-crypt \
	python-ctypes \
	python-curses \
	python-datetime \
	python-db \
	python-debugger \
	python-difflib \
	python-distutils \
	python-doctest \
	python-elementtree  \
	python-email \
	python-fcntl \
	python-gdbm \
	python-hotshot \
	python-html \
	python-idle \
	python-image \
	python-io \
	python-json \
	python-lang \
	python-logging \
	python-mailbox \
	python-math \
	python-mime \
	python-mmap \
	python-multiprocessing \
	python-netclient \
	python-netserver \
	python-numbers \
	python-pickle \
	python-pkgutil \
	python-pprint \
	python-profile \
	python-pydoc \
	python-re \
	python-readline \
	python-resource \
	python-robotparser \
	python-shell \
	python-smtpd \
	python-sqlite3 \
	python-stringold \
	python-subprocess \
	python-syslog \
	python-terminal \
	python-tests \
	python-textutils \
	python-threading \
	python-tkinter \
	python-unittest \
	python-unixadmin \
	python-xml \
	python-xmlrpc \
	python-zlib \
	python-modules \
"

#python-dbg
#python-dev
#python-sqlite3-tests
#python-distutils-staticdev

RDEPENDS_${PN} = "\
	${PYTHON_BASE} \
	python-pyserial \
"
