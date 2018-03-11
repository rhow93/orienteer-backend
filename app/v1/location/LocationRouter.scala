package v1.location

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

/**
  * Routes and URLs to the LocationResource controller.
  */
class LocationRouter @Inject()(controller: LocationController) extends SimpleRouter {
  val prefix = "/v1/location"

  override def routes: Routes = {
    case GET(p"/") =>
      controller.getAllLocations()

    case POST(p"/") =>
      controller.createLocation()

    case GET(p"/$id") =>
      controller.getLocationsByName(id)
  }

}
