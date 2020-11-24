package com.github.amraneze

import com.github.amraneze.services.TennisService
import com.github.javafaker.Faker
import com.github.amraneze.actors.Player
import com.github.amraneze.entities.{LOVE, ZERO}
import com.github.amraneze.utils.Logging

object Game extends App with Logging {

	lazy val faker: Faker = new Faker()
	private val firstPlayer: Player = actors.Player(faker.name().name(), isServing = true, LOVE, (ZERO, 0, 0), hasBall = true)
	private val secondPlayer: Player = actors.Player(faker.name().name(), isServing = false, LOVE, (ZERO, 0, 0), hasBall = false)
	val service: TennisService = services.TennisService(firstPlayer, secondPlayer)

	while (!service.isGameFinished) {
		val isServed: Boolean = service.serve(faker.random().nextBoolean())
		logger.debug(s"The ball is ${if (isServed) "served" else "not served"}")
		if (isServed) {
			while (service.passTheBall(faker.random().nextBoolean())) {
				val players: (Player, Player) = service.players
				logger.debug(s"Passing the ball from {} to {}", players._1, players._2)
			}
		}
	}

	val winner: Player = service.winner
	logger.debug(s"And the winner is {}", winner)

}
