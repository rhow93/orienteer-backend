package v1.location

import play.api.libs.json._

case class Point (
  hint: String,
  lat: Double,
  long: Double,
)

object Point {
  implicit val pointFormat = Json.format[Point]
}

case class Location (
  name: String,
  points: List[Point],
)

object Location {
  implicit val locationFormat = Json.format[Location]
}
