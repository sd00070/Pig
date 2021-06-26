package edu.westga.cs6910.pig.view;

import edu.westga.cs6910.pig.model.Game;
import edu.westga.cs6910.pig.model.Player;
import edu.westga.cs6910.pig.model.strategies.CautiousStrategy;
import edu.westga.cs6910.pig.model.strategies.GreedyStrategy;
import edu.westga.cs6910.pig.model.strategies.PigStrategy;
import edu.westga.cs6910.pig.model.strategies.RandomStrategy;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * Defines a GUI for the Pig game.
 * 
 * This class was started by CS6910
 * 
 * @author Spencer Dent
 * @version 2021-06-08
 */
public class PigPane extends BorderPane {
	private Game theGame;
	private BorderPane pnContent;
	private HumanPane pnHumanPlayer;
	private ComputerPane pnComputerPlayer;
	private StatusPane pnGameInfo;
	private Pane pnChooseFirstPlayer;

	/**
	 * Creates a pane object to provide the view for the specified Game model
	 * object.
	 * 
	 * @param theGame the domain model object representing the Pig game
	 * 
	 * @requires theGame != null
	 * @ensures the pane is displayed properly
	 */
	public PigPane(Game theGame) {
		this.theGame = theGame;

		this.pnContent = new BorderPane();

		this.addFirstPlayerChooserPane(theGame);

		this.addHumanPlayerPane(theGame);

		this.addStatusPane(theGame);

		this.addComputerPlayerPane(theGame);

		this.setCenter(this.pnContent);

		this.addMenuBar();
	}

	private void addFirstPlayerChooserPane(Game theGame) {
		HBox topBox = new HBox();
		topBox.getStyleClass().add("pane-border");
		this.pnChooseFirstPlayer = new NewGamePane(theGame);
		topBox.getChildren().add(this.pnChooseFirstPlayer);
		this.pnContent.setTop(topBox);
	}

	private void addHumanPlayerPane(Game theGame) {
		HBox leftBox = new HBox();
		leftBox.getStyleClass().add("pane-border");
		this.pnHumanPlayer = new HumanPane(theGame);
		this.pnHumanPlayer.setDisable(true);
		leftBox.getChildren().add(this.pnHumanPlayer);
		this.pnContent.setLeft(leftBox);
	}

	private void addStatusPane(Game theGame) {
		HBox centerBox = new HBox();
		centerBox.getStyleClass().add("pane-border");
		this.pnGameInfo = new StatusPane(theGame);
		centerBox.getChildren().add(this.pnGameInfo);
		this.pnContent.setCenter(centerBox);
	}

	private void addComputerPlayerPane(Game theGame) {
		HBox rightBox = new HBox();
		rightBox.getStyleClass().add("pane-border");
		this.pnComputerPlayer = new ComputerPane(theGame);
		this.pnComputerPlayer.setDisable(true);
		rightBox.getChildren().add(this.pnComputerPlayer);
		this.pnContent.setRight(rightBox);
	}

	private MenuItem createExitMenuItem() {
		MenuItem exitMenuItem = new MenuItem("E_xit");
		exitMenuItem.setMnemonicParsing(true);
		exitMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.SHORTCUT_DOWN));
		exitMenuItem.setOnAction(actionEvent -> System.exit(0));
		return exitMenuItem;
	}

	private RadioMenuItem createCautiousMenuItem(ToggleGroup strategiesToggleGroup) {
		RadioMenuItem cautiousMenuItem = this.createStrategyMenuItem("_Cautious", KeyCode.C, new CautiousStrategy(), strategiesToggleGroup);
		cautiousMenuItem.setSelected(true);
		return cautiousMenuItem;
	}

	private RadioMenuItem createGreedyMenuItem(ToggleGroup strategiesToggleGroup) {
		return this.createStrategyMenuItem("Gr_eedy", KeyCode.E, new GreedyStrategy(), strategiesToggleGroup);
	}

	private RadioMenuItem createRandomMenuItem(ToggleGroup strategiesToggleGroup) {
		return this.createStrategyMenuItem("_Random", KeyCode.R, new RandomStrategy(), strategiesToggleGroup);
	}

	private RadioMenuItem createStrategyMenuItem(String label, KeyCode accelerator, PigStrategy strategy,
			ToggleGroup strategiesToggleGroup) {
		RadioMenuItem randomMenuItem = new RadioMenuItem(label);
		randomMenuItem.setMnemonicParsing(true);
		randomMenuItem.setAccelerator(new KeyCodeCombination(accelerator, KeyCombination.SHORTCUT_DOWN));
		randomMenuItem.setOnAction(actionEvent -> this.theGame.getComputerPlayer().setStrategy(strategy));
		randomMenuItem.setToggleGroup(strategiesToggleGroup);
		return randomMenuItem;
	}

	private Menu createStrategyMenu() {
		Menu strategyMenu = new Menu("_Strategy");
		strategyMenu.setMnemonicParsing(true);

		ToggleGroup strategiesToggleGroup = new ToggleGroup();

		strategyMenu.getItems().addAll(this.createCautiousMenuItem(strategiesToggleGroup),
				this.createGreedyMenuItem(strategiesToggleGroup), this.createRandomMenuItem(strategiesToggleGroup));
		return strategyMenu;
	}

	private void addMenuBar() {
		MenuBar menuBar = new MenuBar();
		menuBar.prefWidthProperty().bind(this.widthProperty());
		this.setTop(menuBar);

		Menu gameMenu = new Menu("_Game");
		gameMenu.setMnemonicParsing(true);

		gameMenu.getItems().add(this.createExitMenuItem());

		menuBar.getMenus().addAll(gameMenu, this.createStrategyMenu());
	}

	/**
	 * Defines the panel in which the user selects which Player plays first.
	 */
	private final class NewGamePane extends GridPane {
		private RadioButton radHumanPlayer;
		private RadioButton radComputerPlayer;

		private Game theGame;
		private Player theHuman;
		private Player theComputer;

		private NewGamePane(Game theGame) {
			this.theGame = theGame;

			this.theHuman = this.theGame.getHumanPlayer();
			this.theComputer = this.theGame.getComputerPlayer();

			this.buildPane();
		}

		private void buildPane() {
			this.setHgap(20);

			this.radHumanPlayer = new RadioButton(this.theHuman.getName() + " first");
			this.radHumanPlayer.setOnAction(actionEvent -> {
				PigPane.this.pnChooseFirstPlayer.setDisable(true);
				
				PigPane.this.pnHumanPlayer.setDisable(false);
				
				PigPane.this.theGame.startNewGame(NewGamePane.this.theHuman);
			});

			this.radComputerPlayer = new RadioButton(this.theComputer.getName() + " first");
			this.radComputerPlayer.setOnAction(actionEvent -> {
				PigPane.this.pnChooseFirstPlayer.setDisable(true);
				
				PigPane.this.pnComputerPlayer.setDisable(false);
				
				PigPane.this.theGame.startNewGame(NewGamePane.this.theComputer);
			});

			ToggleGroup firstPlayerRadioButtonToggleGroup = new ToggleGroup();
			firstPlayerRadioButtonToggleGroup.getToggles().addAll(this.radHumanPlayer, this.radComputerPlayer);

			this.add(this.radHumanPlayer, 0, 0);
			this.add(this.radComputerPlayer, 1, 0);
		}
	}
}
