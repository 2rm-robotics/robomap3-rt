# toolchain.cmake file creation

#taken from cmake.bbclass

# C/C++ Compiler (without cpu arch/tune arguments)
OECMAKE_C_COMPILER ?= "`echo ${CC} | sed 's/^\([^ ]*\).*/\1/'`"
OECMAKE_CXX_COMPILER ?= "`echo ${CXX} | sed 's/^\([^ ]*\).*/\1/'`"

# Compiler flags
OECMAKE_C_FLAGS ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} ${BUILD_OPTIMIZATION}"
OECMAKE_CXX_FLAGS ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} ${BUILD_OPTIMIZATION}"
OECMAKE_C_FLAGS_RELEASE ?= "${SELECTED_OPTIMIZATION} ${BUILD_OPTIMIZATION} -DNDEBUG"
OECMAKE_CXX_FLAGS_RELEASE ?= "${SELECTED_OPTIMIZATION} ${BUILD_OPTIMIZATION} -DNDEBUG"
OECMAKE_C_LINK_FLAGS ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} ${CPPFLAGS} ${LDFLAGS}"
OECMAKE_CXX_LINK_FLAGS ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} -Wl,-rpath=${SDKTARGETSYSROOT}/usr/lib:${SDKTARGETSYSROOT}/lib ${BUILD_OPTIMIZATION} ${LDFLAGS}"
OECMAKE_CXX_LINK_FLAGS_genericx86-64 ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} -Wl,--dynamic-linker=${SDKPATHNATIVE}/lib/ld-linux-x86-64.so.2 -Wl,-rpath=${SDKTARGETSYSROOT}/usr/lib:${SDKTARGETSYSROOT}/lib ${BUILD_OPTIMIZATION} ${LDFLAGS}"
OECMAKE_CXX_LINK_FLAGS_genericx86 ?= "${HOST_CC_ARCH} --sysroot=${SDKTARGETSYSROOT} -Wl,--dynamic-linker=${SDKPATHNATIVE}/lib/ld-linux.so.2 -Wl,-rpath=${SDKTARGETSYSROOT}/usr/lib:${SDKTARGETSYSROOT}/lib ${BUILD_OPTIMIZATION} ${LDFLAGS}"

toolchain_create_sdk_cmake_script () {

cat > ${SDK_OUTPUT}/${SDKPATH}/toolchain.cmake <<EOF
# CMake system name must be something like "Linux".
# This is important for cross-compiling.
set( CMAKE_SYSTEM_NAME `echo ${TARGET_OS} | sed -e 's/^./\u&/' -e 's/^\(Linux\).*/\1/'` )
set( CMAKE_SYSTEM_PROCESSOR ${TARGET_ARCH} )
set( CMAKE_SYSROOT ${SDKTARGETSYSROOT})

set( CMAKE_C_COMPILER ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}gcc )
set( CMAKE_CXX_COMPILER ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}g++) 
set( CMAKE_ASM_COMPILER ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}gcc )
set( CMAKE_LINKER       ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}ld )
set( CMAKE_AR           ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}ar )
set( QT_QMAKE_EXECUTABLE ${SDKPATHNATIVE}${bindir_nativesdk}/qmake2 )

set(CMAKE_C_ARCHIVE_CREATE "\${CMAKE_AR} cr <TARGET> <LINK_FLAGS> <OBJECTS>")
set(CMAKE_CXX_ARCHIVE_CREATE \${CMAKE_C_ARCHIVE_CREATE})
 
set(CMAKE_SYSTEM_PREFIX_PATH ${SDKTARGETSYSROOT})

set( CMAKE_C_FLAGS "${OECMAKE_C_FLAGS}" CACHE STRING "CFLAGS" )
set( CMAKE_CXX_FLAGS "${OECMAKE_CXX_FLAGS}" CACHE STRING "CXXFLAGS" )
set( CMAKE_ASM_FLAGS "${OECMAKE_C_FLAGS}" CACHE STRING "ASM FLAGS" )
set( CMAKE_C_FLAGS_RELEASE "${OECMAKE_C_FLAGS_RELEASE}" CACHE STRING "CFLAGS for release" )
set( CMAKE_CXX_FLAGS_RELEASE "${OECMAKE_CXX_FLAGS_RELEASE}" CACHE STRING "CXXFLAGS for release" )
set( CMAKE_ASM_FLAGS_RELEASE "${OECMAKE_C_FLAGS_RELEASE}" CACHE STRING "ASM FLAGS for release" )
set( CMAKE_C_LINK_FLAGS "${OECMAKE_C_LINK_FLAGS}" CACHE STRING "LDFLAGS" )
set( CMAKE_CXX_LINK_FLAGS "${OECMAKE_CXX_LINK_FLAGS}" CACHE STRING "LDFLAGS" )

# only search in the paths provided so cmake doesnt pick
# up libraries and tools from the native build machine
set( CMAKE_FIND_ROOT_PATH ${SDKTARGETSYSROOT})
set( CMAKE_FIND_ROOT_PATH_MODE_PACKAGE ONLY )
set( CMAKE_FIND_ROOT_PATH_MODE_PROGRAM ONLY )
set( CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY )
set( CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY )

# Use qt.conf settings
set( ENV{QT_CONF_PATH} ${SDKPATHNATIVE}${bindir_nativesdk}/qt.conf )

# Use native cmake modules
set( CMAKE_MODULE_PATH ${SDKTARGETSYSROOT}/usr/share/cmake-2.8/Modules/ )

# add for non /usr/lib libdir, e.g. /usr/lib64
#set( CMAKE_LIBRARY_PATH ${libdir} ${base_libdir})

EOF
}
