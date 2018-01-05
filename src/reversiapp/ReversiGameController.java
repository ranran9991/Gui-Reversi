package reversiapp;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class ReversiGameController implements Initializable{
	@FXML
	private HBox root;
	private int[][] reversi = {
			{0,0,0,0},
			{0,0,0,0},
			{0,0,0,0}
	};
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		ReversiBoard reversiBoard = new ReversiBoard(reversi, new HumanPlayer(reversi, true), new HumanPlayer(reversi, false));
		reversiBoard.setPrefWidth(400);
		reversiBoard.setPrefHeight(400);
		root.getChildren().add(0, reversiBoard);
		reversiBoard.draw();
		//Handeling resize of Height
		root.heightProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewHeight = newValue.doubleValue() -120;
			reversiBoard.setPrefHeight(boardNewHeight);
			reversiBoard.draw();
		});
		//Handeling resize of Width
		root.widthProperty().addListener((observable, oldValue, newValue) -> {
			double boardNewWidth = newValue.doubleValue() -120;
			reversiBoard.setPrefWidth(boardNewWidth);
			reversiBoard.draw();
		});
	}
}
