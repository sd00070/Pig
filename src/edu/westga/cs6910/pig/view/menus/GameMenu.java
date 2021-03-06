package edu.westga.cs6910.pig.view.menus;

import edu.westga.cs6910.pig.model.Game;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Creates the Game Menu for the PigMenuBar.
 * 
 * @author Spencer Dent
 * @version 2021-06-29
 */
public class GameMenu extends Menu {

	private Game theGame;

	private MenuItem exitMenuItem;
	private MenuItem newGameMenuItem;
	
	/**
	 * The constructor, creates the Menu and calls private helper functions to add
	 * the MenuItems.
	 * 
	 * @param theGame - the Pig Game model
	 */
	public GameMenu(Game theGame) {
		super("_Game");
		this.setMnemonicParsing(true);

		this.theGame = theGame;

		this.newGameMenuItem = new GameMenuItem("_New Game", KeyCode.N,
		  actionEvent -> this.theGame.startNewGameWithPreviousFirstPlayer());
		this.newGameMenuItem.setDisable(true);
		
		this.exitMenuItem = new GameMenuItem("E_xit", KeyCode.X, actionEvent -> System.exit(0));

		this.getItems().addAll(this.newGameMenuItem, this.exitMenuItem);
	}
	
	/**
	 * Toggles whether the NewGameMenuItem is enabled. To be used after the game has started.
	 */
	public void toggleEnabledNewGameMenuItem() {
		this.newGameMenuItem.setDisable(!this.newGameMenuItem.isDisable());
	}

	private class GameMenuItem extends MenuItem {
		GameMenuItem(String itemLabel, KeyCode acceleratorKey, EventHandler<ActionEvent> onActionHandler) {
			super(itemLabel);
			this.setMnemonicParsing(true);
			this.setAccelerator(new KeyCodeCombination(acceleratorKey, KeyCombination.SHORTCUT_DOWN));
			this.setOnAction(onActionHandler);
		}
	}
}
