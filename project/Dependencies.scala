package com.github.amraneze

import sbt.{Def, _}

object Dependencies {

  val logBackVersion: String = "1.2.3"
  val scalaTestVersion: String = "3.2.2"
  val scalaFakerVersion: String = "1.0.2"

  object Scala {
    val version: String = "2.13.3"
  }

  def javaFaker: Def.Initialize[ModuleID] = Def.setting {
    "com.github.javafaker" % "javafaker" % scalaFakerVersion
  }

  def logBack: Def.Initialize[ModuleID] = Def.setting {
    "ch.qos.logback" % "logback-classic" % logBackVersion
  }

  def dependencies(deps: ModuleID*): Seq[ModuleID] = deps

  def scalaTest: Def.Initialize[List[ModuleID]] = Def.setting {
    "org.scalatest" %% "scalatest" % scalaTestVersion :: Nil
  }

  def testDependencies(deps: List[ModuleID]): Seq[ModuleID] = deps map (_ % "it, test")

}
