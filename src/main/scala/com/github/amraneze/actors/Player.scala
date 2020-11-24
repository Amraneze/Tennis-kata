package com.github.amraneze.actors

import com.github.amraneze.entities.{LOVE, Points, Score, ZERO}
import com.github.amraneze.entities.Points.CounterPoints
import com.github.amraneze.entities.{LOVE, Points, Score, ZERO}

/**
 * A tennis player entity
 *
 * @param name the name of the tennis player
 * @param isServing if the player is serving the ball
 * @param score the score of the player, starts with LOVE and increment to FIFTEEN, THIRTY and FORTY
 * @param points the points of the player, if he won a Match, a Set or a Game
 */
case class Player(name: String, isServing: Boolean, score: Score, points: CounterPoints, hasBall: Boolean = false)

object Player {

  def apply(name: String, isServing: Boolean, score: Score, points: CounterPoints, hasBall: Boolean): Player =
    new Player(name, isServing, score, points, hasBall)

  def apply(player: Player,
            isServing: Boolean,
            incrementScore: Boolean = false,
            decrementScore: Boolean = false,
            incrementPoints: Boolean = false,
            resetScore: Boolean = false,
            hasBall: Boolean = false,
            opponentScore: Score): Player = {

    def incrementPoints(score: Either[Score, Points]): CounterPoints = score match {
      case Left(_)              => Points.incrementPoints(player.points, (ZERO, 0, 0))
      case Right(point: Points) => Points.incrementPoints(player.points, (point, 0, 0))
    }

    val score: Either[Score, Points] = {
      if (incrementScore)
        Score.incrementScore(player.score, opponentScore)
      else if (decrementScore)
        Score.decrementScore(player.score)
      else
        Left(player.score)
    }
    val newPoints: CounterPoints = if (incrementScore) incrementPoints(score) else player.points
    Player(player.name, isServing, score.left.getOrElse(LOVE), newPoints, hasBall)
  }
}
