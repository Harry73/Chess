/**
 * Interface to define the methods of a chess piece
 * 
 * Author: Ian Patel
 */

import java.util.*;

//Interface describing a chess piece
public interface Piece {
	public String getID();
	public String getColor();
	public Coord getLocation();
	public void setLocation(Coord p);
	public void setHasMoved(boolean hasMoved);
	public int drawX();
	public int drawY();
	public void determineValidMoves(Board board);
	public LinkedList<Move> getValidMoves(Board board);
	public boolean hasItMoved();
	public void move(Board board, Coord p);
	public LinkedList<Coord> attackSquares(Board board);
}