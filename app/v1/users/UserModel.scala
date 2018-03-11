package v1.users

import v1.location.{Point, Location}
import play.api.libs.json._
// import play.api.libs.functional.syntax._

case class User(
  name: String,
  age: Int,
  firebaseId: String,
  // id: String,
  // password: String, // such safety
  pointsFound: List[Point],
  locationsStarted: List[Location]
)

object User {
  implicit val userFormat = Json.format[User]

  // I'll leave this here for clarity in the future on how to create a Writes
  // implicit val userWrites: Writes[User] = (
  //   (JsPath \ "name").write[String] and
  //   (JsPath \ "age").write[Int]
  // )(unlift(User.unapply))
}
