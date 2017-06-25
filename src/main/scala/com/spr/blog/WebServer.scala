package com.spr.blog

import akka.actor.{Actor, ActorLogging, PoisonPill, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import com.typesafe.config.ConfigFactory

import scala.util.{Failure, Success}

/**
  *
  */
class WebServer(route: Flow[HttpRequest, HttpResponse, Any])(implicit materializer: Materializer) extends Actor with ActorLogging {

  import context._

  private var binding: ServerBinding = _

  override def preStart(): Unit = {
    val config = ConfigFactory.load()
    val host = config.getString("http.host")
    val port = config.getInt("http.port")
    Http().bindAndHandle(route, host, port) onComplete {
      case Success(b) =>
        log.info(s"WebServer bound to $b")
        self ! b
      case Failure(t) =>
        log.error(t, "Error while attempting to bind WebServer")
        self ! PoisonPill
    }
  }

  override def postStop(): Unit = {
    binding.unbind()
    ()
  }

  override def receive: Receive = {
    case b: ServerBinding =>
      binding = b
      become(Actor.ignoringBehavior)
  }
}

object WebServer {
  def props(route: Flow[HttpRequest, HttpResponse, Any])(implicit materializer: Materializer) = Props(new WebServer(route))
}