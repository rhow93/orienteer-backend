package v1.location

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

class LocationController @Inject() (
                                 components: ControllerComponents,
                                 val reactiveMongoApi: ReactiveMongoApi
                               ) extends AbstractController(components) with MongoController with ReactiveMongoComponents {

  implicit def ec: ExecutionContext = components.executionContext

  def collection: Future[JSONCollection] =
    database.map(_.collection[JSONCollection]("locations"))

  // apply a body parser to the action, which means that
  // request.body is now a JsValue
  def createLocation = Action.async(parse.json) { implicit request =>
    request.body.validate[Location].map { Location =>
      collection.flatMap(_.insert(Location)).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("Invalid JSON")))
  }

  def getAllLocations() = Action.async {
    val futureLocationsList: Future[List[JsObject]] = collection.flatMap {
      _.find(Json.obj()).cursor[JsObject](ReadPreference.primary).
        collect[List](100, Cursor.FailOnError[List[JsObject]]())
    }

    futureLocationsList.map(Json.arr(_)).map(Ok(_))
  }
  def getLocationsByName(name: String) = Action.async {
    val futureLocationsList: Future[Option[Location]] = collection.flatMap {
      _.find(Json.obj("name" -> name)).one[Location]
    }

    futureLocationsList.map(Json.arr(_)).map(Ok(_))
  }
}


