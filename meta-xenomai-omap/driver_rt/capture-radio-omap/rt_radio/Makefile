###### CONFIGURATION ######


### Note: to override the search path for the xeno-config script, use "make XENO=..."


### List of modules to be build
MODULES = capture_omap

### Note: to set the kernel source path, use "make KSRC=..."
KSRC ?= $(OMAP3530_KERNEL)


###### KERNEL MODULE BUILD (no change required normally) ######
ifneq ($(MODULES),)


OBJS     := ${patsubst %, %.o, $(MODULES)}
CLEANMOD := ${patsubst %, .%*, $(MODULES)}
PWD      := $(shell if [ "$$PWD" != "" ]; then echo $$PWD; else pwd; fi)


obj-m        := $(OBJS)
EXTRA_CFLAGS := -I$(KSRC)/include/xenomai $(ADD_CFLAGS)

all::
	$(MAKE) -C $(KSRC) SUBDIRS=$(PWD) modules

install: all
	cp rtradio.h $(IGEP_ROOT)/uav_dev/include/rtdm

## Target for capturing 2.4 module CFLAGS
modules:
	@echo "$(CFLAGS)"

clean::
	$(RM) $(CLEANMOD) *.o *.ko *.mod.c Module*.symvers Module.markers modules.order
	$(RM) -R .tmp*

endif
