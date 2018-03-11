package v1.location

import play.api.libs.json._

case class Point (name: String, hint: String, lat: Double, lng: Double)

object Point {
  implicit val pointFormat = Json.format[Point]
}

case class Location (
  name: String,
  points: List[Point],
)

object Location {

  // allows us to convert between JSON and the Loction case class
  implicit val locationFormat = Json.format[Location]
}
