name := """morphia-factory"""

organization := "com.lvxingpai"

version := "0.1.1-SNAPSHOT"

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
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

scalariformSettings

publishTo := {
  val nexus = "http://nexus.lvxingpai.com/content/repositories/"
  if (isSnapshot.value)
    Some("publishSnapshots" at nexus + "snapshots")
  else
    Some("publishReleases" at nexus + "releases")
}

fork in run := true
