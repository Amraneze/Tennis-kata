import sbt._
import Keys._
import com.github.amraneze.Dependencies._

lazy val root = project
	.in(file("."))
	.settings(
		name := "Tennis",
		version := "0.0.1",
		organization := "Total",
		scalaVersion := Scala.version,
		mainClass in (Compile, run) := Some("com.github.amraneze.Game"),
		libraryDependencies ++= dependencies(javaFaker.value, logBack.value) ++ testDependencies(scalaTest.value)
)


