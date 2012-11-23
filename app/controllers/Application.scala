package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import models.Response

object Application extends Controller {
 
  val responseForm = Form(
    mapping(
      "id" -> longNumber,
      "requestUri" -> nonEmptyText,
      "response" -> text	
    )(Response.apply)(Response.unapply)
  )
 
  def index = Action {
    Redirect(routes.Application.responses)
  }
 

  def handle(requestUri: String) = Action { implicit request =>
    Ok("Request URI was: " + request.uri)	
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

  def deleteResponse(id: Long) = TODO
}
