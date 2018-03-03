package v1.users

import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User(
  name: String,
  age: Int
)

object User {
  implicit val userFormat = Json.format[User]

  // implicit val userWrites: Writes[User] = (
  //   (JsPath \ "name").write[String] and
  //   (JsPath \ "age").write[Int]
  // )(unlift(User.unapply))
}
