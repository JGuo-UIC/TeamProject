import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
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

	VBox vb;
	HBox hb;
	Scene scene;
	Stage stage;
	Text welcome;
	HashMap<String, Scene> sceneMap;
	Button startButt;
	ToggleButton PlayerButt, BankerButt, TieButt;


	public double evaluateWinnings() {return 0.0;}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		StackPane root = new StackPane();
		sceneMap = new HashMap<String, Scene>();
		//Welcome Scene
		welcome = new Text("Welcome to a game of Baccarat\n\n");
		startButt = new Button("Let's Begin!");
		startButt.setOnAction(e->stage.setScene(sceneMap.get("scene")));
		sceneMap.put("scene", SceneController());
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
		welcome.setFill(Color.SKYBLUE);
		welcome.setTextAlignment(TextAlignment.CENTER);
		StackPane.setAlignment(welcome, Pos.CENTER);
		//Image image = new Image("src/images/welcomePage.jpg");
		//root.setStyle("-fx-background-image: url('"+image+"');" +
		//		"-fx-background-position: center center;");
		root.getChildren().addAll(welcome, startButt);

		//vb = new VBox(welcome, startButt);
		scene = new Scene(root, 950, 600);
		stage = new Stage();
		stage.setTitle("Let's Play Baccarat!!!");
		stage.setScene(scene);
		stage.show();
	}
	public Scene SceneController(){
		vb = new VBox(new Label("To Be Added"));
		return new Scene(vb, 950,600);
	}

}
