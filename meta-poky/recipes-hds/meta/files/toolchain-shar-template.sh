#!/bin/bash

INST_ARCH=$(uname -m | sed -e "s/i[3-6]86/ix86/" -e "s/x86[-_]64/x86_64/")
SDK_ARCH=$(echo @SDK_ARCH@ | sed -e "s/i[3-6]86/ix86/" -e "s/x86[-_]64/x86_64/")
MACHINE=$(echo @MACHINE@)
DEFAULTTUNE=$(echo @DEFAULTTUNE@)
ROS_TOOLCHAIN=$(echo @ROS_TOOLCHAIN@)

if [ "$INST_ARCH" != "$SDK_ARCH" ]; then
	# Allow for installation of ix86 SDK on x86_64 host
	if [ "$INST_ARCH" != x86_64 -o "$SDK_ARCH" != ix86 ]; then
		echo "Error: Installation machine not supported!"
		exit 1
	fi
fi

DEFAULT_INSTALL_DIR="@SDKPATH@"
SUDO_EXEC=""
target_sdk_dir=""
answer=""
relocate=1
savescripts=0
verbose=0
while getopts ":yd:DRS" OPT; do
	case $OPT in
	y)
		answer="Y"
		[ "$target_sdk_dir" = "" ] && target_sdk_dir=$DEFAULT_INSTALL_DIR
		;;
	d)
		target_sdk_dir=$OPTARG
		;;
	D)
		verbose=1
		;;
	R)
		relocate=0
		savescripts=1
		;;
	S)
		savescripts=1
		;;
	*)
		echo "Usage: $(basename $0) [-y] [-d <dir>]"
		echo "  -y         Automatic yes to all prompts"
		echo "  -d <dir>   Install the SDK to <dir>"
		echo "======== Advanced DEBUGGING ONLY OPTIONS ========"
		echo "  -S         Save relocation scripts"
		echo "  -R         Do not relocate executables"
		echo "  -D         use set -x to see what is going on"
		exit 1
		;;
	esac
done

if [ $verbose = 1 ] ; then
	set -x
fi

if [ "$target_sdk_dir" = "" ]; then
	read -e -p "Enter target directory for SDK (default: $DEFAULT_INSTALL_DIR): " target_sdk_dir
	[ "$target_sdk_dir" = "" ] && target_sdk_dir=$DEFAULT_INSTALL_DIR
fi

eval target_sdk_dir=$(echo "$target_sdk_dir"|sed 's/ /\\ /g')
if [ -d "$target_sdk_dir" ]; then
	target_sdk_dir=$(cd "$target_sdk_dir"; pwd)
else
	target_sdk_dir=$(readlink -m "$target_sdk_dir")
fi

if [ -n "$(echo $target_sdk_dir|grep ' ')" ]; then
	echo "The target directory path ($target_sdk_dir) contains spaces. Abort!"
	exit 1
fi

if [ -e "$target_sdk_dir/environment-setup-@REAL_MULTIMACH_TARGET_SYS@" ]; then
	echo "The directory \"$target_sdk_dir\" already contains a SDK for this architecture."
	printf "If you continue, existing files will be erased! Proceed[y/N]?"

	default_answer="n"
else
	printf "You are about to install the SDK to \"$target_sdk_dir\". Proceed[Y/n]?"

	default_answer="y"
fi

if [ "$answer" = "" ]; then
	read answer
	[ "$answer" = "" ] && answer="$default_answer"
else
	echo $answer
fi

if [ "$answer" != "Y" -a "$answer" != "y" ]; then
	echo "Installation aborted!"
	exit 1
fi

# Try to create the directory (this will not succeed if user doesn't have rights)
rm -rf $target_sdk_dir
mkdir -p $target_sdk_dir >/dev/null 2>&1

# if don't have the right to access dir, gain by sudo 
if [ ! -x $target_sdk_dir -o ! -w $target_sdk_dir -o ! -r $target_sdk_dir ]; then 
	SUDO_EXEC=$(which "sudo")
	if [ -z $SUDO_EXEC ]; then
		echo "No command 'sudo' found, please install sudo first. Abort!"
		exit 1
	fi

	# test sudo could gain root right
	$SUDO_EXEC pwd >/dev/null 2>&1
	[ $? -ne 0 ] && echo "Sorry, you are not allowed to execute as root." && exit 1

	# now that we have sudo rights, create the directory
	rm -rf $target_sdk_dir
	$SUDO_EXEC mkdir -p $target_sdk_dir >/dev/null 2>&1
fi

payload_offset=$(($(grep -na -m1 "^MARKER:$" $0|cut -d':' -f1) + 1))

printf "Extracting SDK..."
tail -n +$payload_offset $0| $SUDO_EXEC tar xj -C $target_sdk_dir
echo "done"

