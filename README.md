#### TROUBLESHOOTING

When running the ibrdtn Daemon with time synchrony (master & slave model) the slave will send his bundle with a timestamp
of 0. In the [ibrdtn-java-api](https://github.com/auzias/ibrdtn-api) bundles that are created with a 0 timestamp are set 
to the local time. Once it will try to retrieve the bundle from the daemon (with the local timestamp) it will fail. 

There is no problem when the master sends bundles.

**STILL A PROBLEM:** I don't see why the error code returned by the daemon 400 BAD REQUEST is, it should be 404 NOT_FOUND (I think).
So there is probably still something I don't fully understand in the source of the bug. 
