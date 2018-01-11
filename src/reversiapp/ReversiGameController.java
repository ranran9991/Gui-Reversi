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
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ReversiBoard reversiBoard = new ReversiBoard("src/settings");
		reversiBoard.setPlayers(new HumanPlayer(reversiBoard.board, true), new HumanPlayer(reversiBoard.board, false));
		reversiBoard.setPrefWidth(400);
		reversiBoard.setPrefHeight(400);
		turnLabel.setText("Current turn: player 1");
		root.getChildren().add(0, reversiBoard);
		reversiBoard.draw(turnLabel);
		//Handeling resize of Height
		root.heightProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewHeight = newValue.doubleValue() -100;
			reversiBoard.setPrefHeight(boardNewHeight);
			reversiBoard.draw(turnLabel);
		});
		//Handeling resize of Width
		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() -100;
			reversiBoard.setPrefWidth(boardNewWidth);
			reversiBoard.draw(turnLabel);
		});
	}
	@FXML
	private void closeButtonAction(){
		//get a handle of the stage
		Stage stage = (Stage) endButton.getScene().getWindow();
		//close the stage
		stage.close();
	}
}
