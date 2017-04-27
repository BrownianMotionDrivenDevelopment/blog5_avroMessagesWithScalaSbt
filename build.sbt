name := """messages"""
organization := "com.brownian-motion-driven-dev.avro-messages"
version := "0.0.1"

scalaVersion := "2.11.11"
ivyScala := ivyScala.value map { _.copy(overrideScalaVersion = true)}

scalacOptions ++= Seq(
  "-unchecked",
  "-deprecation",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-Ywarn-dead-code",
  "-Ywarn-infer-any",
  "-Ywarn-unused-import",
  "-Xfatal-warnings",
  "-Xlint",
  "-encoding", "utf8")

sbtavrohugger.SbtAvrohugger.specificAvroSettings

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.apache.avro" % "avro" % "1.8.1",

  // test
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)


