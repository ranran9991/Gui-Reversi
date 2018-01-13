package reversiapp;


public interface Player {
	boolean playTurn(int i, int j);
	boolean hasLegalMove();
}
