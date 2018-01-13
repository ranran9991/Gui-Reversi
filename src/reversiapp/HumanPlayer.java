package reversiapp;


public class HumanPlayer implements Player {
	ReversiBoard board;
	boolean isPlayerOne;
	int playerNum;
	
	public HumanPlayer(ReversiBoard board, boolean isPlayerOne){
		this.board = board;
		this.isPlayerOne = isPlayerOne;
		if(isPlayerOne) playerNum = 1;
		else playerNum = 2;
	}
	
	public boolean playTurn(int i, int j){
		/*
		 * This method defines how a turn is played by a normal local human player
		 */
		return board.MakeMove(i, j, playerNum);
	}
	
	public boolean hasLegalMove() {
		return board.PossibleMoveExists(playerNum);
	}
	
}