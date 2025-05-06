FROM ubuntu:16.04

RUN apt-get update && \
    apt-get install -y sudo git subversion chrpath texinfo python diffstat bzip2 gawk wget cpio make gcc g++ nano

RUN useradd -u 1000 admin && \
    mkdir /home/admin && chown admin:admin /home/admin && \
    mkdir /workdir && chown -R admin:root /workdir && \
    echo "admin   ALL=(ALL)       NOPASSWD: ALL" >> /etc/sudoers

USER admin
WORKDIR /workdir

RUN git clone https://github.com/2rm-robotics/robomap3-rt.git robomap3-rt
RUN git clone -b krogoth https://gitlab.utc.fr/uav-hds/yoctoproject/poky poky-krogoth-15.0.3

# Add the meta-hds layer if it is found alongside this dockerfile
RUN mkdir /workdir/meta-hds
COPY meta-hds* /workdir/meta-hds

COPY build.sh .
