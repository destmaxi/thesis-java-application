FROM ibrdtn:latest

RUN apt-get install default-jdk python3

ADD application.jar .
ADD test.py .

# need to manually do to following dtnd -D -i eth0 && java -jar application.jar
# launching dtnd from the entrypoint fails
# to launch the python script just exec it from another terminal "docker exec CONTAINER_NAME python3 test.py"
ENTRYPOINT /bin/bash