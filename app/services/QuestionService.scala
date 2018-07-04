package services

import models.{Question}
import models.dao.IdentifiableDAO
import scaldi.Injector

class QuestionService(implicit val injector: Injector) extends IdentifiableDAO[Question] {
  val collectionName = "questions"
}