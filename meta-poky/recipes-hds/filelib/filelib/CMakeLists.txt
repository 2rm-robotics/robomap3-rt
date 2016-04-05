PROJECT(FileLib)
cmake_minimum_required(VERSION 2.8)

ADD_LIBRARY(${PROJECT_NAME} SHARED
	err.c 
	io_hdfile.c
)

set_target_properties(${PROJECT_NAME} PROPERTIES VERSION 1.0
                                          SOVERSION 1)

INSTALL(TARGETS ${PROJECT_NAME}
	LIBRARY DESTINATION /usr/lib
)

INSTALL(FILES io_hdfile.h DESTINATION /usr/include/)
