package com.github.amraneze.services

import com.github.amraneze.helpers.Helper
import com.github.amraneze.actors.Player
import com.github.amraneze.entities.{ADVANTAGE, FORTY, GAME, LOVE, Score, THIRTY}
import org.scalatest.Inside.inside

class TennisServiceSpec extends Helper {

  it should "serve the ball to the opponent" in {
    val service: TennisService = TennisService(player, opponent)
    service.serve(shouldServeFail = false)
    inside(service.players) {
      case (playerWithBall: Player, playerWithout: Player) => {
        playerWithBall.hasBall shouldBe true
         playerWithBall.isServing shouldBe false
         playerWithBall.name should be(opponent.name)
        // the opponent now has the ball
        playerWithBall.score should be(opponent.score)
        playerWithBall.points should be(opponent.points)

        playerWithout.hasBall shouldBe false
         playerWithout.isServing shouldBe true
         playerWithout.name should be(player.name)
        // the player shouldn't have the ball
        playerWithout.score should be(player.score)
        playerWithout.points should be(player.points)
      }
    }
  }

  it should "not serve the ball to the opponent and the opponent won't gain a point" in {
    val service: TennisService = TennisService(player, opponent)
    service.serve(shouldServeFail = true)
    inside(service.players) {
      case (playerWithBall: Player, playerWithout: Player) => {
         playerWithBall.hasBall shouldBe true
         playerWithBall.isServing shouldBe true
        // the player has the ball, because he didn't serve the ball good
        playerWithBall.name should be(player.name)
        playerWithBall.score should be(player.score)
        playerWithBall.points should be(player.points)

         playerWithout.hasBall shouldBe false
         playerWithout.isServing shouldBe false
        playerWithout.name should be(opponent.name)
        playerWithout.score should be(opponent.score)
        playerWithout.points should be(opponent.points)
      }
    }
  }

  it should "give the opponent a point when the player misses two times" in {
    val service: TennisService = TennisService(player, opponent)
    service.serve(shouldServeFail = true)
    service.serve(shouldServeFail = true)
    inside(service.players) {
      case (playerWithBall: Player, playerWithout: Player) => {
        val newScore = Score.incrementScore(opponent.score, player.score)
        playerWithBall.name should be(opponent.name)
        playerWithBall.score should be(newScore.left.getOrElse(LOVE))
        playerWithBall.points should be(opponent.points)

        playerWithout.name should be(player.name)
        playerWithout.score should be(player.score)
        playerWithout.points should be(player.points)
      }
    }
  }


	it should "handle the case of Deuce" in {
	    val newPlayer: Player = generateRandomPlayer(score = THIRTY, hasBall = true, isServing = true)
	    val newOpponent: Player = generateRandomPlayer(score = THIRTY)
		val service: TennisService = TennisService(newPlayer, newOpponent)

		service.serve(shouldServeFail = true)
		service.serve(shouldServeFail = true)
	    // now newOpponent should have score of 40 and the ball
		service.serve(shouldServeFail = true)
		service.serve(shouldServeFail = true)
		// now the player should have score of 40 and the ball

		inside(service.players) {
			case (playerWithBall: Player, playerWithout: Player) => {
				playerWithBall.name should be(newPlayer.name)
				playerWithBall.score should be(FORTY)
				playerWithBall.points should be(newPlayer.points)

				playerWithout.name should be(newOpponent.name)
				playerWithout.score should be(FORTY)
				playerWithout.points should be(newOpponent.points)
			}
		}

	    // Now, player will win a point to be in Advantage
		service.serve(shouldServeFail = false)
	    service.passTheBall(shouldPassFail = true)

		inside(service.players) {
			case (playerWithBall: Player, playerWithout: Player) => {
				playerWithBall.name should be(newPlayer.name)
				playerWithBall.score should be(ADVANTAGE)
				playerWithBall.points should be(newPlayer.points)

				playerWithout.name should be(newOpponent.name)
				playerWithout.score should be(FORTY)
				playerWithout.points should be(newOpponent.points)
			}
		}

		// Now, player will lose a point to return to Forty (Deuce) and the opponent will serve the ball
		service.serve(shouldServeFail = true)
		service.serve(shouldServeFail = true)

		inside(service.players) {
			case (playerWithBall: Player, playerWithout: Player) => {
				playerWithBall.name should be(newOpponent.name)
				playerWithBall.score should be(FORTY)
				playerWithBall.points should be(newOpponent.points)

				playerWithout.name should be(newPlayer.name)
				playerWithout.score should be(FORTY)
				playerWithout.points should be(newPlayer.points)
			}
		}

		// Now, opponent will win a point and be in Advantage and should win a game after a second point
		service.serve(shouldServeFail = false)
		service.passTheBall(shouldPassFail = true)

		service.serve(shouldServeFail = false)
		service.passTheBall(shouldPassFail = true)

		inside(service.players) {
			case (playerWithBall: Player, playerWithout: Player) =>
				playerWithBall.name should be(newOpponent.name)
				playerWithBall.score should be(LOVE)
				playerWithBall.points should be(GAME, 1, 0)

				playerWithout.name should be(newPlayer.name)
				playerWithout.score should be(FORTY)
				playerWithout.points should be(newPlayer.points)
		}
		// I need to test the case when the player is in Advantage
	    // and the opponent won a point, they should return to Deuce
	}
}
