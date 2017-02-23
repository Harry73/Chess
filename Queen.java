/**
 * Class representing a Queen piece
 * 
 * Author: Ian Patel
 */

import java.util.*;

public class Queen implements Piece {
	private String id;
	private String color;
	private Coord location;
	private LinkedList<Move> validMoves;
	private boolean hasMoved;
	
	public Queen(String id, String color, Coord location, boolean hasMoved) {
		this.id = id;
		this.color = color;
		this.location = location;
		this.hasMoved = hasMoved;
		validMoves = new LinkedList<Move>();
	}
	
	public String getID() {
		return id;
	}
	
	public String getColor() {	
		return color;
	}
	
	public Coord getLocation() {
		return location;
	}
	
	public void setLocation(Coord p) {
		location = p;
	}
	
	public void setHasMoved(boolean hasMoved) {
		this.hasMoved = hasMoved;
	}
	
	// Calculates the Coord at which to draw the piece.
	public int drawX() {
		return location.X() * 75;
	}
	public int drawY() {
		return (7 - location.Y()) * 75;
	}
		
	public void determineValidMoves(Board board) {
		validMoves.clear();
		int x = location.X();
		int y = location.Y();
		
		// Combo of Rook and Bishop methods
		boolean done = true;
		while (x+1<=7 && !board.squareOccupied(new Coord(x+1, y), color) && done) {
			validMoves.add(new Move(location, new Coord(x+1, y)));
			x++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while(x-1>=0 && !board.squareOccupied(new Coord(x-1, y), color) && done) {
			validMoves.add(new Move(location, new Coord(x-1, y)));
			x--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		
		while (y+1<=7 && !board.squareOccupied(new Coord(x, y+1), color) && done) {
			validMoves.add(new Move(location, new Coord(x, y+1)));
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while (y-1>=0 && !board.squareOccupied(new Coord(x, y-1), color) && done) {
			validMoves.add(new Move(location, new Coord(x, y-1)));
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		x = location.X();
		y = location.Y();
		done = true;
		
		
		
		while (x+1<=7 && y+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), color) && done) {
			validMoves.add(new Move(location, new Coord(x+1, y+1)));
			x++;
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while(x-1>=0 && y+1<=7 && !board.squareOccupied(new Coord(x-1, y+1), color) && done) {
			validMoves.add(new Move(location, new Coord(x-1, y+1)));
			x--;
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
				
		while (x+1<=7 && y-1>=0 && !board.squareOccupied(new Coord(x+1, y-1), color) && done) {
			validMoves.add(new Move(location, new Coord(x+1, y-1)));
			x++;
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while (x-1>=0 && y-1>=0 && !board.squareOccupied(new Coord(x-1, y-1), color) && done) {
			validMoves.add(new Move(location, new Coord(x-1, y-1)));
			x--;
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
	}

	public LinkedList<Move> getValidMoves(Board board) {
		determineValidMoves(board);
		return validMoves;
	}

	// Check if the piece has moved yet.
	public boolean hasItMoved()	{
		return hasMoved;
	}
	
	// Moves a piece. Assumes the move is valid.
	public void move(Board board, Coord p) {
		hasMoved = true;
		location = p;
	}

	// Returns a list of squares that the piece can attack
	public LinkedList<Coord> attackSquares(Board board) {
		LinkedList<Coord> attacks = new LinkedList<Coord>();

		int x = location.X();
		int y = location.Y();
		
		// Combo of Rook and Bishop methods
		boolean done = true;
		while (x+1<=7 && !board.squareOccupied(new Coord(x+1, y), color) && done) {
			attacks.add(new Coord(x+1, y));
			x++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while(x-1>=0 && !board.squareOccupied(new Coord(x-1, y), color) && done) {
			attacks.add(new Coord(x-1, y));
			x--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		
		while (y+1<=7 && !board.squareOccupied(new Coord(x, y+1), color) && done) {
			attacks.add(new Coord(x, y+1));
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while (y-1>=0 && !board.squareOccupied(new Coord(x, y-1), color) && done) {
			attacks.add(new Coord(x, y-1));
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		x = location.X();
		y = location.Y();
		done = true;
		
		
		
		while (x+1<=7 && y+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), color) && done) {
			attacks.add(new Coord(x+1, y+1));
			x++;
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while(x-1>=0 && y+1<=7 && !board.squareOccupied(new Coord(x-1, y+1), color) && done) {
			attacks.add(new Coord(x-1, y+1));
			x--;
			y++;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
				
		while (x+1<=7 && y-1>=0 && !board.squareOccupied(new Coord(x+1, y-1), color) && done) {
			attacks.add(new Coord(x+1, y-1));
			x++;
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		done = true;
		x = location.X();
		y = location.Y();
		
		while (x-1>=0 && y-1>=0 && !board.squareOccupied(new Coord(x-1, y-1), color) && done) {
			attacks.add(new Coord(x-1, y-1));
			x--;
			y--;
			// check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Coord(x, y)))
				done = false;
		}
		
		return attacks;
	}
}