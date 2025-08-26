import OSActions._
import com.typesafe.config.{Config, ConfigFactory}

object JiraHelper {
  val userConfig: Config = ConfigFactory.load().getConfig("user-config")
  val osActions = new OSActions(userConfig)
  println(userConfig)

  def main(args: Array[String]): Unit = {
    val response = osActions.createDirectory("21")
    println(response)
  }
}