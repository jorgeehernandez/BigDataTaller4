/*
 * Copyright (c) 2013-2015 Exaxis, LLC.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer
 *   in the documentation and/or other materials provided with the distribution.
 * - Neither the name of the Exaxis, LLC nor the names of its contributors may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS” AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 */

package models


import play.api.libs.json.Json
import reactivemongo.bson._


/**
  * The case class for the User Data access object. It needs to extend Identifiable or Temporal to be usable by the
  * reactive mongo wrapper.
  *
  * @param id
  * @param created_at
  * @param text
  * @param id_str
  * @param source
  * @param truncated
  * @param in_reply_to_status_id
  * @param in_reply_to_status_id_str
  * @param in_reply_to_user_id
  * @param in_reply_to_user_id_str
  * @param in_reply_to_screen_name
  * @param twitterUser
  */
case class Twitt(
                  id: Option[String],
                  created_at: String,
                  id_str: String,
                  text: String,
                  source: Option[String],
                  truncated: Option[Boolean],
                  in_reply_to_status_id: Option[Int],
                  in_reply_to_status_id_str: Option[String],
                  in_reply_to_user_id: Option[Int],
                  in_reply_to_user_id_str: Option[String],
                  in_reply_to_screen_name: Option[String],
                  twitterUser: Option[TwitterUser],
                  classification : Option[String]
                ) extends Identifiable

case class TwitterUser(
                        id_str: String,
                        name: String,
                        screen_name: Option[String],
                        location: Option[String],
                        url: Option[String]
                      )

object TwitterUser {

  implicit val format = Json.format[TwitterUser]

}

/**
  * This is the companion object for the User case class and contains all the additional mojo needed by the reactive mongo wrapper.
  *
  * By mojo we mean that it defines all the implicits that are needed for all the implicit parameters that are defined in all the
  * downstream libraries.
  *
  */
object Twitt {

  /**
    * String attribute names for the scala class
    */
  val attrId = "id"
  val created_at = "created_at"
  val id_str = "id_str"
  val text = "text"
  val source = "source"
  val truncated = "truncated"
  val in_reply_to_status_id = "in_reply_to_status_id"
  val in_reply_to_status_id_str = "in_reply_to_status_id_str"
  val in_reply_to_user_id = "in_reply_to_user_id"
  val in_reply_to_user_id_str = "in_reply_to_user_id_str"
  val in_reply_to_screen_name = "in_reply_to_screen_name"
  val twitterUser = "user"
  val classification = "classification"

  /**
    * String attribute names in the data store
    */
  val dsId = "_id"
  val dsAge = "_age"

  /**
    * The JSON Formatter needed by the BSONReader and BSONWriter
    */
  implicit val format = Json.format[Twitt]


  implicit val twittDaoData = new DaoData[Twitt] {
    /**
      * defines the attributes that will be matched against a query in the search.
      */
    val filterSet = Set(
      created_at
    )

    /**
      * defines the mapping of scala attributes to datastore attributes, same named attributes do not need to be mapped
      */
    val attributeMap = Map(
      attrId -> dsId
    )
  }


  implicit val twitterUserDaoData = new DaoData[TwitterUser] {
    val filterSet = Set(
      "name"
    )

    /**
      * defines the mapping of scala attributes to datastore attributes, same named attributes do not need to be mapped
      */
    val attributeMap = Map(
      "id" -> "_id"
    )
  }

  /**
    * Marshalls a TwitterUser into a BSONDocument
    *
    */
  implicit val TwitterUserBSONWriter = new BSONDocumentWriter[TwitterUser] {
    def write(twitterUser: TwitterUser): BSONDocument =
      BSONDocument(
        twitterUserDaoData.dataSourceName("id_str") -> twitterUser.id_str,
        twitterUserDaoData.dataSourceName("name") -> twitterUser.name,
        twitterUserDaoData.dataSourceName("screen_name") -> twitterUser.screen_name,
        twitterUserDaoData.dataSourceName("location") -> twitterUser.location,
        twitterUserDaoData.dataSourceName("url") -> twitterUser.url
      )
  }


  /**
    * Marshalls a BSONDocument into a TwitterUser
    *
    */
  implicit val TwitterUserBSONReader = new BSONDocumentReader[TwitterUser] {
    def read(doc: BSONDocument): TwitterUser =
      TwitterUser(
        doc.getAs[String](twitterUserDaoData.dataSourceName("id_str")).get,
        doc.getAs[String](twitterUserDaoData.dataSourceName("name")).get,
        doc.getAs[String](twitterUserDaoData.dataSourceName("screen_name")),
        doc.getAs[String](twitterUserDaoData.dataSourceName("location")),
        doc.getAs[String](twitterUserDaoData.dataSourceName("url"))
      )
  }


  /**
    * Marshalls a BSONDocument into a Twitt
    *
    */
  implicit val TwittBSONReader = new BSONDocumentReader[Twitt] {
    def read(doc: BSONDocument): Twitt =
      Twitt(
        doc.getAs[BSONObjectID](twittDaoData.dataSourceName(attrId)) map {
          _.stringify
        },
        doc.getAs[String](twittDaoData.dataSourceName(created_at)).get,
        doc.getAs[String](twittDaoData.dataSourceName(id_str)).get,
        doc.getAs[String](twittDaoData.dataSourceName(text)).get,
        doc.getAs[String](twittDaoData.dataSourceName(source)),
        doc.getAs[Boolean](twittDaoData.dataSourceName(truncated)),
        doc.getAs[Int](twittDaoData.dataSourceName(in_reply_to_status_id)),
        doc.getAs[String](twittDaoData.dataSourceName(in_reply_to_status_id_str)),
        doc.getAs[Int](twittDaoData.dataSourceName(in_reply_to_user_id)),
        doc.getAs[String](twittDaoData.dataSourceName(in_reply_to_user_id_str)),
        doc.getAs[String](twittDaoData.dataSourceName(in_reply_to_screen_name)),
        doc.getAs[TwitterUser](twittDaoData.dataSourceName(twitterUser)),
        doc.getAs[String](twittDaoData.dataSourceName(classification))
      )
  }


  /**
    * Marshalls a Twitt into a BSONDocument
    *
    */
  implicit val TwittBSONWriter = new BSONDocumentWriter[Twitt] {
    def write(twitt: Twitt): BSONDocument =
      BSONDocument(
        twittDaoData.dataSourceName(attrId) -> twitt.id.map(BSONObjectID(_)),
        twittDaoData.dataSourceName(created_at) -> twitt.created_at,
        twittDaoData.dataSourceName(id_str) -> twitt.id_str,
        twittDaoData.dataSourceName(text) -> twitt.text,
        twittDaoData.dataSourceName(source) -> twitt.source,
        twittDaoData.dataSourceName(truncated) -> twitt.truncated,
        twittDaoData.dataSourceName(in_reply_to_status_id) -> twitt.in_reply_to_status_id,
        twittDaoData.dataSourceName(in_reply_to_status_id_str) -> twitt.in_reply_to_status_id_str,
        twittDaoData.dataSourceName(in_reply_to_user_id) -> twitt.in_reply_to_user_id,
        twittDaoData.dataSourceName(in_reply_to_user_id_str) -> twitt.in_reply_to_user_id_str,
        twittDaoData.dataSourceName(in_reply_to_screen_name) -> twitt.in_reply_to_screen_name,
        twittDaoData.dataSourceName(twitterUser) -> twitt.twitterUser,
        twittDaoData.dataSourceName(classification) -> twitt.classification
      )
  }


}
