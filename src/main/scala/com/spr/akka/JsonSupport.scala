package com.spr.akka

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

/**
  * Mixin to provide automatic marshalling and unmarshalling support for case classes and various fundamental data types.
  */
trait JsonSupport extends FailFastCirceSupport with AutoDerivation
