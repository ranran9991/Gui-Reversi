package reversiapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ReversiGameController implements Initializable{
	@FXML
	private HBox root;
	@FXML
	private Button endButton;
	@FXML
	Label turnLabel;
	@FXML
	Label ScoreOneLabel;
	@FXML
	Label ScoreTwoLabel;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//default values for height and width of window
		final double prefWidth = 400;
		final double prefHeight = 400;
		//creates a GridPane (reversi board)
		ReversiBoard reversiBoard = new ReversiBoard("settings");
		//setting the players
		reversiBoard.setPlayers(new HumanPlayer(reversiBoard, true), new HumanPlayer(reversiBoard, false));
		//setting height and width of the board
		reversiBoard.setPrefWidth(prefWidth);
		reversiBoard.setPrefHeight(prefHeight);
		//setting default label values at pop up of window
		turnLabel.setText("Current turn:\nPlayer 1");
		ScoreOneLabel.setText("Player 1 score: 2");
		ScoreTwoLabel.setText("Player 2 score: 2");
		//This lines are used so labels wont be overwritten by the window itself
		ScoreOneLabel.setMinWidth(200);
		ScoreTwoLabel.setMinWidth(200);
		//adding reversiboard on the left 
		root.getChildren().add(0, reversiBoard);
		//drawing the board
		reversiBoard.draw(turnLabel, ScoreOneLabel, ScoreTwoLabel);
		//Handeling resize of Height
		root.heightProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewHeight = newValue.doubleValue() -100;
			reversiBoard.setPrefHeight(boardNewHeight);
			reversiBoard.draw(turnLabel, ScoreOneLabel, ScoreTwoLabel);
		});
		//Handeling resize of Width
		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() -100;
			reversiBoard.setPrefWidth(boardNewWidth);
			reversiBoard.draw(turnLabel, ScoreOneLabel, ScoreTwoLabel);
		});
	}
	//function to handle closing with window
	@FXML
	private void closeButtonAction(){
		//get a handle of the stage
		Stage stage = (Stage) endButton.getScene().getWindow();
		//close the stage
		stage.close();
	}
}
