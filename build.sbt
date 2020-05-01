name := "SnakeGame"

version := "0.1"

scalaVersion := "2.13.1"

val scalaTestVersion = "3.1.1"
val akkaVersion = "2.6.3"

lazy val commonSettings = Seq(
  scalaVersion := "2.13.1",
  libraryDependencies += "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  libraryDependencies += "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  libraryDependencies += "org.scalactic" %% "scalactic" % scalaTestVersion,
  libraryDependencies += "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
)

lazy val root = project
  .in(file("."))

lazy val streaming = project
  .in(file("game"))
  .settings(commonSettings)
