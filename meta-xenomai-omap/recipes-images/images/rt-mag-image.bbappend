
RTDM_DRIVERS = " \
    uart-omap \
    i2c-omap \
    capture-radio-omap \
 "
 
BENCHMARK_TOOLS = " \
    stress \
    hackbench \
 "

IMAGE_INSTALL_append = " xenomai xenomai-dev xenomai-dbg \
	${RTDM_DRIVERS} \
	${BENCHMARK_TOOLS} \
 "
 
