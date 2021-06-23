package edu.westga.cs6910.pig.model.strategies;

/**
 * This strategy causes the player to roll until they have rolled 3 times.
 * 
 * @author Spencer Dent
 * @version 2021-06-22
 */
public class GreedyStrategy implements PigStrategy {

	/**
	 * Returns true until the player has rolled at least 3 times.
	 */
	@Override
	public boolean rollAgain(int numberOfRollsTaken, int numberOfPointsRolled, int totalAndGoalScoreDifference) {
		if (numberOfRollsTaken >= 3) {
			return false;
		}
		return true;
	}

}
