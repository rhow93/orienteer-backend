package v1.users

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class UserRouter @Inject()(controller: UserController) extends SimpleRouter {
  override def routes: Routes = {
    case GET(p"/$name") =>
      controller.findUsersByName(name)
    case POST(p"/") =>
      controller.createUser
  }
}
