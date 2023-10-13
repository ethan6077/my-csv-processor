ThisBuild / scalaVersion := "2.13.10"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "com.ethan-zhang"
ThisBuild / organizationName := "ethan-zhang-company"

val doobieVersion = "1.0.0-RC4"
val specs2Version = "4.20.0"

val myDependencies = Seq(
  "org.typelevel" %% "cats-effect" % "3.5.1",
  "org.tpolecat" %% "doobie-core" % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2" % doobieVersion,
  "org.tpolecat" %% "doobie-hikari" % doobieVersion,
  "com.github.javafaker" % "javafaker" % "1.0.2",
  "org.specs2" %% "specs2-core" % specs2Version % "test",
  "org.specs2" %% "specs2-matcher-extra" % specs2Version % "test"
)

lazy val root = (project in file("."))
  .settings(
    name := "my-csv-processor",
    libraryDependencies ++= myDependencies
  )

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
