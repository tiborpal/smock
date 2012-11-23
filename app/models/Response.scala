package models

case class Response(id: Long, requestUri: String, response: String)

object Response {
  def all(): List[Response] = Nil

  def create(requestUri: String, response: String) {}

  def delete(id: Long) {}
}
