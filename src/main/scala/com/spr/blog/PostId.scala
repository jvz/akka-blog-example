package com.spr.blog

import java.util.UUID

import io.circe.{Decoder, Encoder}

/**
  * Value wrapper class for post ids.
  */
class PostId(val id: UUID) extends AnyVal {
  override def toString: String = id.toString
}

object PostId {
  /** Constructs a new PostId with a random id. */
  def apply(): PostId = new PostId(UUID.randomUUID())

  /** Constructs a new PostId with a given id. */
  def apply(id: UUID): PostId = new PostId(id)

  // custom circe marshaller and unmarshaller so that the id is not wrapped
  implicit val postIdDecoder: Decoder[PostId] = Decoder.decodeUUID.map(PostId(_))
  implicit val postIdEncoder: Encoder[PostId] = Encoder.encodeUUID.contramap(_.id)
}
