package reversiapp;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



public class ReversiBoard extends GridPane{
	private int[][] board;
	Player playerOne;
	Player playerTwo;
	private static final int FREE = 0;
	private static final int PLAYERONE = 1;
	private static final int PLAYERTWO = 2;
	boolean OneTurn = true;
	
	
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
					this.add(new Rectangle(cellHeight, cellWidth, Color.BLUE), j, i);
				}
				else if(board[i][j] == PLAYERTWO){
					this.add(new Rectangle(cellHeight, cellWidth, Color.RED), j, i);
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
