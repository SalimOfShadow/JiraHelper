ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.16"

lazy val root = (project in file("."))
  .settings(
    name := "JiraHelper"
  )
libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.9.2"
libraryDependencies += "com.typesafe" % "config" % "1.4.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
  "com.typesafe.akka" %% "akka-http" % "10.5.3",
  "com.typesafe.akka" %% "akka-stream" % "2.8.8",
  "io.spray" %% "spray-json" % "1.3.6",
  "com.typesafe" % "config" % "1.4.4",
  "com.github.pathikrit" %% "better-files" % "3.9.2",
  "com.typesafe.akka" %% "akka-http-spray-json" % "10.5.3",
  "io.spray" %% "spray-json" % "1.3.6"
)