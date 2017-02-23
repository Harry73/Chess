/**
 * Simple structure class to store two points that constitute a move.
 * 
 * Author: Ian Patel
 */

public class Move {
	private Coord to;
	private Coord from;
	private boolean promotion;
	private String promoteTo;
	
	// Create a Move from two Coords
	public Move(Coord from, Coord to) {
		this.to = to;
		this.from = from;
		this.promotion = false;
		this.promoteTo = "";
	}
	
	public Move(Coord from, Coord to, boolean promotion, String promoteTo) {
		this.to = to;
		this.from = from;
		this.promotion = promotion;
		this.promoteTo = promoteTo;
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

	public boolean promotion() {
		return promotion;
	}
	
	public String promoteTo() {
		return promoteTo;
	}
	
	// Compare the two coordinates for equality
	public boolean equals(Object other) {
		if (other instanceof Move) {
			if (to.equals(((Move)other).to()) && from.equals(((Move)other).from()) && promotion == ((Move)other).promotion()) 
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
		String output = from + " -> " + to;
		if (promotion) {
			output += " (promote to " + promoteTo + ")";
		}
		return output;
	}
}