printf "Setting it up..."
# fix environment paths
for env_setup_script in `ls $target_sdk_dir/environment-setup-*`; do
	$SUDO_EXEC sed -e "s:$DEFAULT_INSTALL_DIR:$target_sdk_dir:g" -i $env_setup_script
done

# fix dynamic loader paths in all ELF SDK binaries
native_sysroot=$($SUDO_EXEC cat $env_setup_script |grep 'OECORE_NATIVE_SYSROOT='|cut -d'=' -f2|tr -d '"')
dl_path=$($SUDO_EXEC find $native_sysroot/lib -name "ld-linux*")
if [ "$dl_path" = "" ] ; then
	echo "SDK could not be set up. Relocate script unable to find ld-linux.so. Abort!"
	exit 1
fi
executable_files=$($SUDO_EXEC find $native_sysroot -type f \
	\( -perm -0100 -o -perm -0010 -o -perm -0001 \) -printf "'%h/%f' ")

tdir=`mktemp -d`
if [ x$tdir = x ] ; then
   echo "SDK relocate failed, could not create a temporary directory"
   exit 1
fi
echo "#!/bin/bash" > $tdir/relocate_sdk.sh
echo exec ${env_setup_script%/*}/relocate_sdk.py $target_sdk_dir $dl_path $executable_files >> $tdir/relocate_sdk.sh
$SUDO_EXEC mv $tdir/relocate_sdk.sh ${env_setup_script%/*}/relocate_sdk.sh
$SUDO_EXEC chmod 755 ${env_setup_script%/*}/relocate_sdk.sh
rm -rf $tdir
if [ $relocate = 1 ] ; then
	$SUDO_EXEC ${env_setup_script%/*}/relocate_sdk.sh
	if [ $? -ne 0 ]; then
		echo "SDK could not be set up. Relocate script failed. Abort!"
		exit 1
	fi
fi

# replace @SDKPATH@ with the new prefix in all text files: configs/scripts/etc
for replace in "$target_sdk_dir -maxdepth 1" "$native_sysroot"; do
	$SUDO_EXEC find $replace -type f -exec file '{}' \; | \
		grep ":.*\(ASCII\|script\|source\).*text" | \
		awk -F':' '{printf "\"%s\"\n", $1}' | \
		$SUDO_EXEC xargs -n32 sed -i -e "s:$DEFAULT_INSTALL_DIR:$target_sdk_dir:g"
done

# change all symlinks pointing to @SDKPATH@
for l in $($SUDO_EXEC find $native_sysroot -type l); do
	#on ne traite pas certains liens symboliques vers "." (xenomai)
	if ! readlink $l| grep -w "\.">/dev/null; then
		$SUDO_EXEC  ln -sfn $(readlink $l|$SUDO_EXEC  sed -e "s:$DEFAULT_INSTALL_DIR:$target_sdk_dir:") $l
	fi
done

#fixes path in file toolchain.cmake
$SUDO_EXEC sed -e "s:$DEFAULT_INSTALL_DIR:$target_sdk_dir:g" -i $target_sdk_dir/toolchain.cmake

#ROS CROSS-COMPILATION STUFF
if [ "$ROS_TOOLCHAIN" = "true" ]; then
    SDKTARGETSYSROOT=$(cat $env_setup_script | grep "export SDKTARGETSYSROOT=" | sed 's/.*=\(.*\)/\1/')

    # fix path in all ros .cmake files
    for file in $($SUDO_EXEC find $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@ -name "*.cmake" ); do
	    $SUDO_EXEC sed -i "s:.*/usr/lib.*:foreach(path $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/lib;$SDKTARGETSYSROOT/usr/lib):g" $file
    done
    # add the .catkin file with correct path for cross-compilation
    echo "$SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/share/;" | $SUDO_EXEC tee $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/.catkin > /dev/null
    # fix python path in header files
    $SUDO_EXEC sed -i "s:#!/usr/bin/env.*:#!/usr/bin/env $native_sysroot/usr/bin/python:g" $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/share/catkin/cmake/templates/_setup_util.py.in
    # fix python native path in ros profile
    $SUDO_EXEC sed -i "s:@STAGING_BINDIR_NATIVE@/python-native/python:$native_sysroot/usr/bin/python:g" $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/etc/catkin/profile.d/10.ros.sh $native_sysroot/opt/ros/@ROSDISTRO@/etc/catkin/profile.d/10.ros.sh
    # fix missing rospack depend in roslibConfig.cmake
    $SUDO_EXEC sed -i "s:set(depends \"\"):set(depends \"rospack\"):g" $SDKTARGETSYSROOT/opt/ros/@ROSDISTRO@/share/roslib/cmake/roslibConfig.cmake
fi
#end ROS STUFF

# find out all perl scripts in $native_sysroot and modify them replacing the
# host perl with SDK perl.
for perl_script in $($SUDO_EXEC find $native_sysroot -type f -exec grep -l "^#!.*perl" '{}' \;); do
	$SUDO_EXEC sed -i -e "s:^#! */usr/bin/perl.*:#! /usr/bin/env perl:g" -e \
		"s: /usr/bin/perl: /usr/bin/env perl:g" $perl_script
done

echo

#libGL fix for Fl-AIR simulator
if [ "$MACHINE" = "genericx86-64" ] || [ "$MACHINE" = "genericx86" ] ; then
	SDKTARGETSYSROOT=$(cat $env_setup_script | grep "export SDKTARGETSYSROOT=" | sed 's/.*=\(.*\)/\1/')
    mkdir $SDKTARGETSYSROOT/usr/lib/GL
    mv $SDKTARGETSYSROOT/usr/lib/libGL* $SDKTARGETSYSROOT/usr/lib/GL
    mv $SDKTARGETSYSROOT/usr/lib/libdrm* $SDKTARGETSYSROOT/usr/lib/GL
fi

#environement variable settings
function comment-and-add {
  FILE=$1
  ENVVAR=$2
  VALUE=$3
  unset LINE_FOUND

  while read -r LINE; do
    #if the correct line is already here...
    if [ Z"$LINE" = Z"export $ENVVAR=$VALUE" ]; then
      #... mark the line as found
      LINE_FOUND=1
    else
      # else, if not already commented...
      echo $LINE | grep -q "^[[:space:]]*#"
      if [ $? -ne 0 ]; then
        #...comment the line.
        sed -i "s:$LINE:#$LINE:g" $FILE
      fi
    fi
  done < <(cat $FILE | grep "[[:space:]]*export[[:space:]]*$ENVVAR=")

  #if LINE_FOUND is unset...
  if [ -z "${LINE_FOUND+set}" ]; then
    #... add it now
    echo "export $ENVVAR=$VALUE" >> $FILE
  fi
}

function add-value {
  FILE=$1
  ENVVAR=$2
  VALUE=$3
  unset VALUE_FOUND
  unset LINE_FOUND

  while read -r LINE; do
    #if the correct line is already here...
    LINE_FOUND=$LINE
    TOOLCHAINS=($(echo $LINE | sed -e 's/export .*="\(.*\)"/\1/'))
    for ARCH in ${TOOLCHAINS[@]}; do
        if [ $ARCH = $VALUE ]; then
            VALUE_FOUND=1
        fi
    done
  done < <(cat $FILE | grep "^export[[:space:]]*$ENVVAR=")


  if [ -z "${LINE_FOUND+set}" ]; then
    echo "export $ENVVAR=\"$VALUE\"" >> $FILE
  elif [ -z "${VALUE_FOUND+set}" ]; then
    TOOLCHAINS=("${TOOLCHAINS[@]}" "$VALUE")
    NEW_LINE="export $ENVVAR=\"${TOOLCHAINS[@]}\""
    sed -i "s:$LINE_FOUND:$NEW_LINE:g" $FILE
  fi
}

add-value ~/.bashrc OECORE_CMAKE_TOOLCHAINS ${DEFAULTTUNE//-/_}

var=OECORE_CMAKE_${DEFAULTTUNE^^}_TOOLCHAIN
var=${var//-/_} # replace - by _
comment-and-add ~/.bashrc $var "$target_sdk_dir/toolchain.cmake"
echo "Added $var in ~/.bashrc"

var=OECORE_${DEFAULTTUNE^^}_NATIVE_SYSROOT
var=${var//-/_} # replace - by _
VALUE=$(cat $env_setup_script | grep "export OECORE_NATIVE_SYSROOT=" | sed 's/.*=\(.*\)/\1/')
comment-and-add ~/.bashrc $var $VALUE
echo "Added $var in ~/.bashrc"

if [ "$MACHINE" = "genericx86-64" ] || [ "$MACHINE" = "genericx86" ] ; then
	VALUE=$(cat $env_setup_script | grep "export SDKTARGETSYSROOT=" | sed 's/.*=\(.*\)/\1/')
	comment-and-add ~/.bashrc OECORE_HOST_SYSROOT $VALUE
	echo "Added OECORE_HOST_SYSROOT in ~/.bashrc"

fi

echo done

@SDK_POST_INSTALL_COMMAND@

# delete the relocating script, so that user is forced to re-run the installer
# if he/she wants another location for the sdk
if [ $savescripts = 0 ] ; then
	$SUDO_EXEC rm ${env_setup_script%/*}/relocate_sdk.py ${env_setup_script%/*}/relocate_sdk.sh
fi

echo "SDK has been successfully set up and is ready to be used."

exit 0

MARKER:
