package v1.users

import javax.inject.Inject
import play.api.mvc.Result

import scala.concurrent.{ ExecutionContext, Future }

import play.api.Logger
import play.api.mvc.{ AbstractController, Action, ControllerComponents }
import play.api.libs.json._

// Reactive Mongo imports
import reactivemongo.api.Cursor
import reactivemongo.api.ReadPreference
import reactivemongo.play.json.collection.JSONCollection

import play.modules.reactivemongo.{ // ReactiveMongo Play2 plugin
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}

// BSON-JSON conversions/collection
import reactivemongo.play.json._

class UserController @Inject() (
  components: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi
) extends AbstractController(components) with MongoController with ReactiveMongoComponents {

  implicit def ec: ExecutionContext = components.executionContext

  /*
   * Resolves a JSONCollection
   * (a Collection implementation that is designed to work with JsObject,
   * Reads and Writes).
   *
   * The deprecated `.db` function should be replaced as there by `.database`.
   *
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the col)lection reference to avoid potential problems in development with
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

  def findUsersByName(name: String) = Action.async {
    val cursor: Future[Cursor[JsObject]] = collection.map {
      _.find(Json.obj("name" -> name))
        .cursor[JsObject](ReadPreference.primary)
    }

    val futureUsersList: Future[List[JsObject]] =
      cursor.flatMap(_.collect[List](Int.MaxValue, Cursor.FailOnError[List[JsObject]]()))

    futureUsersList.map(Json.arr(_)).map(Ok(_))
  }
}
