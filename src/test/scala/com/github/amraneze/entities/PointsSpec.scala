package com.github.amraneze.entities

import Points.CounterPoints
import com.github.amraneze.helpers.Helper
import org.scalatest.Inside.inside

class PointsSpec extends Helper {

	it should "increment a zero point to a Game" in {
		val points: CounterPoints = generateRandomPoints(ZERO)
		val newPoints: CounterPoints = generateRandomPoints(GAME)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		inside(result) {
			case (point, _, _) =>
				point should be(GAME)
		}
	}

	it should "increment a point to a Game" in {
		val points: CounterPoints = (GAME, randomIntInRange(0, 4), 0)
		val newPoints: CounterPoints = (GAME, 1, 0)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		inside(result) {
			case (point, counter, _) =>
				point should be(GAME)
				counter should be(points._2 + newPoints._2 + 1)
		}
	}

	it should "increment a 6 Games to a Set" in {
		val points: CounterPoints = (GAME, 5, 0)
		val newPoints: CounterPoints = (GAME, 1, 0)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		inside(result) {
			case (point, counter, _) =>
				point should be(SET)
				counter should be(1)
		}
	}

	it should "increment the Games counter within a Set" in {
		val points: CounterPoints = (SET, 1, randomIntInRange(0, 4))
		val newPoints: CounterPoints = (GAME, 1, 0)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		inside(result) {
			case (point, counter, gamesCounter) =>
				point should be(SET)
				counter should be(1)
				gamesCounter should be(points._3 + 1)
		}
	}

	it should "increment a Set to 2 Sets in order to win a Match" in {
		val points: CounterPoints = (SET, 1, 5)
		val newPoints: CounterPoints = (GAME, 1, 0)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		inside(result) {
			case (point, counter, _) =>
				point should be(MATCH)
				counter should be(1)
		}
	}

	it should "not increase the points if the case is not handled" in {
		val points: CounterPoints = (SET, 1, randomIntInRange(0, 4))
		val newPoints: CounterPoints = (ZERO, randomIntInRange(1, 4), 0)
	    val result: CounterPoints = Points.incrementPoints(points, newPoints)
		result should be(points)
	}

}
