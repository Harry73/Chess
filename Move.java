/**
 * Simple structure class to store two points that constitute a move.
 * 
 * Author: Ian Patel
 */

public class Move {
	private Coord to;
	private Coord from;
	
	// Create a Move from two Coords
	public Move(Coord from, Coord to) {
		this.to = to;
		this.from = from;
	}
	
	// Set the "move to" Coord
	public void to(Coord to) {
		this.to = to;
	}
	
	// Get the "move to" Coord
	public Coord to() {
		return to;
	}
	
	// Set the "move from" Coord
 	public void from(Coord from) {
		this.from = from;
	}
	
	// Get the "move from" Coord
	public Coord from() {
		return from;
	}

	// Compare the two coordinates for equality
	public boolean equals(Object other) {
		if (other instanceof Move) {
			if (to.equals(((Move)other).to()) && from.equals(((Move)other).from())) 
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public int hashCode() {
		return to.hashCode() + from.hashCode() * 1000;
	}
	
	public String toString() {
		return from + " -> " + to;
	}
}