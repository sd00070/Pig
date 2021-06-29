package edu.westga.cs6910.pig.view.menus;

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
	/**
	 * The constructor, creates the Menu and calls private helper functions to add
	 * the MenuItems.
	 */
	public GameMenu() {
		super("_Game");
		this.setMnemonicParsing(true);
		
		this.getItems().addAll(this.createExitMenuItem());
	}
	
	private MenuItem createExitMenuItem() {
		MenuItem exitMenuItem = new MenuItem("E_xit");
		exitMenuItem.setMnemonicParsing(true);
		exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		exitMenuItem.setOnAction(actionEvent -> System.exit(0));
		return exitMenuItem;
	}
}