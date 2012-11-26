package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.Response

import anorm._

object Application extends Controller {
 
  val responseForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "requestUri" -> nonEmptyText,
      "response" -> text	
    )(Response.apply)(Response.unapply)
  )
 
  def index = Action {
    Redirect(routes.Application.responses)
  }
 

  def handle(requestUri: String) = Action { implicit request =>
    val responseOption : Option[Response] = Response.all().find({e: Response => e.requestUri == request.uri})
    responseOption match {
      case Some(response) => {
        Ok(response.response)
      }
      case None => {
        Ok("NotFound")
      }
    }
  }


  def responses = Action {
    Ok(views.html.index(Response.all(), responseForm))
  }

  def newResponse = Action { implicit request =>
    responseForm.bindFromRequest.fold(
      errors => BadRequest(views.html.index(Response.all(), errors)),
      resp => { Response.create(resp.requestUri, resp.response)
      		Redirect(routes.Application.responses)	
      }
    )
  }

  def deleteResponse(id: Long) = Action {
    Response.delete(id)
    Redirect(routes.Application.responses)
  }
}
