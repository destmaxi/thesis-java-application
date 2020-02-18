#!/bin/bash -xe
#
ROOT_PATH=$(pwd)
DESTDIR="$(pwd)/linux-inst"

# set defaults
[ -z "${CLEAN}" ] && CLEAN=1
[ -z "${STRIP}" ] && STRIP=1
[ -z "${DEBUG}" ] && DEBUG=0
[ -z "${PROFILE}" ] && PROFILE=0

CXXFLAGS=""

if [ ${DEBUG} -eq 1 ]; then
    CXXFLAGS+=" -ggdb -g3 -Wall"
    STRIP=0
fi

if [ ${PROFILE} -eq 1 ]; then
    CXXFLAGS+=" -pg"
fi

# clean destdir
rm -rf ${DESTDIR}

cd ibrcommon
if [ ${CLEAN} -eq 1 ]; then
    [ -f Makefile ] && make clean
    bash autogen.sh
    ./configure --with-openssl --with-lowpan
fi
make -j
make install
cd ..

cd ibrdtn/ibrdtn
if [ ${CLEAN} -eq 1 ]; then
    [ -f Makefile ] && make clean
    bash autogen.sh
    ./configure --with-dtnsec --with-compression --with-ibrcommon=${ROOT_PATH}/ibrcommon
fi

make -j
make install
cd ..

cd daemon
if [ ${CLEAN} -eq 1 ]; then
    [ -f Makefile ] && make clean
    bash autogen.sh
    ./configure --with-curl --with-lowpan --with-sqlite --with-dtnsec --with-compression --with-tls --with-cppunit --with-ibrcommon=${ROOT_PATH}/ibrcommon --with-ibrdtn=${ROOT_PATH}/ibrdtn/ibrdtn
fi
make -j
make install
cd ..

cd tools
if [ ${CLEAN} -eq 1 ]; then
    [ -f Makefile ] && make clean
    bash autogen.sh
    ./configure
fi

make -j
make install
cd ..
cd ..
ln -s /usr/local/lib/libibrcommon.so.1.0.0 libibrcommon.so.1
ln -s /usr/local/lib/libibrdtn.so.1.0.0 libibrdtn.so.1
