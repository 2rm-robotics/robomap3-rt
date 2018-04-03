#problem with gcc 4.9
#in tmp/work/x86_64-nativesdk-pokysdk-linux/nativesdk-libgcc/4.9.3-r0/image/opt/robomap3/2.1.3/core2-64/sysroots/x86_64-pokysdk-linux/usr/lib/
#x86_64-poky-linux -> x86_64-pokysdk-linux
#when installing it, x86_64-poky-linux already exists

#workaround remove the task creating the symlink

deltask extra_symlinks

