package com.github.amraneze.entities

sealed trait Points

/**
 * When the there was a reset for player's points
 */
case object ZERO extends Points

/**
 * You win a game when you score 40 + win a ball
 */
case object GAME extends Points

/**
 * You win a set when you win 6 games
 */
case object SET extends Points

/**
 * You win a match when you need to win 2 sets
 */
case object MATCH extends Points

object Points {

  /**
   * A new type to count the points, it consist of the point of the player
   * an integer which count the number of points the player got and an integer
   * to count a sub Points: Example:
   * (SET, 1, 2) => it means that the player won one (the first integer 1) SET
   * and won 2 games (the second integer 2)
   */
  type CounterPoints = (Points, Int, Int)

  def incrementPoints(points: CounterPoints, newPoints: CounterPoints): CounterPoints = points match {
    // TODO implement this rule A game is won by the first player to have won at least four points in total and at least two points more than the opponent.
    case (ZERO, _, _) if newPoints._1 == GAME                       => (GAME, 1, 0)
    case (ZERO, _, _) if newPoints._1 == ZERO && newPoints._2 == 0  => points
    case (GAME, 5, _) if newPoints._1 == GAME                       => (SET, 1, 0)
    case (SET, 1, gamesWon) if newPoints._1 == GAME && gamesWon > 4 => (MATCH, 1, 0)
    case (SET, 1, gamesWon) if newPoints._1 == GAME                 => (SET, 1, gamesWon + 1)
    case (point, counter, gamesWon) if point == newPoints._1        => (point, counter + newPoints._2 + 1, gamesWon)
    case _                                                          => points
  }

}
