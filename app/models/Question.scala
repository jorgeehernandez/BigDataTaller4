package models

import play.api.libs.json.Json
import reactivemongo.bson.{BSONDocument, BSONDocumentReader, BSONDocumentWriter, BSONObjectID}

case class Question(
                     id: Option[String],
                     tags: List[String],
                     link: String,
                     title: String,
                     body: String,
                     view_count: Int,
                     score: Int,
                     creation_date: Int,
                     question_id: Int,
                     places: List[String],
                     people: List[String],
                     dates: List[String],
                     songs: List[String],
                     artist_songs: Option[List[String]],
                     artist_places: Option[List[String]],
                     artist_genres: Option[List[String]],
                     artist_birthDay: Option[List[String]],
                     artist_instruments: Option[List[String]],
                     artist_uris: Option[List[String]],
                     group: String

                   ) extends Identifiable

object Question {

  implicit val format = Json.format[Question]

  implicit val questionDaoData = new DaoData[Question] {
    val filterSet = Set("creation_date", "people", "places", "group", "tags", "artist_uris", "artist_places")
    val attributeMap = Map("id" -> "_id")
  }


  implicit val QuestionBSONReader = new BSONDocumentReader[Question] {
    def read(doc: BSONDocument): Question =
      Question(
        doc.getAs[BSONObjectID](questionDaoData.dataSourceName("_id")) map {
          _.stringify
        },
        doc.getAs[List[String]](questionDaoData.dataSourceName("tags")).toList.flatten,
        doc.getAs[String](questionDaoData.dataSourceName("title")).get,
        doc.getAs[String](questionDaoData.dataSourceName("body")).get,
        doc.getAs[String](questionDaoData.dataSourceName("link")).get,
        doc.getAs[Int](questionDaoData.dataSourceName("view_count")).get,
        doc.getAs[Int](questionDaoData.dataSourceName("score")).get,
        doc.getAs[Int](questionDaoData.dataSourceName("creation_date")).get,
        doc.getAs[Int](questionDaoData.dataSourceName("question_id")).get,
        doc.getAs[List[String]](questionDaoData.dataSourceName("places")).get,
        doc.getAs[List[String]](questionDaoData.dataSourceName("people")).get,
        doc.getAs[List[String]](questionDaoData.dataSourceName("dates")).get,
        doc.getAs[List[String]](questionDaoData.dataSourceName("songs")).get,
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_songs")),
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_places")),
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_genres")),
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_birthDay")),
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_instruments")),
        doc.getAs[List[String]](questionDaoData.dataSourceName("artist_uris")),
        doc.getAs[String](questionDaoData.dataSourceName("group")).get
      )
  }


  implicit val QuestionBSONWriter = new BSONDocumentWriter[Question] {
    def write(question: Question): BSONDocument =
      BSONDocument(
        questionDaoData.dataSourceName("_id") -> question.id.map(BSONObjectID(_)),
        questionDaoData.dataSourceName("tags") -> question.tags,
        questionDaoData.dataSourceName("link") -> question.link,
        questionDaoData.dataSourceName("title") -> question.title,
        questionDaoData.dataSourceName("body") -> question.body,
        questionDaoData.dataSourceName("view_count") -> question.view_count,
        questionDaoData.dataSourceName("score") -> question.score,
        questionDaoData.dataSourceName("creation_date") -> question.creation_date,
        questionDaoData.dataSourceName("question_id") -> question.question_id,
        questionDaoData.dataSourceName("places") -> question.places,
        questionDaoData.dataSourceName("people") -> question.people,
        questionDaoData.dataSourceName("dates") -> question.dates,
        questionDaoData.dataSourceName("songs") -> question.songs,
        questionDaoData.dataSourceName("artist_songs") -> question.artist_songs,
        questionDaoData.dataSourceName("artist_places") -> question.artist_places,
        questionDaoData.dataSourceName("artist_genres") -> question.artist_genres,
        questionDaoData.dataSourceName("artist_birthDay") -> question.artist_birthDay,
        questionDaoData.dataSourceName("artist_instruments") -> question.artist_instruments,
        questionDaoData.dataSourceName("artist_uris") -> question.artist_uris,
        questionDaoData.dataSourceName("group") -> question.group

      )
  }


}




