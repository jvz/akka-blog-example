package com.spr.akka

import akka.http.scaladsl.server.{Directives, Route}

/**
  * Provides a route for a REST API. This mixes in useful support traits for the route DSL and automatic JSON
  * marshalling and unmarshalling support.
  */
trait RestApi extends Directives with JsonSupport {
  def route: Route
}
