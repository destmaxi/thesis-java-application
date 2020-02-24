FROM ibrdtn:latest

RUN apt-get install default-jdk python3 python3-pip ssh -y
RUN pip3 install observable

ADD github .

RUN chmod 600 github

ENV GIT_SSH_COMMAND="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no"

RUN ssh-agent bash -c 'ssh-add github; git clone git@github.com:destmaxi/thesis.git app'

RUN sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin yes/g' /etc/ssh/sshd_config
RUN sed -i 's/#X11DisplayOffset 10/X11DisplayOffset 10/g' /etc/ssh/sshd_config
RUN sed -i 's/#AllowAgentForwarding yes/AllowAgentForwarding yes/g' /etc/ssh/sshd_config

RUN echo 'root:root' | chpasswd

# need to manually do to following dtnd -D -i eth0 && java -jar app/jar/java-app.jar
# launching dtnd from the entrypoint fails
# to launch the python script just exec it from another terminal "docker exec CONTAINER_NAME python3 test.py"
ENTRYPOINT RUN ssh-agent bash -c 'ssh-add github; git --git-dir=/usr/dtn_app/app/.git pull' && service ssh restart && /bin/bash
