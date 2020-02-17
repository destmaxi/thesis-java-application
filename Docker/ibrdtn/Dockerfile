FROM debian:buster

MAINTAINER Maxime de Streel <maxime.destreel@student.uclouvain.be>
# TODO: autogen & configure & make & make install the tools dir
# add the missing libs to the /usr/lib dir

WORKDIR /usr/dtn_app

RUN apt-get update && apt-get install -y --no-install-recommends \
# packets for ibrdtn
  build-essential libssl-dev libz-dev libsqlite3-dev libcurl4-gnutls-dev \
  libdaemon-dev automake autoconf pkg-config libtool libcppunit-dev \
  libnl-3-dev libnl-cli-3-dev libnl-genl-3-dev libnl-nf-3-dev libnl-route-3-dev \
  libarchive-dev libarchive-dev \
  # others
  ca-certificates git

RUN git clone https://github.com/ibrdtn/ibrdtn.git

ADD linux-compile.sh ibrdtn/scripts

RUN cd ibrdtn && bash scripts/linux-compile.sh

RUN mv ibrdtn/libibr* /usr/lib
