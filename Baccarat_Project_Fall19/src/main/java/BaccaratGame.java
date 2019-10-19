import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;

	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;

	String choice;
	double currentBet;
	double totalWinnings;
	boolean playerWin;

	MenuBar menuBar;
	MenuItem frshStart;
	HashMap<String, Scene> sceneMap;

	// Left VBox
	TextField betMoney;
	Button startBtn;
	ToggleButton PlayerButt, BankerButt, TieButt;
	ToggleGroup toggleGrp;
	HBox betChoices;
	Button playBtn;
	TextArea result;
	TextField currWinnings;

	// Right VBox
	ImageView bankerCard1;
	ImageView bankerCard2;
	ImageView bankerCard3;
	ImageView playerCard1;
	ImageView playerCard2;
	ImageView playerCard3;
	HBox bankerPos;
	HBox playerPos;

	// evaluateWinnings calculate the player's amount of totalWinnings after the end of a game
	public double evaluateWinnings() {
		double resultWinnings = 0.0;
		if (playerWin) {
			if (choice.equals("Player"))
				resultWinnings = totalWinnings + currentBet * 2;
			else if (choice.equals("Banker"))
				resultWinnings = totalWinnings + currentBet * 1.95;
			else
				resultWinnings = totalWinnings + currentBet * 8;
			if (totalWinnings > resultWinnings) // deals with overflow
				resultWinnings = Double.MAX_VALUE;
		} else {
			resultWinnings = totalWinnings - currentBet;
			if (totalWinnings < resultWinnings) // deals with overflow
				resultWinnings = Double.MIN_VALUE;
		}
		totalWinnings = resultWinnings;

		return totalWinnings;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Let's Play Baccarat!!!");

		theDealer = new BaccaratDealer();
		gameLogic = new BaccaratGameLogic();
		sceneMap = new HashMap<String, Scene>();
		result = new TextArea();
		menuBar = new MenuBar();
		choice = "";
		initMenu(primaryStage);
		startGame(primaryStage);
	}

	private void startGame(Stage primaryStage) {
		totalWinnings = 0.0;
		result.clear();
		sceneMap.put("scene", mainScene());

		primaryStage.setScene(sceneMap.get("scene"));
		primaryStage.show();
	}

	// initMenu initializes a menu with two menu items: freshstart and exit, and add into menuBar
	public void initMenu(Stage primaryStage) {
		Menu mainMenu = new Menu();
		mainMenu.setText("Options");
		frshStart = new MenuItem();
		MenuItem exitItm = new MenuItem();

		frshStart.setText("Fresh Start");
		frshStart.setOnAction(e -> {
			// Reset game
			startGame(primaryStage);
		});

		exitItm.setText("Exit");
		exitItm.setOnAction(e -> {
			Platform.exit();
		});

		mainMenu.getItems().addAll(frshStart, exitItm);
		menuBar.getMenus().add(mainMenu);
	}

	public Scene mainScene() {
		BorderPane pane = new BorderPane();
		pane.setTop(menuBar);

		VBox selection = initLeftVBox();
		GridPane game = initRightGrid();
		Text displayWinnings = new Text("Total Winnings: $");

		displayWinnings.setFont(Font.font("Algerian", FontWeight.EXTRA_BOLD, 30));
		displayWinnings.setFill(Color.web("#ffc400"));
		currWinnings = new TextField();
		currWinnings.setEditable(false);
		currWinnings.setText(Double.toString(totalWinnings));
		HBox winningBar = new HBox(displayWinnings,currWinnings);
		winningBar.setAlignment(Pos.CENTER);
		winningBar.setPadding(new Insets(10,0,0,0));
		VBox twPos = new VBox(winningBar,game);
		pane.setLeft(selection);
		pane.setCenter(twPos);
		pane.setStyle("-fx-background-color: #00695c;");

		return new Scene(pane, 950, 700);
	}

	private VBox initLeftVBox() {
		ImageView logo = new ImageView(new Image("logo.png"));
		logo.setFitWidth(255);
		logo.setPreserveRatio(true);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setBlurType(BlurType.GAUSSIAN);
		dropShadow.setRadius(5);

		Text dollar = new Text("$");
		dollar.setFill(Color.web("#ffc400"));
		dollar.setStyle("-fx-font-size: 20;" +
				"-fx-font: bold;");
		// Textfield for bet
		betMoney = new TextField();
		betMoney.setPromptText("Enter your bid here!");

		betMoney.setDisable(true);
		dollar.setTextAlignment(TextAlignment.CENTER);
		HBox betRow = new HBox(dollar, betMoney);
		HBox.setMargin(dollar, new Insets(5));
		betRow.setAlignment(Pos.CENTER);

		toggleGrp = new ToggleGroup();
		// Select Banker
		BankerButt = new ToggleButton("Bet Banker");
		BankerButt.setPrefSize(100, 20);
		BankerButt.setToggleGroup(toggleGrp);
		BankerButt.setId("Banker");
		BankerButt.setEffect(dropShadow);
		BankerButt.setStyle("-fx-background-radius:15em; -fx-background-color:#ffc400;");
		// Select Player
		PlayerButt = new ToggleButton("Bet Player");
		PlayerButt.setPrefSize(100, 20);
		PlayerButt.setToggleGroup(toggleGrp);
		PlayerButt.setId("Player");
		PlayerButt.setEffect(dropShadow);
		PlayerButt.setStyle("-fx-background-radius:15em; -fx-background-color:#ffc400;");
		// Select Tie
		TieButt = new ToggleButton("Bet Draw");
		TieButt.setPrefSize(100, 20);
		TieButt.setToggleGroup(toggleGrp);
		TieButt.setId("Draw");
		TieButt.setEffect(dropShadow);
		TieButt.setStyle("-fx-background-radius:15em; -fx-background-color:#ffc400;");
		betChoices = new HBox(8.5, BankerButt, PlayerButt, TieButt);
		betChoices.setDisable(true);

		//force the textfield to be Numeric, EX: 1234.56
		betMoney.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*([\\.]\\d{0,2})?")) {
					betMoney.setText(newValue.replaceAll("\\D", ""));
				}
				final int maxLength = 7;
				if (betMoney.getText().length() > maxLength) {
					String s = betMoney.getText().substring(0, maxLength);
					betMoney.setText(s);
				}
			}
		});

		//After Player, Banker, or Tie btn are pressed
		EventHandler<MouseEvent> btnPressedHandler = e -> {
			ToggleButton btn = (ToggleButton) e.getSource();
			btn.setStyle("-fx-background-radius:15em; -fx-background-color: #ff9100;");
			choice = btn.getId();
		};

		// Handler for after Player, Banker, or Tie btn are released
		EventHandler<MouseEvent> btnReleasedHandler = e -> {
			ToggleButton btn = (ToggleButton) e.getSource();
			btn.setStyle("-fx-background-radius:15em; -fx-background-color: #ffc400;");
			choice = btn.getId();
		};

		BankerButt.setOnMousePressed(btnPressedHandler);
		BankerButt.setOnMouseReleased(btnReleasedHandler);

		PlayerButt.setOnMousePressed(btnPressedHandler);
		PlayerButt.setOnMouseReleased(btnReleasedHandler);

		TieButt.setOnMousePressed(btnPressedHandler);
		TieButt.setOnMouseReleased(btnReleasedHandler);

		// Button to submit the player's bet and start the game
		startBtn = new Button("Confirm Bet");
		startBtn.setOnAction(e -> {
			if (!betMoney.getText().equals("")) {
				currentBet = Double.parseDouble(betMoney.getText());
				betMoney.setDisable(true);
				betChoices.setDisable(true);
				startBtn.setDisable(true);
				gamePlay();
			}
		});
		// StartBtn styles
		startBtn.setEffect(dropShadow);
		startBtn.setStyle("-fx-background-radius:15em;");
		startBtn.setDisable(true);
		HBox startHBox = new HBox(startBtn);
		startHBox.setAlignment(Pos.CENTER_RIGHT);

		// Button to enable inputs from users and reset images and result for a new round
		playBtn = new Button("PLAY");
		playBtn.setOnAction(e -> {
			betMoney.setDisable(false);
			betChoices.setDisable(false);
			startBtn.setDisable(false);
			playBtn.setDisable(true);
			result.clear();
			playerCard1.setImage(null);
			playerCard2.setImage(null);
			playerCard3.setImage(null);
			bankerCard1.setImage(null);
			bankerCard2.setImage(null);
			bankerCard3.setImage(null);
		});
		// PlayBtn styles
		playBtn.setEffect(dropShadow);
		playBtn.setStyle("-fx-font-size: 40px;");
		playBtn.setPrefSize(296.7, 75);
		HBox playHBox = new HBox(playBtn);
		playHBox.setAlignment(Pos.BOTTOM_CENTER);

		result.setEditable(false); // set result textbox to be uneditable

		// VBox that contains all of the child nodes in the left sidebar
		VBox selection = new VBox(10, logo, betRow, betChoices, startHBox, result, playHBox);
		// VBox styles
		selection.setMaxWidth(316.7);
		selection.setStyle("-fx-background-color: #d32f2f;");
		DropShadow vBoxDS = new DropShadow();
		vBoxDS.setHeight(0);
		selection.setEffect(vBoxDS);
		VBox.setMargin(logo, new Insets(20, 30, 0, 30));
		VBox.setMargin(betRow, new Insets(50,50,0,50));
		VBox.setMargin(betChoices, new Insets(0,10,0,10));
		VBox.setMargin(startHBox, new Insets(0,10,0,10));
		VBox.setMargin(playHBox, new Insets(10,10,10,10));
		return selection;
	}

	/*
		initRightGrid returns a gridPane for the main area of the game
	 */
	private GridPane initRightGrid() {
		GridPane board = new GridPane();

		// Labels Player and Banker, Changes color of each letter and fonts
		Text playerTextP = new Text("P");
		playerTextP.setFill(Color.web("#ffc400"));
		Text playerTextL = new Text("L");
		playerTextL.setFill(Color.web("#ffc400"));
		Text playerTextA = new Text("A");
		playerTextA.setFill(Color.web("#ffc400"));
		Text playerTextY = new Text("Y");
		playerTextY.setFill(Color.web("#ffc400"));
		Text playerTextE = new Text("E");
		playerTextE.setFill(Color.web("#ffc400"));
		Text playerTextR = new Text("R");
		playerTextR.setFill(Color.web("#ffc400"));
		//Add all the letter vertically
		VBox playerText = new VBox(playerTextP,playerTextL,playerTextA,playerTextY,playerTextE,playerTextR);
		playerText.setStyle("-fx-font-family: Algerian;" +
				"-fx-font-size: 35;");
		playerText.setPadding(new Insets(0,10,0,0));

		Text bankerTextB = new Text("B");
		bankerTextB.setFill(Color.web("#ffc400"));
		Text bankerTextA = new Text("A");
		bankerTextA.setFill(Color.web("#ffc400"));
		Text bankerTextN = new Text("N");
		bankerTextN.setFill(Color.web("#ffc400"));
		Text bankerTextK = new Text("K");
		bankerTextK.setFill(Color.web("#ffc400"));
		Text bankerTextE = new Text("E");
		bankerTextE.setFill(Color.web("#ffc400"));
		Text bankerTextR = new Text("R");
		bankerTextR.setFill(Color.web("#ffc400"));
		//Add all the letter vertically
		VBox bankerText = new VBox(bankerTextB,bankerTextA,bankerTextN,bankerTextK,bankerTextE,bankerTextR);
		bankerText.setStyle("-fx-font-family: Algerian;" +
				"-fx-font-size: 35;");
		bankerText.setPadding(new Insets(0,10,0,0));
		board.setAlignment(Pos.CENTER);

		// initializes imageViews to hold the images of the banker's cards
		bankerCard1 = new ImageView();
		bankerCard1.setPreserveRatio(true);
		bankerCard1.setFitHeight(250);
		bankerCard2 = new ImageView();
		bankerCard2.setPreserveRatio(true);
		bankerCard2.setFitHeight(250);
		bankerCard3 = new ImageView();
		bankerCard3.setPreserveRatio(true);
		bankerCard3.setFitHeight(250);
		bankerPos = new HBox(bankerText, bankerCard1, bankerCard2, bankerCard3);
		bankerPos.setMargin(bankerCard1, new Insets(0,10,0,0));
		bankerPos.setMargin(bankerCard2, new Insets(0,10,0,0));

		// initializes imageViews to hold the images of the player's cards
		playerCard1 = new ImageView();
		playerCard1.setPreserveRatio(true);
		playerCard1.setFitHeight(250);
		playerCard2 = new ImageView();
		playerCard2.setPreserveRatio(true);
		playerCard2.setFitHeight(250);
		playerCard3 = new ImageView();
		playerCard3.setPreserveRatio(true);
		playerCard3.setFitHeight(250);
		playerPos.setMargin(playerCard1, new Insets(0,10,0,0));
		playerPos.setMargin(playerCard2, new Insets(0,10,0,0));

		// initialize HBox that contains all of the child nodes in the main area of the game
		playerPos = new HBox(playerText, playerCard1, playerCard2, playerCard3);
		board.add(bankerPos, 0,2);
		board.add(playerPos, 0,10);
		board.setVgap(10);

		return board;
	}

	/*
	 * gamePlay runs the game with the game logic and deals with the transitions of the cards' images
	 */
	private void gamePlay() {
		frshStart.setDisable(true); // disable fresh start button
		SequentialTransition sT; // sequential transition to play pauseTransitions sequentially
		// pauseTransitions for displaying the cards
		PauseTransition pause1 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause3 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause4 = new PauseTransition(Duration.seconds(2));
		PauseTransition pause5 = new PauseTransition(Duration.seconds(0));
		PauseTransition pause6 = new PauseTransition(Duration.seconds(0));
		PauseTransition pause7 = new PauseTransition(Duration.seconds(3));

		theDealer.shuffleDeck();
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();

		// display player card1
		pause1.setOnFinished(e -> {
			playerCard1.setImage(retrieveCard(playerHand.get(0)));
		});

		// display player card2
		pause2.setOnFinished(e -> {
			playerCard2.setImage(retrieveCard(playerHand.get(1)));
		});

		// display banker card1
		pause3.setOnFinished(e -> {
			bankerCard1.setImage(retrieveCard(bankerHand.get(0)));
		});

		// display banker card2
		pause4.setOnFinished(e -> {
			bankerCard2.setImage(retrieveCard(bankerHand.get(1)));
		});

		Card player3rdC = null;
		if (gameLogic.whoWon(playerHand, bankerHand).equals("Draw")) { // check for natural win
			if (gameLogic.evaluatePlayerDraw(playerHand)) {
				pause5.setDuration(Duration.seconds(3));
				player3rdC = theDealer.drawOne(); // player draw one card
				playerHand.add(player3rdC);
				// display player card3
				pause5.setOnFinished(e -> {
					playerCard3.setImage(retrieveCard(playerHand.get(2)));
				});
			}
			if (gameLogic.evaluateBankerDraw(bankerHand, player3rdC)) {
				pause6.setDuration(Duration.seconds(3));
				bankerHand.add(theDealer.drawOne()); // banker draw one card
				// display player card3
				pause6.setOnFinished(e -> {
					bankerCard3.setImage(retrieveCard(bankerHand.get(2)));
				});
			}
		}

		// slight pause before game ends
		pause7.setOnFinished(e -> {
			gameEnd();
		});
		sT = new SequentialTransition(pause1,pause3,pause2,pause4,pause5,pause6, pause7); // play transitions
		sT.play();
	}

	/*
	 * gameEnd contains the logic for the end of the game
	 */
	private void gameEnd() {
		String winner = gameLogic.whoWon(playerHand, bankerHand);
		result.setText(gameEndMsg(winner));
		if (winner.equals(choice)) // determine if the user wins
			playerWin = true;
		else
			playerWin = false;
		currWinnings.setText(Double.toString(evaluateWinnings())); // displays resulted total winnings
		playBtn.setDisable(false); // enable replay
		frshStart.setDisable(false); // enable fresh start
	}

	/*
	 * gameEndMsg returns the text representation of end results
	 */
	private String gameEndMsg(String winner) {
		String playerMsg = "Player Total: " + gameLogic.handTotal(playerHand);
		String bankerMsg = " Banker Total: " + gameLogic.handTotal(bankerHand) + "\n";
		String winnerMsg = winner + " wins!\n";
		String msg = "";
		if (choice.equals(winner)) {
			msg = "Congratulations! You bet " + choice + "! You win!";
		} else {
			msg = "Sorry, you bet " + choice + "! You lost your bet!";
		}
		return playerMsg + bankerMsg + winnerMsg + msg;
	}

	/*
	 * retrieveCard takes the given card and returns an Image object
	 * that stores the img corresponding to the card
	 */
	private Image retrieveCard(Card c) {
		String suit = c.getSuite();
		int val = c.getValue();
		switch (suit) {
			case "Hearts":
				return (new Image(val + "H.png"));
			case "Diamonds":
				return (new Image(val + "D.png"));
			case "Spades":
				return (new Image(val + "S.png"));
			case "Clubs":
				return (new Image(val + "C.png"));
		}
		return null;
	}
}
