#!/bin/bash

# Usage : upload.sh ipAddress

### This script uploads the ROS cross-compiled stuff to a raspberry pi.
USER="root"
TARGET_DEFAULT_ROS_DIRECTORY="~/ros_packages"

if [ $# -ne 1 ]; then
	echo "Usage : upload.sh ipAddress"
	IP_ADDRESSE=0
else
	IP_ADDRESSE=$1
fi


function upload() {
	echo "Uploading files to $IP_ADDRESSE"
	scp -r install/lib root@$IP_ADDRESSE:$TARGET_DEFAULT_ROS_DIRECTORY/lib
	scp -r install/share root@$IP_ADDRESSE:$TARGET_DEFAULT_ROS_DIRECTORY/share
}

# Mandatory in order to find the packages on the target system
function replace_paths() {
	# Replace path in pkgconfig files
	for file in $(find install/lib/pkgconfig -name "*.pc"); do
		sed -ri 's/home.*.install/home\/root\/ros_packages/g' $file
	done
	# Replace path in cmake files
	for file in $(find install/share -name "*.cmake"); do
		sed -ri 's/home.*.install/home\/root\/ros_packages/g' $file
	done
}

function process() {
	replace_paths
	upload
}

if [ "$IP_ADDRESSE" != "0" ]; then
	process
fi
