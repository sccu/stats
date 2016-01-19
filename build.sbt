name := "stats"

version := "1.0"

scalaVersion := "2.11.7"

fork := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "latest.integration" % "test",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2"
)

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-compiler" % "2.11.7"
//  "org.scala-lang" % "jline" % "2.11.0-M3"
)
