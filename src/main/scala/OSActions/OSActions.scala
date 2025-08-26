package OSActions

import better.files._
import better.files.File._

sealed trait ActionResponse
final case class Success(message: String = "Action completed") extends ActionResponse
final case class Failure(error: String) extends ActionResponse

case class OSActions() {
  def createDirectory(issueNumber: String): ActionResponse = {
    val dir: File = s"/Users/Alex/Desktop/${issueNumber}"
      .toFile
      .createIfNotExists(asDirectory = true)
    if (dir.exists) Success(s"Folder created Successfully for issue ${issueNumber}")
    else Failure("Failed to created a folder")
  }
}
