import OSActions._
import com.typesafe.config.{Config, ConfigFactory}
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json._
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._


case class ApiResponse(status: String, message: String)
case class IssueRequest(issueNumber: String)

object JsonFormats extends DefaultJsonProtocol {
  implicit val apiResponseFormat: RootJsonFormat[ApiResponse] = jsonFormat2(ApiResponse)
  implicit val issueRequestFormat: RootJsonFormat[IssueRequest] = jsonFormat1(IssueRequest)
}

object JiraHelper {
  implicit val system: ActorSystem = ActorSystem("scala-http-server")
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  val userConfig = ConfigFactory.load().getConfig("user-config")
  val osActions = OSActions(userConfig)

  println(userConfig)

  def main(args: Array[String]): Unit = {
    import JsonFormats._

    val route: Route =
      concat(
        path("hello") {
          get {
            complete("Hello, World!")
          }
        },
        path("create-directory") {
          post {
            entity(as[String]) { body =>
              val issueNumber = body.parseJson.convertTo[IssueRequest].issueNumber
              val result = osActions.createResources(issueNumber)

              result match {
                case Success(msg) =>
                  complete(ApiResponse("success", msg))
                case Failure(err) =>
                  complete(StatusCodes.InternalServerError, ApiResponse("error", err))
              }
            }
          }
        }
      )

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println("Server online at http://localhost:8080/\n" +
      "Press RETURN to stop...")
    StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
