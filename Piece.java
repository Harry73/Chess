/**
 * Interface to define a chess piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

//Interface describing a chess piece
public interface Piece
{
	public String getID();
	public String getColor();
	public Point getLocation();
	public void setLocation(Point p);
	public void setHasMoved(boolean hasMoved);
	public int drawX();
	public int drawY();
	public boolean validMove(Point p);
	public void determineValidMoves(Board board);
	public boolean hasItMoved();
	public void move(Board board, Point p);
	public LinkedList<Point> attackSquares(Board board);
}