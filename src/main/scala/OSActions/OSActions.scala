package OSActions

import better.files._
import better.files.File._
import com.typesafe.config.Config

sealed trait ActionResponse
final case class Success(message: String = "Action completed") extends ActionResponse
final case class Failure(error: String) extends ActionResponse

case class OSActions(userConfig: Config) {

  def createDirectory(issueNumber: String): ActionResponse = {
    try {
      val basePath: String = userConfig.getString("selectedPath")

      val ticketResourcesDir: File = (File(basePath) / "Ticket Resources").createIfNotExists(asDirectory = true)

      val issueDir: File = (ticketResourcesDir / issueNumber).createIfNotExists(asDirectory = true)

      if (issueDir.exists)
        Success(s"Folder created successfully for issue $issueNumber")
      else
        Failure(s"Failed to create folder for issue $issueNumber")
    } catch {
      case ex: Exception => Failure(s"Error creating directory: ${ex.getMessage}")
    }
  }

  def createSQLFile(issueNumber: String): ActionResponse = {
    try {
      val sqlFile: File = (File(userConfig.getString("selectedPath")) / "Ticket Resources" / issueNumber / s"$issueNumber.sql")
        .createIfNotExists()
      if (sqlFile.exists)
        Success(s"SQL file created successfully for issue $issueNumber")
      else
        Failure(s"Failed to create the SQL file for issue $issueNumber")
    } catch {
      case ex: Exception => Failure(s"Error creating SQLFile ${ex.getMessage}")
    }
  }

  def createNotesFile(issueNumber: String): ActionResponse = {
    try {
      val sqlFile: File = (File(userConfig.getString("selectedPath")) / "Ticket Resources" / issueNumber / s"$issueNumber.txt")
        .createIfNotExists()
      if (sqlFile.exists)
        Success(s"Notes file created successfully for issue $issueNumber")
      else
        Failure(s"Failed to create the notes file for issue $issueNumber")
    } catch {
      case ex: Exception => Failure(s"Error creating notes ${ex.getMessage}")
    }
  }

}
