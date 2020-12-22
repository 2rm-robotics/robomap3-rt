from ubuntu:16.04

RUN apt-get update
RUN apt-get install -y sudo git subversion chrpath texinfo python diffstat bzip2 gawk wget cpio make gcc g++ nano

#RUN echo 'root:Letmein' | chpasswd
RUN useradd -u 1000 admin
#RUN echo "admin:admin" | chpasswd
RUN echo "admin   ALL=(ALL)       NOPASSWD: ALL" >> /etc/sudoers

USER admin
