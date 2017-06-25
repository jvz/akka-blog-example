package com.spr.blog

import java.util.UUID

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import scala.concurrent.{ExecutionContext, Future}

/**
  * Wrapper API around a [[Blog]] actor.
  */
trait BlogService {

  import Blog._

  def create(): ActorRef

  implicit val executionContext: ExecutionContext
  implicit val requestTimeout: Timeout

  lazy val blogService: ActorRef = create()

  def getPost(id: UUID): Future[MaybePost[PostContent]] =
    (blogService ? GetPost(id)).mapTo[MaybePost[PostContent]]

  def addPost(content: PostContent): Future[PostAdded] =
    (blogService ? AddPost(content)).mapTo[PostAdded]

  def updatePost(id: UUID, content: PostContent): Future[MaybePost[PostUpdated]] =
    (blogService ? UpdatePost(id, content)).mapTo[MaybePost[PostUpdated]]

}
