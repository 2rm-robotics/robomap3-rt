# RaspberryPi BSP default providers

PREFERRED_PROVIDER_virtual/kernel ?= "linux-raspberrypi"
PREFERRED_PROVIDER_virtual/xserver ?= "xserver-xorg"
#PREFERRED_PROVIDER_virtual/egl ?= "userland"
PREFERRED_PROVIDER_virtual/egl ?= "mesa"
#PREFERRED_PROVIDER_virtual/libgles2 ?= "userland"
PREFERRED_PROVIDER_virtual/libgles2 ?= "mesa"
#PREFERRED_PROVIDER_virtual/libgl ?= "mesa-gl"
PREFERRED_PROVIDER_virtual/libgl ?= "mesa"
#PREFERRED_PROVIDER_virtual/mesa ?= "mesa-gl"
PREFERRED_PROVIDER_virtual/mesa ?= "mesa"
PREFERRED_PROVIDER_jpeg ?= "jpeg"
