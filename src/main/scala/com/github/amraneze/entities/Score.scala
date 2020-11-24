package com.github.amraneze.entities

sealed abstract class Score(val name: String)

case object LOVE extends Score("love")
case object FIFTEEN extends Score("fifteen")
case object THIRTY extends Score("thirty")
case object FORTY extends Score("forty")
case object ADVANTAGE extends Score("advantage")

object Score {
  def incrementScore(score: Score, opponentScore: Score): Either[Score, Points] = score match {
    case LOVE                            => Left(FIFTEEN)
    case FIFTEEN                         => Left(THIRTY)
    case THIRTY                          => Left(FORTY)
    case FORTY if opponentScore == FORTY => Left(ADVANTAGE)
    case FORTY | ADVANTAGE               => Right(GAME)
  }

  def decrementScore(score: Score): Either[Score, Points] = score match {
    case ADVANTAGE => Left(FORTY)
    case _         => Left(score)
  }
}
