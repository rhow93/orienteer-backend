name := """orienteer-backend"""
organization := "com.roryhow"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "4.11"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.roryhow.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.roryhow.binders._"
