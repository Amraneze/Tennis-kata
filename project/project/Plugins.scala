package com.github.amraneze

import sbt._

object Plugins {

	object ScalaFmt {
		private val version: String = "2.4.2"
		val core: ModuleID = "org.scalameta" % "sbt-scalafmt" % version
	}

	object Bloop {
		private val version: String = "1.4.3"
		val core: ModuleID = "ch.epfl.scala" % "sbt-bloop" % version
	}

}
