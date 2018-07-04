package services

import models.Twitt
import models.dao.IdentifiableDAO
import scaldi.Injector

/**
  * Created by dberry on 17/8/16.
  */
class TwittService(implicit val injector: Injector) extends IdentifiableDAO[Twitt] {
  val collectionName = "twitts"
}
