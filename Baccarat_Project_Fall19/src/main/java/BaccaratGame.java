import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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

	double currentBet;
	double totalWinnings;

	Scene scene;
	Stage stage;
	HashMap<String, Scene> sceneMap;
	ToggleButton PlayerButt, BankerButt, TieButt;
	EventHandler<ActionEvent> bpdButt;
	TextField betMoney;


	public double evaluateWinnings() {return 0.0;}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Let's Play Baccarat!!!");

		sceneMap = new HashMap<String, Scene>();
		betMoney = new TextField();
		stage = new Stage();

		//force the textfield to be Numeric, EX: 1234.56
		betMoney.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if(!newValue.matches("\\d*([\\.]\\d{0,1})?")){
					betMoney.setText(newValue.replaceAll("\\D", ""));
				}
			}
		});
		//Adding bet money
		betMoney.setOnKeyPressed(e-> {if(e.getCode().equals(KeyCode.ENTER)){
				betMoney.clear();
			}
		});
		/*Temporary
		VBox vb = new VBox(10, betMoney, BankerButt, PlayerButt, TieButt);
		scene = new Scene(vb);
		stage.setScene(scene);
		*/
		sceneMap.put("scene", mainScene());
		stage.setScene(sceneMap.get("scene"));
		stage.show();
	}
	public Scene mainScene() {
		BorderPane pane = new BorderPane();
		//pane.setPadding(new Insets(70));
		BankerButt = new ToggleButton();
		PlayerButt = new ToggleButton();
		TieButt = new ToggleButton();
		VBox selection = new VBox(10, betMoney, BankerButt, PlayerButt, TieButt);

		pane.setLeft(selection);
		pane.setStyle("-fx-background-color: Green;");

		return new Scene(pane, 950, 600);
	}
	//Checks players money, NOT fully implemented
	boolean playerMoney(int money) {
		int initMoney = 10000;
		if(initMoney >= money)
			return true;
		else
			return false;
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
