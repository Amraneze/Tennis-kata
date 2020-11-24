package com.github.amraneze.entities

import com.github.amraneze.helpers.Helper
import org.scalatest.Inside.inside

class ScoreSpec extends Helper {

	it should "increment a score from Love to 15" in {
		val score: Either[Score, Points] = Score.incrementScore(LOVE, LOVE)
		inside(score) {
			case Left(result) => result should be(FIFTEEN)
		}
	}

	it should "increment a score from 15 to 30" in {
		val score: Either[Score, Points] = Score.incrementScore(FIFTEEN, LOVE)
		inside(score) {
			case Left(result) => result should be(THIRTY)
		}
	}

	it should "increment a score from 30 to 40" in {
		val score: Either[Score, Points] = Score.incrementScore(THIRTY, LOVE)
		inside(score) {
			case Left(result) => result should be(FORTY)
		}
	}

	it should "increment from 40 to a Game if the opponent's score is not Forty" in {
		val score: Either[Score, Points] = Score.incrementScore(FORTY, LOVE)
		inside(score) {
			case Right(result) => result should be(GAME)
		}
	}

	it should "increment from 40 to a Advantage if the opponent's score is Forty" in {
		val score: Either[Score, Points] = Score.incrementScore(FORTY, FORTY)
		inside(score) {
			case Left(result) => result should be(ADVANTAGE)
		}
	}

	it should "increment from Advantage to a game" in {
		val score: Either[Score, Points] = Score.incrementScore(ADVANTAGE, FORTY)
		inside(score) {
			case Right(result) => result should be(GAME)
		}
	}

	it should "decrement the score from Advantage to Forty" in {
		val score: Either[Score, Points] = Score.decrementScore(ADVANTAGE)
		inside(score) {
			case Left(result) => result should be(FORTY)
		}
	}

	it should "not decrement the score if the current score is not Advantage" in {
		inside(Score.decrementScore(LOVE)) {
			case Left(result) => result should be(LOVE)
		}

		inside(Score.decrementScore(FIFTEEN)) {
			case Left(result) => result should be(FIFTEEN)
		}

		inside(Score.decrementScore(THIRTY)) {
			case Left(result) => result should be(THIRTY)
		}

		inside(Score.decrementScore(FORTY)) {
			case Left(result) => result should be(FORTY)
		}
	}

}
