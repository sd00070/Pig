package edu.westga.cs6910.pig.model.strategies;

import java.util.Random;

/**
 * This strategy causes the player to randomly decide whether or not they will
 * roll again.
 * 
 * If they have already reached the goal, they do not.
 * 
 * @author Spencer Dent
 * @version 2021-06-22
 */
public class RandomStrategy implements PigStrategy {

	/**
	 * Randomly decides whether or not the player should roll again.
	 * 
	 * @return random boolean
	 */
	@Override
	public boolean rollAgain(int numberOfRollsTaken, int numberOfPointsRolled, int totalAndGoalScoreDifference) {
		if (totalAndGoalScoreDifference <= 0) {
			return false;
		}
		
		return new Random().nextBoolean();
	}

}
