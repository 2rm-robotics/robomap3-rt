#built it with: sudo docker build -t robomap3_poky_docker .
from ubuntu:16.04

RUN apt-get update
RUN apt-get install -y sudo git subversion chrpath texinfo python diffstat bzip2 gawk wget cpio make gcc g++ nano

RUN useradd -u 1000 admin
RUN mkdir /home/admin && chown admin:admin /home/admin
RUN echo "admin   ALL=(ALL)       NOPASSWD: ALL" >> /etc/sudoers

USER admin
