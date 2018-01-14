package reversiapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;



public class ReversiBoard extends GridPane{
	public int[][] board;
	Player playerOne;
	Color playerOneColor;
	Player playerTwo;
	Color playerTwoColor;
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
		
		//int cellHeight = height / board.length;
		//int cellWidth = width / board[0].length;
		
		int cellHeight = height/board.length;
		int cellWidth = width/board[1].length;
		//creating rectangles in the board
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				if(board[i][j] == FREE){
					this.add(new Rectangle(cellHeight,cellWidth, Color.WHITE), j, i);
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
			node.setOnMouseClicked(e -> {
				/*
				 * If the color of the tile is white it can be changed
				 * otherwise don't ever change the color of that tile through events.
				 */
				if(((Shape) node).getFill().equals(Color.WHITE)) {
					//if its the turn of the first player and he has legal moves
					if(OneTurn) {
						if (playerOne.playTurn(getRowIndex(node), getColumnIndex(node)) &&
								playerTwo.hasLegalMove()) {
							OneTurn = false;
							label.setText("Current Turn:\nPlayer 1");
							ScoreOneLabel.setText("Player 1 Score: " + this.CountSign(PLAYERONE));
							ScoreTwoLabel.setText("Player 2 Score: " + this.CountSign(PLAYERTWO));
						}
					}
					//if the turn of the second player and he has legal moves
					else {
						if (playerTwo.playTurn(getRowIndex(node), getColumnIndex(node)) &&
								playerOne.hasLegalMove()) {
							OneTurn = true;
							label.setText("Current Turn:\nPlayer 2");
							ScoreOneLabel.setText("Player 1 Score: " + this.CountSign(PLAYERONE));
							ScoreTwoLabel.setText("Player 2 Score: " + this.CountSign(PLAYERTWO));
						}
					}
					draw(label, ScoreOneLabel, ScoreTwoLabel);
				}
				//check after every move if it is over
				 if(End()) {
					 String alert = "Game is over, ";
					 int countOne = CountSign(PLAYERONE);
					 int countTwo = CountSign(PLAYERTWO);
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
	
	public boolean MakeMove(int x, int y, int playerNum) {
		if (!CheckLegal(x, y, playerNum)) return false; // illegal move
		// for each direction
		for (int i = -1, j; i <= 1; i++)
			for (j = -1; j <= 1; j++)
				if (i != 0 || j != 0)
					// making the move in this direction
					MakeMoveDir(x+i, y+j, i, j, playerNum);
		board[x][y] = playerNum; // assigning the number to the given place
		return true;
	}
	
	private boolean MakeMoveDir(int x, int y, int i, int j, int playerNum) {
		int height = board.length;
		int width = board[1].length;
		if (x >= height || x < 0 || y >= width || y < 0) return false;
		int place = board[x][y];
		if (place == 0) return false; // if reached a zero, return false
		if (place == playerNum) return true; // if reached the right number, end and return true
		// if this direction is legal
		if (MakeMoveDir(x+i, y+j, i, j, playerNum)) {
			// assign the number to the place
			board[x][y] = playerNum;
			return true;
		}
		return false; // else, this direction is illegal and return false
	}
	
	public boolean CheckLegal(int x, int y, int playerNum) {
		int height = board.length;
		int width = board[1].length;
		if (x >= height || x < 0 || y >= width || y < 0) return false;
		if (board[x][y] != 0) return false;
		// checking for each direction
		for (int i = -1, j; i <= 1; i++)
			for (j = -1; j <= 1; j++)
				if (i != 0 || j != 0)
					// if there is a valid direction, return true
					if (CheckLegalDir(x+i, y+j, i, j, playerNum)) return true;
		// else, return false
		return false;
	}
	
	private boolean CheckLegalDir(int x, int y, int i, int j, int playerNum) {
		int height = board.length;
		int width = board[1].length;
		if (x >= height || x < 0 || y >= width || y < 0) return false;
		int place = board[x][y];
		if (place == 0) return false; // if reached a zero, return false
		if (place == playerNum) {
			/* if another place with the right number was found in the direction
			 * but it is next to the place we started searching from, return false
			 */
			if (board[x-i][y-j] == 0) return false;
			return true; // else, the right number was found in the given direction
		}
		// if reached the other number, continue searching in the same direction
		return CheckLegalDir(x+i, y+j, i, j, playerNum);
	}
	
	public boolean PossibleMoveExists(int playerNum) {
		int height = board.length;
		int width = board[1].length;
		// for all places
		for (int i = 0, j; i < height; i++)
			for (j = 0; j < width; j++)
				// if move is legal in some place, return true
				if (CheckLegal(i, j, playerNum)) return true;
		// else, return false
		return false;
	}
	
	private boolean End() {
		return !(PossibleMoveExists(PLAYERONE) || PossibleMoveExists(PLAYERTWO));
	}
	
	public int CountSign(int playerNum) {
		int height = board.length;
		int width = board[1].length;
		int count = 0;
		// for all places
		for (int i = 0, j; i < height; i++)
			for (j = 0; j < width; j++)
				// if given char was found, increase the counter
				if (board[i][j] == playerNum) ++count;
		return count;
	}
}
