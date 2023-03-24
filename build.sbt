import Dependencies._

ThisBuild / scalaVersion     := "2.13.10"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

val specs2Version = "4.17.0"

val myDependencies = Seq(
  "org.typelevel"           %% "cats-effect"                  % "2.5.3",
  "org.specs2"              %% "specs2-core"                  % specs2Version             % "test",
  "org.specs2"              %% "specs2-matcher-extra"         % specs2Version             % "test"
)

lazy val root = (project in file("."))
  .settings(
    name := "my-csv-processor",
    libraryDependencies ++= myDependencies
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
