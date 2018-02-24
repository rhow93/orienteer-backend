package v1.users

import javax.inject.Inject
import play.api._
import play.api.mvc._
import play.api.libs.json._

case class User(name: String, age: Int)

class UserController extends Controller {
  def addUser: Action[AnyContent] = {
    Action({ implicit request =>
      val body: Option[JsValue] = request.body.asJson
      body match {
        case Some(_) =>
          println(body.get)
          Ok(body.get)
        case None =>
          BadRequest("missing things")
      }
    })
  }
}
