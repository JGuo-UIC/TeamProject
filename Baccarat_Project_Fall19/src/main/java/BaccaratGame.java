import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BaccaratGame extends Application {
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;

	BaccaratDealer theDealer;
	BaccaratGameLogic gameLogic;

	String choice = "";
	double currentBet;
	double totalWinnings;

	MenuBar menuBar = new MenuBar();
	HashMap<String, Scene> sceneMap = new HashMap<String, Scene>();
	
	Button playBtn;
	ToggleButton PlayerButt, BankerButt, TieButt;
	Button placeBet;
	EventHandler<ActionEvent> bpdButt;
	TextField betMoney;
	
	TextArea result;


	public double evaluateWinnings() {return 0.0;}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Baccarat!!!");		
		initMenu();
		sceneMap.put("scene", mainScene());
		//sceneMap.put("gameScene", gameBoardScene());
		primaryStage.setScene(sceneMap.get("scene"));
		primaryStage.show();
	}

	// initMenu initializes a menu with two menu items: freshstart and exit, and add into menuBar
	public void initMenu() {
		Menu mainMenu = new Menu();
		mainMenu.setText("Options");
		MenuItem frshStart = new MenuItem();
		MenuItem exitItm = new MenuItem();

		frshStart.setText("Fresh Start");
		frshStart.setOnAction(e -> {
			placeBet.setDisable(true);
			PlayerButt.setDisable(true);
			BankerButt.setDisable(true);
			TieButt.setDisable(true);
			playBtn.setDisable(false);
			currentBet = 0;
			totalWinnings = 0;
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
		//pane.setPadding(new Insets(70));

		betMoney = new TextField();
		betMoney.setPromptText("Enter your bid here!");

		//force the textfield to be Numeric, EX: 1234.56
		betMoney.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*([\\.]\\d{0,2})?")){
					betMoney.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});

		// Button to submit player's bet
		placeBet = new Button("Place Bet");
		placeBet.setOnAction(e->{
			currentBet = Integer.parseInt(betMoney.getText());
		});

		BankerButt = new ToggleButton("Bet On Banker");
		BankerButt.setId("Banker");
		PlayerButt = new ToggleButton("Bet on Player");
		PlayerButt.setId("Player");
		TieButt = new ToggleButton("Bet On Tie");
		TieButt.setId("Draw");
		HBox betChoices = new HBox(BankerButt, PlayerButt, TieButt);

		//After Player, Banker, or Tie butt are pressed
		bpdButt = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent pressed){
				ToggleButton butt = (ToggleButton)pressed.getSource();
				choice = butt.getId();
				betChoices.setDisable(true);
				//primaryStage.setScene(sceneMap.get("gameScene")); //switches to the game scene
			}
		};

		BankerButt.setOnAction(bpdButt);
		PlayerButt.setOnAction(bpdButt);
		TieButt.setOnAction(bpdButt);
		betChoices.setDisable(true);

		playBtn = new Button("PLAY");
		playBtn.setOnAction(e -> {
			placeBet.setDisable(false);
			betChoices.setDisable(false);
			playBtn.setDisable(true);
		});

		VBox selection = new VBox(10, betMoney, betChoices, playBtn);
		pane.setLeft(selection);
		pane.setStyle("-fx-background-color: Green;");

		return new Scene(pane, 950, 600);
	}

	public Scene gameBoardScene() {
		//Temporary Holder
		BorderPane pane = new BorderPane();
		return new Scene(pane, 950,600);
	}

	//Checks players money, NOT fully implemented
//	boolean playerMoney(int money) {
//		int initMoney = 10000;
//		if(initMoney >= money)
//			return true;
//		else
//			return false;
//	}

	// gameEnd contains the logic for the end of the game
	private void gameEnd() { // text representation of end results, prefer a popup window
		result = new TextArea();
		result.setText(gameEndMsg());

	}

	private String gameEndMsg() {
		String playerMsg = "Player Total: " + gameLogic.handTotal(playerHand);
		String bankerMsg = "Banker Total: " + gameLogic.handTotal(bankerHand) + "\n";
		String winner = gameLogic.whoWon(playerHand, bankerHand);
		String winnerMsg = winner + "wins!\n";
		String msg = "";
		if (choice.equals(winner)) {
			msg = "Congratuations! You bet " + choice + "! You win!";
		} else {
			msg = "Sorry, you bet " + choice + "! You lost your bet!";
		}
		return playerMsg + bankerMsg + winnerMsg + msg;
	}




	/* Optional to implement the Welcome Scene
		StackPane root = new StackPane();

		//Welcome Scene
		welcome = new Text("Welcome to a game of Baccarat\n\n");
		startButt = new Button("Let's Begin!");
		startButt.setOnAction(e->stage.setScene(sceneMap.get("scene")));
		sceneMap.put("scene", SceneController());
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		welcome.setFill(Color.SKYBLUE);
		welcome.setTextAlignment(TextAlignment.CENTER);
		StackPane.setAlignment(welcome, Pos.CENTER);
		Image image = new Image(welcomePage.jpg);
		root.setStyle("-fx-background-image: url('"+image+"');" +
				"-fx-background-position: center center;");
		root.getChildren().addAll(welcome, startButt);
		*/
//	public Scene SceneController(){
//		vb = new VBox(new Label("To Be Added"));
//		return new Scene(vb, 950,600);
//	}

}
