/**
 * Class representing a King piece
 * 
 * Author: Ian Patel
 */

import java.util.*;

public class King implements Piece {
	private String id;
	private String color;
	private Coord location;
	private LinkedList<Move> validMoves;
	private boolean hasMoved;
	
	public King(String id, String color, Coord location, boolean hasMoved) {
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
	
	// Calculates the point at which to draw the piece.
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
		
		// Create a list of invalid positions (due to checking)
		LinkedList<Coord> invalidMoves = new LinkedList<Coord>();
		
		// Search the whole board
		for (int i = 0; i <= 7; i++) {
			for (int j = 0; j <= 7; j++) {
				// Save the piece in the current square
				Piece current = board.getPiece(new Coord(i, j ));
				
				// if there is a piece here...
				if (current != null) {
					// if the piece is of a different color...
					if (!current.getColor().equals(color)) {
						invalidMoves.addAll(current.attackSquares(board));
					}
				}
			}
		}
		
		// Check top three spaces. King can't move into check
		if (y+1<=7) {
			if (!board.squareOccupied(new Coord(x, y+1), color) && !invalidMoves.contains(new Coord(x, y+1)))
				validMoves.add(new Move(location, new Coord(x, y+1)));
			
			if (x-1>=0 && !board.squareOccupied(new Coord(x-1, y+1), color) && !invalidMoves.contains(new Coord(x-1, y+1)))
				validMoves.add(new Move(location, new Coord(x-1, y+1)));
			
			if (x+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), color) && !invalidMoves.contains(new Coord(x+1, y+1)))
				validMoves.add(new Move(location, new Coord(x+1, y+1)));
		}
		
		// Check bottom three spaces. King can't move into check
		if (y-1>=0) {
			if (!board.squareOccupied(new Coord(x, y-1), color) && !invalidMoves.contains(new Coord(x, y-1)))
				validMoves.add(new Move(location, new Coord(x, y-1)));
			
			if (x-1>=0 && !board.squareOccupied(new Coord(x-1, y-1), color) && !invalidMoves.contains(new Coord(x-1, y-1)))
				validMoves.add(new Move(location, new Coord(x-1, y-1)));
			
			if (x+1<=7 && !board.squareOccupied(new Coord(x+1, y-1), color) && !invalidMoves.contains(new Coord(x+1, y-1)))
				validMoves.add(new Move(location, new Coord(x+1, y-1)));
		}
			
		// Check left and right spaces. King can't move into check
		if (x+1<=7)
			if (!board.squareOccupied(new Coord(x+1, y), color) && !invalidMoves.contains(new Coord(x+1, y)))
				validMoves.add(new Move(location, new Coord(x+1, y)));
			
		if (x-1>=0)
			if (!board.squareOccupied(new Coord(x-1, y), color) && !invalidMoves.contains(new Coord(x-1, y)))
				validMoves.add(new Move(location, new Coord(x-1, y)));
			
		// Check Castling (can't castle through/into check)
		if (!hasMoved) {
			x = location.X();
			y = location.Y();
			if (board.getPiece(new Coord(x-4, y)) != null)
				if (!board.getPiece(new Coord(x-4, y)).hasItMoved() && !board.squareOccupiedPeriod(new Coord(x-1, y)) && !invalidMoves.contains(new Coord(x-1, y))
																	&& !board.squareOccupiedPeriod(new Coord(x-2, y)) && !invalidMoves.contains(new Coord(x-2, y))
																	&& !board.squareOccupiedPeriod(new Coord(x-3, y)))
					validMoves.add(new Move(location, new Coord(x-2, y)));
					
			if (board.getPiece(new Coord(x+3, y)) != null)
				if (!board.getPiece(new Coord(x+3, y)).hasItMoved() && !board.squareOccupiedPeriod(new Coord(x+1, y)) && !invalidMoves.contains(new Coord(x+1, y))
																	&& !board.squareOccupiedPeriod(new Coord(x+2, y)) && !invalidMoves.contains(new Coord(x+2, y)))
					validMoves.add(new Move(location, new Coord(x+2, y)));
		}
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
	// Castling is handled in Board class.
	public void move(Board board, Coord p) {
		hasMoved = true;
		location = p;
	}

	// Returns a list of squares that the piece can attack
	public LinkedList<Coord> attackSquares(Board board) {
		LinkedList<Coord> attacks = new LinkedList<Coord>();
		int x = location.X();
		int y = location.Y();
		
		// Check top three spaces
		if (y+1<=7) {
			if (!board.squareOccupied(new Coord(x, y+1), color))
				attacks.add(new Coord(x, y+1));
			
			if (x-1>=0 && !board.squareOccupied(new Coord(x-1, y+1), color))
				attacks.add(new Coord(x-1, y+1));
			
			if (x+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), color))
				attacks.add(new Coord(x+1, y+1));
		}
		
		// Check bottom three spaces
		if (y-1>=0) {
			if (!board.squareOccupied(new Coord(x, y-1), color))
				attacks.add(new Coord(x, y-1));
			
			if (x-1>=0 && !board.squareOccupied(new Coord(x-1, y-1), color))
				attacks.add(new Coord(x-1, y-1));
			
			if (x+1<=7 && !board.squareOccupied(new Coord(x+1, y-1), color))
				attacks.add(new Coord(x+1, y-1));
		}
			
		// Check left and right spaces
		if (x+1<=7)
			if (!board.squareOccupied(new Coord(x+1, y), color))
				attacks.add(new Coord(x+1, y));
			
		if (x-1>=0)
			if (!board.squareOccupied(new Coord(x-1, y), color))
				attacks.add(new Coord(x-1, y));
			
		return attacks;
	}
}