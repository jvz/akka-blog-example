import CompilerFlags._

name := "akka-blog-microservice"
organization := "com.spr"
version := "1.0"

mainClass := Some("com.spr.blog.Main")

scalaVersion := "2.12.2"

scalacOptions ++= compilerFlags
scalacOptions in(Compile, console) ~= filterExcludedReplOptions

lazy val akkaVersion = "2.5.2"
lazy val akkaHttpVersion = "10.0.8"
lazy val circeVersion = "0.8.0"
lazy val akkaJsonVersion = "1.16.1"

libraryDependencies ++= Seq(
  "akka-actor",
  "akka-persistence",
  "akka-stream"
).map { "com.typesafe.akka" %% _ % akkaVersion } ++ Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
  "io.circe" %% "circe-generic" % circeVersion,
  "org.iq80.leveldb" % "leveldb" % "0.7",
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",
  "de.heikoseeberger" %% "akka-http-circe" % akkaJsonVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)
