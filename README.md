## Test the java-application

- build the docker image in ibrdtn with the tag "ibrdtn"
- build the docker image in java-application
- create a network with ipv6 
```
$ docker network create --ipv6 --subnet 2001:db8:1::/64 mynetwork
```

- run the java-application docker image
```
$ docker run --name javaapp --hostname node1 -it --rm --network mynetwork javaapp
```

Normally you should have a bash terminal open

- launch the ibrdtn daemon `$ dtnd -D -i eth0`
- launch the java application `$ java -jar app/jar/java-app.jar`

Open anther terminal to repeat the previous steps (don't forget to change the hostname)

Finally you can from (again) another terminal launch the python script that will send some data to the application.
```
$ docker exec javaapp python3 app/src/main/python/com/datacollector/Application.py
```

## TODO:

- Make a docker-compose to launch multiple containers easily
- Make a script to initialise all dtnd daemons and java application in the different containers
