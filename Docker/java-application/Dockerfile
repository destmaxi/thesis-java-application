FROM ibrdtn:latest

RUN apt-get install default-jdk python3 python3-pip ssh -y
RUN pip3 install observable

ADD github .

RUN chmod 600 github
RUN alias ll='ls -la'

ENV GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"

RUN ssh-agent bash -c 'ssh-add github; git clone git@github.com:destmaxi/thesis.git app'

# need to manually do to following dtnd -D -i eth0 && java -jar app/jar/java-app.jar
# launching dtnd from the entrypoint fails
# to launch the python script just exec it from another terminal "docker exec CONTAINER_NAME python3 test.py"
ENTRYPOINT /bin/bash