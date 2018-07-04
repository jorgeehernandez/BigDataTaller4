package controllers

import models.Question
import scaldi.Injector
import services.QuestionService

class Questions(implicit val injector: Injector) extends DAOController[Question] {
  val dao = new QuestionService
}