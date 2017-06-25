package com.spr.blog

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  *
  */
object Main {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    val api = BlogRestApi(10.seconds)
    system.actorOf(WebServer.props(api.route))
    sys.addShutdownHook {
      Await.result(system.terminate(), 10.seconds)
      ()
    }
    ()
  }

}
