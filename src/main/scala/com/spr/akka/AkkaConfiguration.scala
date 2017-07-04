package com.spr.akka

import akka.actor.ActorRefFactory
import akka.stream.Materializer
import akka.util.Timeout

import scala.concurrent.ExecutionContextExecutor

/**
  * Mix-in for pulling in Akka-related classes and configuration.
  */
trait AkkaConfiguration {

  /** Provides either an ActorSystem or ActorContext for spawning actors. */
  implicit val actorRefFactory: ActorRefFactory
  /** Provides a stream materializer for executing streams. */
  implicit val materializer: Materializer
  /** Provides an execution context for composing futures. */
  implicit val executionContext: ExecutionContextExecutor = actorRefFactory.dispatcher
  /** Provides a default timeout for the ask pattern. */
  implicit val askTimeout: Timeout

}

/**
  * Implicit-based implementation of AkkaConfiguration. Given a trait `Foo` which mixes in `AkkaConfiguration`, a
  * concrete instance of `Foo` can be created by injecting implicitly-available instances like so:
  *
  * {{{
  *   implicit val system = ActorSystem()
  *   implicit val materializer = ActorMaterializer()
  *   implicit val timeout = Timeout(30.seconds)
  *   val foo = new ImplicitAkkaConfiguration with Foo
  * }}}
  */
class ImplicitAkkaConfiguration(implicit
  override val actorRefFactory: ActorRefFactory,
  override val materializer: Materializer,
  override val askTimeout: Timeout
) extends AkkaConfiguration
