package com.spr.blog

import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import akka.util.Timeout

import scala.concurrent.ExecutionContext

trait BlogRestApi extends JsonSupport with BlogService {
  def route: Route =
    pathPrefix("api" / "blog") {
      (pathEndOrSingleSlash & post) {
        // POST /api/blog/
        entity(as[PostContent]) { content =>
          onSuccess(addPost(content)) { added =>
            complete((StatusCodes.Created, added))
          }
        }
      } ~
        pathPrefix(JavaUUID) { id =>
          pathEndOrSingleSlash {
            get {
              // GET /api/blog/:id
              onSuccess(getPost(id)) {
                case Right(content) => complete((StatusCodes.OK, content))
                case Left(error) => complete((StatusCodes.NotFound, error))
              }
            } ~
              put {
                // PUT /api/blog/:id
                entity(as[PostContent]) { content =>
                  onSuccess(updatePost(id, content)) {
                    case Right(updated) => complete((StatusCodes.OK, updated))
                    case Left(error) => complete((StatusCodes.NotFound, error))
                  }
                }
              }
          }
        }
    }
}

object BlogRestApi {

  def apply(timeout: Timeout)(implicit system: ActorSystem): BlogRestApi = new DefaultBlogRestApi(timeout)

  private class DefaultBlogRestApi(override val requestTimeout: Timeout)(implicit system: ActorSystem) extends BlogRestApi {
    override implicit val executionContext: ExecutionContext = system.dispatcher

    override def create(): ActorRef = system.actorOf(BlogEntity.props)
  }

}
