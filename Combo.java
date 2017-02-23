/**
 * Contains a Move and an associated value.
 * For use by the minimax algorithm.
 *
 * Author: Ian Patel
 */

public class Combo {
	private Move move;
	private double value;

	// Save the initial configuration of the Move
	public Combo(double value, Move move) {
		this.value = value;
		this.move = move;
	}

	public Move getMove() {
		return move;
	}
	public double getValue() {
		return value;
	}
	public void setMove(Move move) {
		this.move = move;
	}
	public void setValue(double value) {
		this.value = value;
	}
}
