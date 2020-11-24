package com.github.amraneze.services

import com.github.amraneze.actors.Player
import com.github.amraneze.entities.{ADVANTAGE, MATCH}
import com.github.javafaker.Faker

case class TennisService(var firstPlayer: Player, var secondPlayer: Player) {
  val faker: Faker = new Faker()

  var serveFails: Int = 0

  def serve(shouldServeFail: Boolean): Boolean = {
    def onServe(servingPlayer: Player, player: Player, shouldServeFail: Boolean): (Player, Player, Boolean) = {
      def switchBallBetweenPlayers(oldPlayerWithBall: Player, oldPlayerWithoutBall: Player): (Player, Player, Boolean) = {
        val playerWithBall = Player(oldPlayerWithoutBall,
          isServing = false,
          hasBall = true,
          opponentScore = oldPlayerWithBall.score)
        val playerWithoutBall = Player(oldPlayerWithBall,
          isServing = true,
          opponentScore = oldPlayerWithoutBall.score)
        (playerWithBall, playerWithoutBall, true)
      }

      if (serveFails >= 1) {
        if (shouldServeFail) {
          serveFails = 0
          val newPlayer =
            Player(servingPlayer, isServing = false, opponentScore = player.score, decrementScore = true)
          val isItDeuce = servingPlayer.score == ADVANTAGE

          val newServingPlayer = Player(player,
                                        isServing = true,
                                        incrementScore = !isItDeuce,
                                        incrementPoints = !isItDeuce,
                                        hasBall = true,
                                        opponentScore = servingPlayer.score)
          (newPlayer, newServingPlayer, false)
        } else {
          switchBallBetweenPlayers(servingPlayer, player)
        }
      } else {
        if (shouldServeFail) {
          serveFails = serveFails + 1
          (servingPlayer, player, false)
        } else {
          switchBallBetweenPlayers(servingPlayer, player)
        }
      }
    }

    if (firstPlayer.isServing) {
      val players: (Player, Player, Boolean) = onServe(firstPlayer, secondPlayer, shouldServeFail)
      firstPlayer = players._1
      secondPlayer = players._2
      players._3
    } else {
      val players: (Player, Player, Boolean) = onServe(secondPlayer, firstPlayer, shouldServeFail)
      secondPlayer = players._1
      firstPlayer = players._2
      players._3
    }
  }

  def passTheBall(shouldPassFail: Boolean): Boolean = {
    val isFirstPlayerHasTheBall: Boolean = firstPlayer.hasBall
    if (shouldPassFail) {
      firstPlayer = Player(
        firstPlayer,
        isServing = firstPlayer.isServing,
        incrementScore = !isFirstPlayerHasTheBall,
        incrementPoints = !isFirstPlayerHasTheBall,
        hasBall = firstPlayer.isServing,
        opponentScore = secondPlayer.score,
        decrementScore = isFirstPlayerHasTheBall
      )
      secondPlayer = Player(
        secondPlayer,
        isServing = secondPlayer.isServing,
        incrementScore = isFirstPlayerHasTheBall,
        incrementPoints = isFirstPlayerHasTheBall,
        hasBall = secondPlayer.isServing,
        opponentScore = firstPlayer.score,
        decrementScore = !isFirstPlayerHasTheBall
      )
      false
    } else {
      firstPlayer = Player(
        firstPlayer,
        isServing = firstPlayer.isServing,
        hasBall = !isFirstPlayerHasTheBall,
        opponentScore = secondPlayer.score
      )
      secondPlayer = Player(
        secondPlayer,
        isServing = secondPlayer.isServing,
        hasBall = isFirstPlayerHasTheBall,
        opponentScore = firstPlayer.score
      )
      true
    }
  }

  def isGameFinished: Boolean = firstPlayer.points._1 == MATCH || secondPlayer.points._1 == MATCH
  def winner: Player = if (firstPlayer.points._1 == MATCH) firstPlayer else secondPlayer
  def players: (Player, Player) = if (firstPlayer.hasBall) (firstPlayer, secondPlayer) else (secondPlayer, firstPlayer)
}
