package controllers

import models.Twitt
import scaldi.Injector
import services.TwittService

class Twitts (implicit val injector: Injector) extends DAOController[Twitt] {
  val dao = new TwittService
}