package reversiapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



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
		try{
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line = reader.readLine();
		board = new int[Integer.parseInt(line)][Integer.parseInt(line)];
		//initializing board with 0's
		for(int i=0;i<board.length;i++){
			for(int j=0; j<board[0].length; j++){
				board[i][j] = 0;
			}
		}
		board[board.length/2][board.length/2] = PLAYERTWO;
		board[(board.length/2) - 1][(board.length/2) - 1] = PLAYERTWO;
		board[(board.length/2)][(board.length/2) - 1] = PLAYERONE;
		board[(board.length/2) - 1][(board.length/2)] = PLAYERONE;
		line = reader.readLine();
		playerOneColor = Color.web(line);
		line = reader.readLine();
		playerTwoColor = Color.web(line);
		reader.close();
		//rethrow exceptions
		} catch(FileNotFoundException e){
			throw new RuntimeException(e);
		} catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	public void setPlayers(Player playerOne, Player playerTwo){
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}
	public ReversiBoard(int[][] board, Player playerOne, Player playerTwo){
		this.board = board;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReversiBoard.fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try{
			fxmlLoader.load();
		} catch(IOException exception){
			throw new RuntimeException(exception);
		}
	}
	
	public void draw() {
		this.getChildren().clear();
		
		int height = (int)this.getPrefHeight();
		int width = (int)this.getPrefWidth();
		
		int cellHeight = height / board.length;
		int cellWidth = width / board[0].length;
		
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
		for(Node node : this.getChildren()){
			node.setOnMouseClicked(e -> {
				if(OneTurn){
					playerOne.playTurn(getRowIndex(node), getColumnIndex(node));
					OneTurn = false;
					draw();
				}
				else{
					playerTwo.playTurn(getRowIndex(node), getColumnIndex(node));
					OneTurn = true;
					draw();
				}
			});
		}
	}
}
