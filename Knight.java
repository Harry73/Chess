/**
 * Class representing a Knight piece
 * 
 * Author: Ian Patel
 */

import java.util.*;

public class Knight implements Piece {
	private String id;
	private String color;
	private Coord location;
	private LinkedList<Move> validMoves;
	private boolean hasMoved;
	
	public Knight(String id, String color, Coord location, boolean hasMoved) {
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
		
	// Creates a list of squares the piece can move to.
	public void determineValidMoves(Board board) {
		validMoves.clear();
		int x = location.X();
		int y = location.Y();
		if (x+1<=7 && y+2<=7)
			if (!board.squareOccupied(new Coord(x+1, y+2), color))
				validMoves.add(new Move(location, new Coord(x+1, y+2)));
			
		if (x+2<=7 && y+1<=7)
			if (!board.squareOccupied(new Coord(x+2, y+1), color))
				validMoves.add(new Move(location, new Coord(x+2, y+1)));
		
		if (x+2<=7 && y-1>=0)
			if (!board.squareOccupied(new Coord(x+2, y-1), color))
				validMoves.add(new Move(location, new Coord(x+2, y-1)));
		
		if (x+1<=7 && y-2>=0)
			if (!board.squareOccupied(new Coord(x+1, y-2), color))
				validMoves.add(new Move(location, new Coord(x+1, y-2)));
		
		if (x-1>=0 && y-2>=0)
			if (!board.squareOccupied(new Coord(x-1, y-2), color))
				validMoves.add(new Move(location, new Coord(x-1, y-2)));
			
		if (x-2>=0 && y-1>=0)
			if (!board.squareOccupied(new Coord(x-2, y-1), color))
				validMoves.add(new Move(location, new Coord(x-2, y-1)));
			
		if (x-2>=0 && y+1<=7)
			if (!board.squareOccupied(new Coord(x-2, y+1), color))
				validMoves.add(new Move(location, new Coord(x-2, y+1)));
			
		if (x-1>=0 && y+2<=7)
			if (!board.squareOccupied(new Coord(x-1, y+2), color))
				validMoves.add(new Move(location, new Coord(x-1, y+2)));
	}
	
	public LinkedList<Move> getValidMoves(Board board) {
		determineValidMoves(board);
		return validMoves;
	}

	// Check if the piece has moved yet.
	public boolean hasItMoved() {
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
		if (x+1<=7 && y+2<=7)
			if (!board.squareOccupied(new Coord(x+1, y+2), color))
				attacks.add(new Coord(x+1, y+2));
			
		if (x+2<=7 && y+1<=7)
			if (!board.squareOccupied(new Coord(x+2, y+1), color))
				attacks.add(new Coord(x+2, y+1));
		
		if (x+2<=7 && y-1>=0)
			if (!board.squareOccupied(new Coord(x+2, y-1), color))
				attacks.add(new Coord(x+2, y-1));
		
		if (x+1<=7 && y-2>=0)
			if (!board.squareOccupied(new Coord(x+1, y-2), color))
				attacks.add(new Coord(x+1, y-2));
		
		if (x-1>=0 && y-2>=0)
			if (!board.squareOccupied(new Coord(x-1, y-2), color))
				attacks.add(new Coord(x-1, y-2));
			
		if (x-2>=0 && y-1>=0)
			if (!board.squareOccupied(new Coord(x-2, y-1), color))
				attacks.add(new Coord(x-2, y-1));
			
		if (x-2>=0 && y+1<=7)
			if (!board.squareOccupied(new Coord(x-2, y+1), color))
				attacks.add(new Coord(x-2, y+1));
			
		if (x-1>=0 && y+2<=7)
			if (!board.squareOccupied(new Coord(x-1, y+2), color))
				attacks.add(new Coord(x-1, y+2));
			
		return attacks;
	}
}