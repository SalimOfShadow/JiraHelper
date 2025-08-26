ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "JiraHelper"
  )
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.2"