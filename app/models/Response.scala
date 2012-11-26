package models

import anorm._

case class Response(id: Pk[Long] = NotAssigned, requestUri: String, response: String)

object Response {

  import anorm.SqlParser._
  import play.api.db._
  import play.api.Play.current

  val response = {
    get[Pk[Long]]("id") ~
    get[String]("requestUri") ~
    get[String]("response") map {
      case id~requestUri~response => Response(id, requestUri, response)
    }

  }

  def all(): List[Response] = DB.withConnection { implicit c =>
    SQL("select * from response").as(response *)
    
  }

  def create(requestUri: String, response: String) {
    DB.withConnection{ implicit c =>
      SQL("insert into response (requestUri, response) values ({requestUri}, {response})").on(
       'requestUri -> requestUri,
       'response -> response
      ).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection{ implicit c =>
      SQL("delete from response where id={id}").on(
        'id -> id
      ).executeUpdate()
    }
  }
}
