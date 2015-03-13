# creation du fichier toolchain.cmake
toolchain_create_sdk_cmake_script () {
	
	script=${SDK_OUTPUT}/${SDKPATH}/toolchain.cmake
	rm -f $script
	touch $script

        echo 'cmake_minimum_required(VERSION 2.8)' >> $script
        echo '' >> $script
        echo '# CMake system name must be something like "Linux".' >> $script
        echo '# This is important for cross-compiling.' >> $script
        echo 'set( CMAKE_SYSTEM_NAME Linux )' >> $script
        echo 'set( CMAKE_SYSTEM_PROCESSOR arm )' >> $script
        echo '' >> $script
        echo 'SET(CMAKE_C_COMPILER   ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}gcc)' >> $script
        echo 'SET(CMAKE_CXX_COMPILER ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}g++)' >> $script
        echo 'SET(CMAKE_LINKER       ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}ld)' >> $script
        echo 'SET(CMAKE_AR           ${SDKPATHNATIVE}${bindir_nativesdk}/${TARGET_SYS}/${TARGET_PREFIX}ar)' >> $script
        echo 'SET(QT_QMAKE_EXECUTABLE ${SDKPATHNATIVE}${bindir_nativesdk}/qmake2)' >> $script
        echo '' >> $script
        echo 'SET(CMAKE_C_ARCHIVE_CREATE "${CMAKE_AR} cr <TARGET> <LINK_FLAGS> <OBJECTS>")' >> $script
        echo 'SET(CMAKE_CXX_ARCHIVE_CREATE ${CMAKE_C_ARCHIVE_CREATE})' >> $script
        echo '' >> $script
        echo 'SET(CMAKE_SYSTEM_PREFIX_PATH ${SDKTARGETSYSROOT})' >> $script
        echo '' >> $script
        echo 'set( CMAKE_C_FLAGS " -march=armv7-a -fno-tree-vectorize     -mthumb-interwork -mfloat-abi=softfp -mfpu=neon -mtune=cortex-a8  --sysroot=${SDKTARGETSYSROOT} " CACHE STRING "CFLAGS" )' >> $script
        echo 'set( CMAKE_CXX_FLAGS " -march=armv7-a -fno-tree-vectorize     -mthumb-interwork -mfloat-abi=softfp -mfpu=neon -mtune=cortex-a8  --sysroot=${SDKTARGETSYSROOT} -O2 -pipe -feliminate-unused-debug-types -fpermissive -fvisibility-inlines-hidden " CACHE STRING "CXXFLAGS" )' >> $script
        echo 'set( CMAKE_C_FLAGS_RELEASE "-O3 -pipe -feliminate-unused-debug-types  -DNDEBUG" CACHE STRING "CFLAGS for release" )' >> $script
        echo 'set( CMAKE_CXX_FLAGS_RELEASE "-O3 -pipe -feliminate-unused-debug-types  -fpermissive -fvisibility-inlines-hidden -DNDEBUG" CACHE STRING "CXXFLAGS for release" )' >> $script
        echo '' >> $script
        echo '# only search in the paths provided so cmake doesnt pick' >> $script
        echo '# up libraries and tools from the native build machine' >> $script
        echo 'set( CMAKE_FIND_ROOT_PATH ${SDKTARGETSYSROOT})' >> $script
        echo 'set( CMAKE_FIND_ROOT_PATH_MODE_PROGRAM ONLY )' >> $script
        echo 'set( CMAKE_FIND_ROOT_PATH_MODE_LIBRARY ONLY )' >> $script
        echo 'set( CMAKE_FIND_ROOT_PATH_MODE_INCLUDE ONLY )' >> $script
        echo '' >> $script
        echo '# Use native cmake modules' >> $script
        echo '#set( CMAKE_MODULE_PATH ${SDKTARGETSYSROOT}/usr/share/cmake-2.8/Modules/ )' >> $script
}
