/**
 * Class representing a Pawn piece
 * 
 * Author: Ian Patel
 */

import java.util.*;

public class Pawn implements Piece {
	private String id;
	private String color;
	private Coord location;
	private LinkedList<Move> validMoves;
	private boolean hasMoved;
	
	public Pawn(String id, String color, Coord location, boolean hasMoved) {
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
		
		// This will have to be color dependent I think.
		if (color == "white") {
			// Add special case if the pawn hasn't moved.
			if (!hasMoved && !board.squareOccupiedPeriod(new Coord(x, y+1)) && !board.squareOccupiedPeriod(new Coord(x, y+2)))
				validMoves.add(new Move(location, new Coord(x, y+2)));
			
			// Move forward
			if (y+1<=7 && !board.squareOccupiedPeriod(new Coord(x, y+1))) {
				if (y == 6) {
					validMoves.add(new Move(location, new Coord(x, y+1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x, y+1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x, y+1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x, y+1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x, y+1)));
				}
			}

			// Diagonal captures
			if (x+1<=7 && y+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), "white") && board.squareOccupiedPeriod(new Coord(x+1, y+1))) {
				
				if (y == 6) {
					validMoves.add(new Move(location, new Coord(x+1, y+1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x+1, y+1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x+1, y+1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x+1, y+1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x+1, y+1)));
				}
			}

			if (x-1>=0 && y+1<=7 && !board.squareOccupied(new Coord(x-1, y+1), "white") && board.squareOccupiedPeriod(new Coord(x-1, y+1))) {
				
				if (y == 6) {
					validMoves.add(new Move(location, new Coord(x-1, y+1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x-1, y+1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x-1, y+1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x-1, y+1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x-1, y+1)));
				}
			}
		}
		else {
			// Add special case if the pawn hasn't moved.
			if (!hasMoved && !board.squareOccupiedPeriod(new Coord(x, y-1)) && !board.squareOccupiedPeriod(new Coord(x, y-2)))
				validMoves.add(new Move(location, new Coord(x, y-2)));
			
			// Move forward
			if (y-1<=7 && !board.squareOccupiedPeriod(new Coord(x, y-1))) {
				if (y == 1) {
					validMoves.add(new Move(location, new Coord(x, y-1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x, y-1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x, y-1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x, y-1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x, y-1)));
				}
			}
			
			// Diagonal captures
			if (x+1<=7 && y-1<=7 && !board.squareOccupied(new Coord(x+1, y-1), "black") && board.squareOccupiedPeriod(new Coord(x+1, y-1))) {
				if (y == 1) {
					validMoves.add(new Move(location, new Coord(x+1, y-1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x+1, y-1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x+1, y-1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x+1, y-1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x+1, y-1)));
				}
			}
			
			if (x-1>=0 && y-1<=7 && !board.squareOccupied(new Coord(x-1, y-1), "black") && board.squareOccupiedPeriod(new Coord(x-1, y-1))) {
				if (y == 1) {
					validMoves.add(new Move(location, new Coord(x-1, y-1), true, "Queen"));
					validMoves.add(new Move(location, new Coord(x-1, y-1), true, "Rook"));
					validMoves.add(new Move(location, new Coord(x-1, y-1), true, "Knight"));
					validMoves.add(new Move(location, new Coord(x-1, y-1), true, "Bishop"));
				}
				else {
					validMoves.add(new Move(location, new Coord(x-1, y-1)));
				}
			}
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
	public void move(Board board, Coord p) {
		hasMoved = true;
		location = p;
	}
	
	// Returns a list of squares that the piece can attack
	public LinkedList<Coord> attackSquares(Board board) {
		LinkedList<Coord> attacks = new LinkedList<Coord>();
		int x = location.X();
		int y = location.Y();
		
		// This will have to be color dependent I think.
		if (color.equals("white")) {
			// Diagonal captures
			if (x+1<=7 && y+1<=7 && !board.squareOccupied(new Coord(x+1, y+1), "white") && board.squareOccupiedPeriod(new Coord(x+1, y+1)))
				attacks.add(new Coord(x+1, y+1));

			if (x-1>=0 && y+1<=7 && !board.squareOccupied(new Coord(x-1, y+1), "white") && board.squareOccupiedPeriod(new Coord(x-1, y+1)))
				attacks.add(new Coord(x-1, y+1));
		}
		else {	
			// Diagonal captures
			if (x+1<=7 && y-1<=7 && !board.squareOccupied(new Coord(x+1, y-1), "black") && board.squareOccupiedPeriod(new Coord(x+1, y-1)))
				attacks.add(new Coord(x+1, y-1));
			
			if (x-1>=0 && y-1<=7 && !board.squareOccupied(new Coord(x-1, y-1), "black") && board.squareOccupiedPeriod(new Coord(x-1, y-1)))
				attacks.add(new Coord(x-1, y-1));
		}
		
		return attacks;
	}
}