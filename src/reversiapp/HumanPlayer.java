package reversiapp;


public class HumanPlayer implements Player {
	GameLogic logics;
	boolean isPlayerOne;
	int playerNum;
	
	public HumanPlayer(ReversiBoard board, boolean isPlayerOne){
		logics = board.logics;
		this.isPlayerOne = isPlayerOne;
		if(isPlayerOne) playerNum = 1;
		else playerNum = 2;
	}
	
	public boolean playTurn(int i, int j){
		/*
		 * This method defines how a turn is played by a normal local human player
		 */
		return logics.MakeMove(i, j, playerNum);
	}
	
	public boolean hasLegalMove() {
		return logics.PossibleMoveExists(playerNum);
	}

	public void UpdateAvailable() {
		logics.UpdateAvailableMoves(playerNum);
	}
	
}