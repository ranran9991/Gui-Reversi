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
	/**
	 * The root HBox layout in which everything is set
	 */
	@FXML
	private HBox root;
	/**
	 * Button to finish the game
	 */
	@FXML
	private Button endButton;
	/**
	 * Label that tells the client which player should take his turn next
	 */
	@FXML
	Label turnLabel;
	/**
	 * Label to tell the players the score of Player 1
	 */
	@FXML
	Label ScoreOneLabel;
	/**
	 * Label to tell th eplayers the score of Player 2
	 */
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
		turnLabel.setText("Current turn: Player 1");
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

	/**
	 * Function that is called after clicking the exit button
	 * handles the closing of the window
	 */
	@FXML
	private void closeButtonAction(){
		//get a handle of the stage
		Stage stage = (Stage) endButton.getScene().getWindow();
		//close the stage
		stage.close();
	}
}
