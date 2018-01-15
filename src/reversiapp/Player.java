package reversiapp;


public interface Player {
	/**
	 * @param i row in the matrix
	 * @param j column in the matrix
	 * @return false if move is illegal, true otherwise
	 */
	boolean playTurn(int i, int j);
	
	/**
	 * @return true if player has legal move, false otherwise.
	 */
	boolean hasLegalMove();
}
