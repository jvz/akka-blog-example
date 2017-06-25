package com.spr.blog

import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.AutoDerivation

/**
  *
  */
trait JsonSupport extends FailFastCirceSupport with AutoDerivation
