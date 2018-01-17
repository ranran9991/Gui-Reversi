package reversiapp;

public class ReversiLogics implements GameLogic{
	int[][] board;
	private static final int FREE = 0;
	private static final int PLAYERONE = 1;
	private static final int PLAYERTWO = 2;
	
	public ReversiLogics(int[][] board) {
		this.board = board;
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
		if (place == FREE) return false; // if reached a zero, return false
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
		if (board[x][y] != FREE) return false;
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
	
	public boolean End() {
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
