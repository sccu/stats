organization := "name.sccu"

name := "stats"

version := "0.1"

scalaVersion := "2.11.7"

fork := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "latest.integration" % "test",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.7"
)

libraryDependencies += "commons-cli" % "commons-cli" % "1.3.1"

lazy val csv4s = ProjectRef(uri("git://github.com/sccu/csv4s.git#v0.1.6"), "csv4s")
lazy val stats = (project in file(".")).
  settings(
    scalaVersion := "2.11.2"
  ).
  dependsOn(csv4s)
