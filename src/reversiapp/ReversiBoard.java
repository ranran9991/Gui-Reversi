package reversiapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import alertbox.AlertBox;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeType;



public class ReversiBoard extends GridPane{
	public int[][] board;
	Player playerOne;
	Color playerOneColor;
	Player playerTwo;
	Color playerTwoColor;
	ReversiLogics logics;
	private static final int FREE = 0;
	private static final int PLAYERONE = 1;
	private static final int PLAYERTWO = 2;
	
	boolean OneTurn = true;
	
	public ReversiBoard(String fileName){
		/*
		 * The expected file format is
		 * <Int>
		 * <String>
		 * <String>
		 */
		try {
			//opening file
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			//reading first line which is expected to be an integer
			String line = reader.readLine();
			//parsing size of board
			board = new int[Integer.parseInt(line)][Integer.parseInt(line)];
			//initializing board with 0's
			for(int i=0;i<board.length;i++){
				for(int j=0; j<board[0].length; j++){
					board[i][j] = 0;
				}
			}
			//setting middle pieces
			board[board.length/2][board.length/2] = PLAYERTWO;
			board[(board.length/2) - 1][(board.length/2) - 1] = PLAYERTWO;
			board[(board.length/2)][(board.length/2) - 1] = PLAYERONE;
			board[(board.length/2) - 1][(board.length/2)] = PLAYERONE;
			//reading and initializing color hex values from file
			line = reader.readLine();
			playerOneColor = Color.web(line);
			line = reader.readLine();
			playerTwoColor = Color.web(line);
			reader.close();
		} 
		//rethrow exceptions
		catch(FileNotFoundException e){
			throw new RuntimeException(e);
		} 
		catch(IOException e){
			throw new RuntimeException(e);
		}
		logics = new ReversiLogics(board);
	}
	//simple method for setting players
	public void setPlayers(Player playerOne, Player playerTwo){
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	
	public void draw(Label label, Label ScoreOneLabel, Label ScoreTwoLabel) {
		//clearing children of board because they will be recreated in this method
		this.getChildren().clear();
		int height = (int)this.getPrefHeight();
		int width = (int)this.getPrefWidth();
		
		int min = Math.min(height, width);
		int cellHeight = min/board.length;
		int cellWidth = min/board[1].length;
		//creating rectangles in the board
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == FREE){
					this.add(new Rectangle(cellHeight, cellWidth, Color.LIGHTGRAY), j, i);
				}
				else if(board[i][j] == PLAYERONE){
					this.add(new Rectangle(cellHeight, cellWidth, playerOneColor), j, i);
				}
				else if(board[i][j] == PLAYERTWO){
					this.add(new Rectangle(cellHeight, cellWidth, playerTwoColor), j, i);
				}
			}
		}
		//setting evens on player mouse click
		for(Node node : this.getChildren()) {
			//for every node set border
			Rectangle rectangle = (Rectangle) node;
			rectangle.setStroke(Color.BLACK);
			rectangle.setStrokeType(StrokeType.INSIDE);
			node.setOnMouseClicked(e -> {
				/*
				 * If the color of the tile is white it can be changed
				 * otherwise don't ever change the color of that tile through events.
				 */
				if(((Shape) node).getFill().equals(Color.LIGHTGRAY)) {
					//if its the turn of the first player and he has legal moves
					if(OneTurn) {
						if (playerOne.playTurn(getRowIndex(node), getColumnIndex(node)) &&
								playerTwo.hasLegalMove()) {
							OneTurn = false;
							label.setText("Current Turn:\nPlayer 1");
							ScoreOneLabel.setText("Player 1 Score: " + logics.CountSign(PLAYERONE));
							ScoreTwoLabel.setText("Player 2 Score: " + logics.CountSign(PLAYERTWO));
						}
					}
					//if the turn of the second player and he has legal moves
					else {
						if (playerTwo.playTurn(getRowIndex(node), getColumnIndex(node)) &&
								playerOne.hasLegalMove()) {
							OneTurn = true;
							label.setText("Current Turn:\nPlayer 2");
							ScoreOneLabel.setText("Player 1 Score: " + logics.CountSign(PLAYERONE));
							ScoreTwoLabel.setText("Player 2 Score: " + logics.CountSign(PLAYERTWO));
						}
					}
					draw(label, ScoreOneLabel, ScoreTwoLabel);
				}
				//check after every move if it is over
				 if(logics.End()) {
					 String alert = "Game is over, ";
					 int countOne = logics.CountSign(PLAYERONE);
					 int countTwo = logics.CountSign(PLAYERTWO);
					 if (countOne > countTwo)
						 alert += "player 1 won!";
					 else if (countTwo > countOne)
						 alert += "player 2 won!";
					 else 
						 alert += "it's a tie!";
					 AlertBox.display("Game is over", alert);
				 }
				 
			});
		}
	}
}
