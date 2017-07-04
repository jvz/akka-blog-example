package com.spr.akka

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.Materializer
import com.typesafe.config.ConfigFactory

import scala.concurrent.Future

/**
  * Provides an HTTP server using a given RestApi.
  */
class RestApiServer(api: RestApi)(implicit system: ActorSystem, materializer: Materializer) {

  def bind(): Future[ServerBinding] = {
    val config = ConfigFactory.load()
    val host = config.getString("http.host")
    val port = config.getInt("http.port")
    implicit val system = this.system
    implicit val materializer = this.materializer
    Http().bindAndHandle(api.route, host, port)
  }

}
