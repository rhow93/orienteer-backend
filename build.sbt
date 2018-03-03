name := """orienteer-backend"""
organization := "com.roryhow"

val buildVersion = "0.13.0-play26"

version := "0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

// scalaVersion := "2.12.4"

resolvers += "Sonatype Staging" at "https://oss.sonatype.org/content/repositories/staging/"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "4.11"
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % buildVersion

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.roryhow.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.roryhow.binders._"
