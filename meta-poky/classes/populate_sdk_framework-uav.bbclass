# The majority of populate_sdk is located in populate_sdk_base
# This chunk simply facilitates compatibility with SDK only recipes.

inherit populate_sdk_base_framework-uav

addtask populate_sdk after do_install before do_build

