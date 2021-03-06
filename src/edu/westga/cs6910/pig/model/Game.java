package edu.westga.cs6910.pig.model;

import java.util.Random;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Game represents a Pig game.
 * 
 * This class was started by CS6910
 * 
 * @author Spencer Dent
 * @version 2021-06-09
 */
public class Game implements Observable {
	/**
	 * The goal score for the game
	 */
	public static final int GOAL_SCORE = 20;

	private ObjectProperty<Player> currentPlayerObject;
	private HumanPlayer theHuman;
	private ComputerPlayer theComputer;

	private Player firstPlayer;
	private BooleanProperty gameOverIndicator;

	private DicePair thePair;

	/**
	 * Creates a Pig Game with the specified Players and a pair of dice.
	 * 
	 * @param theComputer the automated player
	 * @param theHuman    the human player
	 * 
	 * @require theHuman != null && theComputer != null
	 * 
	 * @ensure humanPlayer().equals(theHuman) && computerPlayer.equals(theComputer)
	 */
	public Game(HumanPlayer theHuman, ComputerPlayer theComputer) {
		this.theHuman = theHuman;
		this.theComputer = theComputer;

		this.currentPlayerObject = new SimpleObjectProperty<Player>();
		this.gameOverIndicator = new SimpleBooleanProperty();

		this.thePair = new DicePair();
	}

	/**
	 * Returns the Player the Game was started with.
	 * 
	 * @return firstPlayer
	 */
	public Player getFirstPlayer() {
		return this.firstPlayer;
	}

	/**
	 * Initializes the game for play.
	 * 
	 * @param firstPlayer the Player who takes the first turn
	 * 
	 * @require firstPlayer != null && !firstPlayer.equals(secondPlayer)
	 * 
	 * @ensures whoseTurn().equals(firstPlayer) && firstPlayer.getTotal() == 0
	 */
	public void startNewGame(Player firstPlayer) {
		this.firstPlayer = firstPlayer;
		this.gameOverIndicator.setValue(false);

		this.resetDice();
		this.resetScores();

		this.currentPlayerObject.setValue(null);
		this.currentPlayerObject.setValue(firstPlayer);

		this.thePair = new DicePair();
	}

	/**
	 * Initializes the game for play. Selecting a random player to start
	 */
	public void startNewGameWithRandomFirstPlayer() {
		boolean shouldStartWithHuman = (new Random()).nextBoolean();

		if (shouldStartWithHuman) {
			this.startNewGame(this.theHuman);
		} else {
			this.startNewGame(this.theComputer);
		}
	}

	/**
	 * Initializes the game for play, starting with the first player from the
	 * previous round.
	 */
	public void startNewGameWithPreviousFirstPlayer() {
		this.startNewGame(this.firstPlayer);
	}

	/**
	 * Conducts a move in the game, allowing the appropriate Player to take a turn.
	 * Has no effect if the game is over.
	 * 
	 * @requires !isGameOver()
	 * 
	 * @ensures !whoseTurn().equals(whoseTurn()@prev)
	 */
	public void play() {
		Player currentPlayer = this.currentPlayerObject.getValue();
		this.currentPlayerObject.getValue().takeTurn();

		this.currentPlayerObject.setValue(null);
		this.currentPlayerObject.setValue(currentPlayer);

		if (!this.currentPlayerObject.getValue().getIsMyTurn()) {
			this.hold();
			this.currentPlayerObject.getValue().resetTurnTotal();
		}
	}

	/**
	 * Notifies the game that the player is holding
	 * 
	 * @requires !isGameOver()
	 * 
	 * @ensures !whoseTurn().equals(whoseTurn()@prev)
	 */
	public void hold() {
		if (!this.isGameOver()) {
			this.swapWhoseTurn();
		}
	}

	/**
	 * Returns the human Player object.
	 * 
	 * @return the human Player
	 */
	public HumanPlayer getHumanPlayer() {
		return this.theHuman;
	}

	/**
	 * Returns the computer Player object.
	 * 
	 * @return the computer Player
	 */
	public ComputerPlayer getComputerPlayer() {
		return this.theComputer;
	}

	/**
	 * Returns the Player whose turn it is.
	 * 
	 * @return the current Player
	 */
	public Player getCurrentPlayer() {
		return this.currentPlayerObject.getValue();
	}

	/**
	 * Retrieves the gameOverIndicator boolean property.
	 * 
	 * @return gameOverIndicator - indicates if the game is over
	 */
	public BooleanProperty getGameOverIndicator() {
		return this.gameOverIndicator;
	}

	/**
	 * Return whether the game is over.
	 * 
	 * @return true iff currentPlayer.getTotal() >= GOAL_SCORE
	 */
	public boolean isGameOver() {
		if (this.currentPlayerObject.getValue() == null) {
			return true;
		}

		if (this.theHuman.getTotal() >= GOAL_SCORE || this.theComputer.getTotal() >= GOAL_SCORE) {
			this.gameOverIndicator.setValue(true);
			return true;
		}

		return false;
	}

	/**
	 * Returns the pair of dice being used in the game
	 * 
	 * @return the pair of dice
	 */
	public DicePair getDicePair() {
		return this.thePair;
	}

	/**
	 * Returns a String showing the goal score, or, if the game is over, the name of
	 * the winner.
	 * 
	 * @return a String representation of this Game
	 */
	public String toString() {
		String result = "Goal Score: " + GOAL_SCORE;
		result += System.getProperty("line.separator") + this.theHuman.getName() + ": " + this.theHuman.getTotal();
		result += System.getProperty("line.separator") + this.theComputer.getName() + ": "
				+ this.theComputer.getTotal();

		if (this.theHuman.getTotal() >= GOAL_SCORE) {
			return result + System.getProperty("line.separator") + "Game over! Winner: " + this.theHuman.getName();
		} else if (this.theComputer.getTotal() >= GOAL_SCORE) {
			return result + System.getProperty("line.separator") + "Game over! Winner: " + this.theComputer.getName();
		} else {
			return result;
		}
	}

	/**
	 * Resets the scores of all the players.
	 */
	public void resetScores() {
		this.theComputer.resetTurnTotal();
		this.theComputer.setTotal(0);

		this.theHuman.resetTurnTotal();
		this.theHuman.setTotal(0);
	}

	/**
	 * Resets the pair of dice to its original state.
	 */
	public void resetDice() {
		this.theHuman.getThePair().reset();
		this.theHuman.clearDiceValues();

		this.theComputer.getThePair().reset();
		this.theComputer.clearDiceValues();

		this.thePair.reset();
	}

	/**
	 * Swaps the players so that the other player becomes the current player.
	 */
	private void swapWhoseTurn() {
		
		if (this.currentPlayerObject.getValue().equals(this.theHuman)) {
			this.theComputer.clearDiceValues();
			this.currentPlayerObject.setValue(this.theComputer);
		} else {
			this.theHuman.clearDiceValues();
			this.currentPlayerObject.setValue(this.theHuman);
		}
	}

	@Override
	public void addListener(InvalidationListener theListener) {
		this.currentPlayerObject.addListener(theListener);
	}

	@Override
	public void removeListener(InvalidationListener theListener) {
		this.currentPlayerObject.removeListener(theListener);
	}

}
