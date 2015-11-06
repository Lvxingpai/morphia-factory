name := """morphia-factory"""

organization := "com.lvxingpai"

version := "0.2.0"

scalaVersion := "2.11.4"

crossScalaVersions := "2.10.4" :: "2.11.4" :: Nil

val morphiaVersion = "1.0.1"

libraryDependencies ++= Seq(
  // Change this to another test framework if you prefer
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.mongodb.morphia" % "morphia" % morphiaVersion,
  "org.mongodb.morphia" % "morphia-validation" % morphiaVersion,
  "com.google.inject" % "guice" % "4.0",
  "com.typesafe" % "config" % "1.3.0"
)

// Uncomment to use Akka

