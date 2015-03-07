/**
 * Class representing a Rook piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

public class Rook implements Piece
{
	private String id;
	private String color;
	private Point location;
	private LinkedList<Point> validMoves;
	private boolean hasMoved;
	
	public Rook(String id, String color, Point location, boolean hasMoved)
	{
		this.id = id;
		this.color = color;
		this.location = location;
		this.hasMoved = hasMoved;
		validMoves = new LinkedList<Point>();
	}
	
	public String getID()
	{
		return id;
	}
	
	public String getColor()
	{	
		return color;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public void setLocation(Point p)
	{
		location = p;
	}
	
	public void setHasMoved(boolean hasMoved)
	{
		this.hasMoved = hasMoved;
	}
	
	//Calculates the point at which to draw the piece.
	public int drawX()
	{
		return (int)location.getX() * 75;
	}
	public int drawY()
	{
		return (7 - (int)location.getY()) * 75;
	}
	
	//Check is the list of valid moves contains the desired move.
	public boolean validMove(Point p)
	{
		if (validMoves.contains(p))
			return true;
		else 
			return false;
	}
	
	public void determineValidMoves(Board board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//Alright, so. 4 directions, 4 while loops. 
		//while next square is on the board and not occupied
		//if its occupied by opposite piece, include that square and end.
		//if its occupied by same piece, don't include that square.
		boolean done = true;
		while (x+1<=7 && !board.squareOccupied(new Point(x+1, y), color) && done)
		{
			validMoves.add(new Point(x+1, y));
			x++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		
		while(x-1>=0 && !board.squareOccupied(new Point(x-1, y), color) && done)
		{
			validMoves.add(new Point(x-1, y));
			x--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		
		
		while (y+1<=7 && !board.squareOccupied(new Point(x, y+1), color) && done)
		{
			validMoves.add(new Point(x, y+1));
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		y = (int)location.getY();
		
		while (y-1>=0 && !board.squareOccupied(new Point(x, y-1), color) && done)
		{
			validMoves.add(new Point(x, y-1));
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
	}
		
	//Check if the piece has moved yet.
	public boolean hasItMoved()
	{
		return hasMoved;
	}
	
	//Moves a piece. Assumes the move is valid.
	public void move(Board board, Point p)
	{
		hasMoved = true;
		location = p;
	}
	
	//Returns a list of squares that the piece can attack
	public LinkedList<Point> attackSquares(Board board)
	{
		determineValidMoves(board);
		return validMoves;
	}
}