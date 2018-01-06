package reversiapp;


public class HumanPlayer implements Player {
	int[][] grid;
	boolean isPlayerOne;
	
	public HumanPlayer(int[][] grid, boolean isPlayerOne){
		this.grid = grid;
		this.isPlayerOne = isPlayerOne;
	}
	public void playTurn(int i, int j){
		/*
		 * This method defines how a turn is played by a normal local human player
		 */
		if(isPlayerOne){
			grid[i][j] = 1;
		}
		else{
			grid[i][j] = 2;
		}
	}
	
}
