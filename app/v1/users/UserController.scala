package v1.users

import scala.concurrent.Future

import javax.inject.Inject
import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._
import play.modules.reactivemongo.{MongoController, ReactiveMongoApi, ReactiveMongoComponents}
import play.modules.reactivemongo.json.collection._

class UserController @Inject() (
  components: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(components) with MongoController with ReactiveMongoComponents {

  /*
   * Resolves a JSONCollection
   * (a Collection implementation that is designed to work with JsObject,
   * Reads and Writes).
   *
   * The deprecated `.db` function should be replaced as there by `.database`.
   *
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */
  def collection: Future[JSONCollection] =
    database.map(_.collection[JSONCollection]("users"))

  // apply a body parser to the action, which means that
  // request.body is now a JsValue
  def createUser = Action.async(parse.json) { implicit request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[User].map { user =>
      collection.flatMap(_.insert(user)).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid JSON")))
  }
}
