/**
 * A Player in the game. A Player object represents a human player in the game.
 *
 * Author: Ian Patel
 */

public class Player {
	protected String color;
	protected volatile boolean isMyTurn;
	protected int MAX_DEPTH = 0;
	
	// Save player's color. A blue player will always go first
	public Player(String color) {
		this.color = color;
		if (color.compareTo("white") == 0)
			this.isMyTurn = true;
		else if (color.compareTo("black") == 0)
			this.isMyTurn = false;
	}

	// Getter and setter for 'isMyTurn' boolean
	public boolean isMyTurn() {
		return isMyTurn;
	}
	public void isMyTurn(boolean isMyTurn) {
		this.isMyTurn = isMyTurn;
	}

	public void setMaxDepth(int depth) {
		MAX_DEPTH = depth;
	}
	
	// Wait for a human player to select a move with the mouse
	public Move move(Board board) {
		while (true) {
			if (!isMyTurn)
				break;
		}

		return null;
	}
}