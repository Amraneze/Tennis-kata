package com.github.amraneze.helpers

import com.github.amraneze.actors
import com.github.javafaker.Faker
import com.github.amraneze.actors.Player
import com.github.amraneze.entities.{GAME, LOVE, MATCH, Points, SET, Score, ZERO}
import com.github.amraneze.entities.Points.CounterPoints
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

trait Helper extends AnyFlatSpecLike with Matchers {

  lazy val faker: Faker = new Faker()
  lazy val player: Player = actors.Player(faker.name().name(), isServing = true, LOVE, (ZERO, 0, 0), hasBall = true)
  lazy val opponent: Player = actors.Player(faker.name().name(), isServing = false, LOVE, (ZERO, 0, 0), hasBall = false)

  def generateRandomPlayer(name: String = faker.name().name(),
                           isServing: Boolean = false,
                           score: Score = LOVE,
                           points: CounterPoints = (ZERO, 0, 0),
                           hasBall: Boolean = false): Player = Player(name, isServing, score, points, hasBall)

  def generateRandomPoints(points: Points): CounterPoints = points match {
    case ZERO  => (ZERO, 0, 0)
    case GAME  => (GAME, randomIntInRange(0, 6), 0)
    case SET   => (SET, randomIntInRange(0, 2), randomIntInRange(0, 6))
    case MATCH => (MATCH, randomIntInRange(0, 1), 0)
  }

  def randomIntInRange(min: Int, max: Int): Int =
    new scala.util.Random().nextInt(max - min)

}
