package com.spr.blog

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.util.Timeout

import scala.concurrent.Await
import scala.concurrent.duration._

object Main {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val timeout = Timeout(10.seconds)
    val api = new ImplicitAkkaConfiguration with BlogRestApi
    val server = new RestApiServer(api)
    val bindingF = server.bind()
    sys.addShutdownHook {
      import scala.concurrent.ExecutionContext.Implicits.global
      val afterSystemTerminates = for {
        binding <- bindingF
        _ <- binding.unbind()
        _ <- system.terminate()
      } yield ()
      Await.result(afterSystemTerminates, 10.seconds)
    }
    ()
  }

}
