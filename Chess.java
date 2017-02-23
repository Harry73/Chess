/**
 * Driver class of the Chess program.
 * 
 * Author: Ian Patel
 */

import java.util.*;

public class Chess {
	// Main method, simply creates a Game object and run the game
	public static void main(String[] args) {
		Game chess = new Game();
		chess.run();
	}	
}

/**
Something's screwy with queens moving into the second row. Actually a lot's screwy, because when the player moves it generates a list of valid moves
which requires using the "move" function on a bunch of different test boards. Which might include pawn movements into promotions. So, while testing, the 
promotion panel actually shouldn't appear, maybe just auto-default to queen. 
*/