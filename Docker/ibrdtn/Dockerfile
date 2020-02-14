# TODO: autogen & configure & make & make install the tools dir
# add the missing libs to the /usr/lib dir
FROM raspbian/stretch:latest

WORKDIR /usr/src

RUN sudo apt-get update && sudo apt-get upgrade -y
RUN sudo apt-get install git -y
RUN git clone https://github.com/ibrdtn/ibrdtn.git ibrdtn-repo
RUN sudo apt-get install devscripts build-essential libtool cdbs pkg-config debhelper autotools-dev \
 libnl-3-dev libnl-genl-3-dev libnl-route-3-dev libnl-nf-3-dev libnl-cli-3-dev libssl-dev libssl-dev \
 zlib1g-dev libsqlite3-dev libcurl4-openssl-dev libdaemon-dev libvmime-dev libarchive-dev -y

ADD linux-compile.sh .

RUN cp linux-compile.sh ibrdtn-repo/scripts/
RUN cd ibrdtn-repo && bash scripts/linux-compile.sh

EXPOSE 4550

