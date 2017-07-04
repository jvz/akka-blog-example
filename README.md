This repository contains sample code for implementing a trivial blog microservice
using [Akka](http://akka.io/) and [Scala](http://scala-lang.org/). The ideas
being demonstrated here include CQRS and event sourcing, actors, and various
Scala syntax concepts. Some inspiration for Akka-related patterns used here are
from the [Akka in Action source code](https://github.com/RayRoestenburg/akka-in-action).

See [my blog post](http://musigma.org/scala/2017/07/03/akka-cqrs.html) for more
information. This project uses SBT and can be run by simply executing:

    sbt run

This will run an HTTP server on port 8080 by default. This can be configured by
the `application.conf` file.
