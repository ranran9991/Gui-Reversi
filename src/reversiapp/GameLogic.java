package reversiapp;

public interface GameLogic {
	/**
	 * @param x row cord
	 * @param y column cord
	 * @param playerNum player 1 or player 2
	 * @return returns if making the move was successful or not
	 */
	boolean MakeMove(int x, int y, int playerNum);
	/**
	 * @param x row cord
	 * @param y column cord
	 * @param playerNum player 1 or 2
	 * @return
	 */
	boolean CheckLegal(int x, int y, int playerNum);
	/**
	 * @param playerNum player 1 or 2
	 * @return true if player <playerNum> has any moves , false otherwise
	 */
	boolean PossibleMoveExists(int playerNum);
	/**
	 * @param playerNum player 1 or 2
	 * @return the amount of points player <playerNum> has
	 */
	int CountSign(int playerNum);
	/**
	 * @return true of the game is over, false otherwise
	 */
	boolean End();
	/**
	 * @param playerNum player 1 or 2
	 */
	void UpdateAvailableMoves(int playerNum);
}
