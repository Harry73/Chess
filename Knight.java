/**
 * Class representing a Knight piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

public class Knight implements Piece
{
	private String id;
	private String color;
	private Point location;
	private LinkedList<Point> validMoves;
	private boolean hasMoved;
	
	public Knight(String id, String color, Point location, boolean hasMoved)
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
	
	//Creates a list of squares the piece can move to.
	public void determineValidMoves(Board board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		if (x+1<=7 && y+2<=7)
			if (!board.squareOccupied(new Point(x+1, y+2), color))
				validMoves.add(new Point(x+1, y+2));
			
		if (x+2<=7 && y+1<=7)
			if (!board.squareOccupied(new Point(x+2, y+1), color))
				validMoves.add(new Point(x+2, y+1));
		
		if (x+2<=7 && y-1>=0)
			if (!board.squareOccupied(new Point(x+2, y-1), color))
				validMoves.add(new Point(x+2, y-1));
		
		if (x+1<=7 && y-2>=0)
			if (!board.squareOccupied(new Point(x+1, y-2), color))
				validMoves.add(new Point(x+1, y-2));
		
		if (x-1>=0 && y-2>=0)
			if (!board.squareOccupied(new Point(x-1, y-2), color))
				validMoves.add(new Point(x-1, y-2));
			
		if (x-2>=0 && y-1>=0)
			if (!board.squareOccupied(new Point(x-2, y-1), color))
				validMoves.add(new Point(x-2, y-1));
			
		if (x-2>=0 && y+1<=7)
			if (!board.squareOccupied(new Point(x-2, y+1), color))
				validMoves.add(new Point(x-2, y+1));
			
		if (x-1>=0 && y+2<=7)
			if (!board.squareOccupied(new Point(x-1, y+2), color))
				validMoves.add(new Point(x-1, y+2));
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