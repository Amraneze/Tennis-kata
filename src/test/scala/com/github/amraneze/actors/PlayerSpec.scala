package com.github.amraneze.actors

import com.github.amraneze.entities.{ADVANTAGE, FIFTEEN, FORTY, ZERO}
import com.github.amraneze.helpers.Helper
import com.github.amraneze.entities.{ADVANTAGE, FIFTEEN, FORTY, ZERO}
import org.scalatest.Inside.inside

class PlayerSpec extends Helper {

  it should "increment player's score and points" in {
    val newPlayer: Player = Player(player,
                                   isServing = true,
                                   incrementScore = true,
                                   incrementPoints = true,
                                   hasBall = true,
                                   opponentScore = opponent.score)
    newPlayer.hasBall shouldBe true
    newPlayer.isServing shouldBe true
    newPlayer.score should be(FIFTEEN)
    inside(newPlayer.points) {
      case (points, counter, gamesCounter) =>
        points should be(ZERO)
        counter should be(0)
        gamesCounter should be(0)
    }
  }

  it should "increment player's score to Advantage" in {
    val _player: Player = generateRandomPlayer(score = FORTY)
    val _opponent: Player = generateRandomPlayer(score = FORTY)
    val newPlayer: Player = Player(_player,
                                   isServing = true,
                                   incrementScore = true,
                                   incrementPoints = true,
                                   hasBall = true,
                                   opponentScore = _opponent.score)

    newPlayer.hasBall shouldBe true
    newPlayer.isServing shouldBe true
    newPlayer.score should be(ADVANTAGE)
    inside(newPlayer.points) {
      case (points, counter, gamesCounter) =>
        points should be(ZERO)
        counter should be(0)
        gamesCounter should be(0)
    }
  }

  it should "decrement player's score and points when the player is in advantage and made a mistake" in {
    val _player: Player = generateRandomPlayer(score = ADVANTAGE)
    val newPlayer: Player = Player(_player, isServing = false, decrementScore = true, opponentScore = opponent.score)
    newPlayer.hasBall shouldBe false
    newPlayer.isServing shouldBe false
    newPlayer.score should be(FORTY)
    inside(newPlayer.points) {
      case (points, counter, gamesCounter) =>
        points should be(ZERO)
        counter should be(0)
        gamesCounter should be(0)
    }
  }
}
