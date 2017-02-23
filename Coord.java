/**
 * Stores an x-y coordinate
 * 
 * Author: Ian Patel
 */
 
public class Coord {
	private int x;
	private int y;
	
	public Coord(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public int X() {
		return x;
	}
	public int Y() {
		return y;
	}
	public void X(int newX) {
		x = newX;
	}
	public void Y(int newY) {
		y = newY;
	}
	
	// Print like a two-tuple
	public String toString() {
		String output = "(" + x + ", " + y + ")";
		return output;
	}
	
	// Compare the two values for equality
	public boolean equals(Object other) {
		if (other instanceof Coord) {
			if (x == ((Coord)other).X() && y == ((Coord)other).Y())
				return true;
			else
				return false;
		}
		else
			return false;
	}
	
	public int hashCode() {
		return x + 10*y;
	}
}
