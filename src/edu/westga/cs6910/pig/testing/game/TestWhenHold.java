package edu.westga.cs6910.pig.testing.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.westga.cs6910.pig.model.ComputerPlayer;
import edu.westga.cs6910.pig.model.strategies.CautiousStrategy;
import edu.westga.cs6910.pig.model.Game;
import edu.westga.cs6910.pig.model.HumanPlayer;

/**
 * Provides unit testing to verify the expected behavior of Game's hold method.
 * 
 * @author Spencer Dent
 * @version 2021-06-16
 */
public class TestWhenHold {

	/**
	 * When called on a Game with a HumanPlayer currentPlayer, it should change the
	 * currentPlayer to the ComputerPlayer.
	 */
	@Test
	public void testWithHumanCurrentPlayerShouldsetCurrentPlayerToComputerPlayer() {
		HumanPlayer testHumanPlayer = new HumanPlayer("David Lightman");
		ComputerPlayer testComputerPlayer = new ComputerPlayer(new CautiousStrategy());
		Game testGame = new Game(testHumanPlayer, testComputerPlayer);
		testGame.startNewGame(testHumanPlayer);
		testGame.hold();
		assertEquals(testComputerPlayer, testGame.getCurrentPlayer());
	}

	/**
	 * When called on a Game with a ComputerPlayer currentPlayer, it should change
	 * the currentPlayer to the HumanPlayer.
	 */
	@Test
	public void testWithComputerCurrentPlayerShouldsetCurrentPlayerToHumanPlayer() {
		HumanPlayer testHumanPlayer = new HumanPlayer("David Lightman");
		ComputerPlayer testComputerPlayer = new ComputerPlayer(new CautiousStrategy());
		Game testGame = new Game(testHumanPlayer, testComputerPlayer);
		testGame.startNewGame(testComputerPlayer);
		testGame.hold();
		assertEquals(testHumanPlayer, testGame.getCurrentPlayer());
	}

	/**
	 * When swapping over to the HumanPlayer after the ComputerPlayer's turn, hold
	 * should clear the dice values of the HumanPlayer.
	 */
	@Test
	public void testWhenSwapToHumanPlayerShouldClearHumanPlayerDiceValues() {
		HumanPlayer testHumanPlayer = new HumanPlayer("test");
		ComputerPlayer testComputerPlayer = new ComputerPlayer(new CautiousStrategy());
		Game testGame = new Game(testHumanPlayer, testComputerPlayer);
		testGame.startNewGame(testComputerPlayer);

		testHumanPlayer.rollDice();
		testHumanPlayer.rollDice();
		testHumanPlayer.rollDice();

		testGame.hold();

		assertEquals(0, testHumanPlayer.getDiceValues().size());
	}

	/**
	 * When swapping over to the ComputerPlayer after the HumanPlayer's turn, hold
	 * should clear the dice values of the ComputerPlayer.
	 */
	@Test
	public void testWhenSwapToComputerPlayerShouldClearComputerPlayerDiceValues() {
		HumanPlayer testHumanPlayer = new HumanPlayer("test");
		ComputerPlayer testComputerPlayer = new ComputerPlayer(new CautiousStrategy());
		Game testGame = new Game(testHumanPlayer, testComputerPlayer);
		testGame.startNewGame(testHumanPlayer);

		testComputerPlayer.rollDice();
		testComputerPlayer.rollDice();
		testComputerPlayer.rollDice();

		testGame.hold();

		assertEquals(0, testComputerPlayer.getDiceValues().size());
	}
}
