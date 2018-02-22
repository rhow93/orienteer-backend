package v1.location

import javax.inject.Inject

import play.api.Logger
import play.api.data.Form
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Takes HTTP requests and produces JSON.
  */
class LocationController @Inject()(cc: LocationControllerComponents) extends LocationBaseController(cc) {


  def getLocation(id: String): Action[AnyContent] = Action({
    Ok(Json.obj(
      "name" -> id,
    ))
  })
}


