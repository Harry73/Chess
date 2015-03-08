/**
 * Class representing a Pawn piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

public class Pawn implements Piece
{
	private String id;
	private String color;
	private Point location;
	private LinkedList<Point> validMoves;
	private boolean hasMoved;
	
	public Pawn(String id, String color, Point location, boolean hasMoved)
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
		
		//This will have to be color dependent I think.
		if (color == "white")
		{
			//Add special case if the pawn hasn't moved.
			if (!hasMoved && !board.squareOccupiedPeriod(new Point(x, y+1)) && !board.squareOccupiedPeriod(new Point(x, y+2)))
				validMoves.add(new Point(x, y+2));

			//Move forward
			if (y+1<=7 && !board.squareOccupiedPeriod(new Point(x, y+1)))
				validMoves.add(new Point(x, y+1));

			//Diagonal captures
			if (x+1<=7 && y+1<=7 && !board.squareOccupied(new Point(x+1, y+1), "white") && board.squareOccupiedPeriod(new Point(x+1, y+1)))
				validMoves.add(new Point(x+1, y+1));

			if (x-1>=0 && y+1<=7 && !board.squareOccupied(new Point(x-1, y+1), "white") && board.squareOccupiedPeriod(new Point(x-1, y+1)))
				validMoves.add(new Point(x-1, y+1));
		}
		else
		{
			//Add special case if the pawn hasn't moved.
			if (!hasMoved && !board.squareOccupiedPeriod(new Point(x, y-1)) && !board.squareOccupiedPeriod(new Point(x, y-2)))
				validMoves.add(new Point(x, y-2));
			
			//Move forward
			if (y-1<=7 && !board.squareOccupiedPeriod(new Point(x, y-1)))
				validMoves.add(new Point(x, y-1));
			
			//Diagonal captures
			if (x+1<=7 && y-1<=7 && !board.squareOccupied(new Point(x+1, y-1), "black") && board.squareOccupiedPeriod(new Point(x+1, y-1)))
				validMoves.add(new Point(x+1, y-1));
			
			if (x-1>=0 && y-1<=7 && !board.squareOccupied(new Point(x-1, y-1), "black") && board.squareOccupiedPeriod(new Point(x-1, y-1)))
				validMoves.add(new Point(x-1, y-1));
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
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//This will have to be color dependent I think.
		if (color.equals("white"))
		{
			//Diagonal captures
			if (x+1<=7 && y+1<=7 && !board.squareOccupied(new Point(x+1, y+1), "white") && board.squareOccupiedPeriod(new Point(x+1, y+1)))
				validMoves.add(new Point(x+1, y+1));

			if (x-1>=0 && y+1<=7 && !board.squareOccupied(new Point(x-1, y+1), "white") && board.squareOccupiedPeriod(new Point(x-1, y+1)))
				validMoves.add(new Point(x-1, y+1));
		}
		else
		{	
			//Diagonal captures
			if (x+1<=7 && y-1<=7 && !board.squareOccupied(new Point(x+1, y-1), "black") && board.squareOccupiedPeriod(new Point(x+1, y-1)))
				validMoves.add(new Point(x+1, y-1));
			
			if (x-1>=0 && y-1<=7 && !board.squareOccupied(new Point(x-1, y-1), "black") && board.squareOccupiedPeriod(new Point(x-1, y-1)))
				validMoves.add(new Point(x-1, y-1));
		}
		
		return validMoves;
	}
	
	//Returns a list of squares that the piece can attack
	public LinkedList<Point> attackSquares(Piece[][] board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//This will have to be color dependent I think.
		if (color.equals("white"))
		{
			//Diagonal captures
			if (x+1<=7 && y+1<=7 && board[x+1][y+1] != null && board[x+1][y+1].getColor().equals("black"))
				validMoves.add(new Point(x+1, y+1));

			if (x-1>=0 && y+1<=7 && board[x-1][y+1] != null && board[x-1][y+1].getColor().equals("black"))
				validMoves.add(new Point(x-1, y+1));
		}
		else
		{	
			//Diagonal captures
			if (x+1<=7 && y-1<=7 && 	board[x+1][y-1] != null && board[x+1][y-1].getColor().equals("white"))
				validMoves.add(new Point(x+1, y-1));
			
			if (x-1>=0 && y-1<=7 && board[x-1][y-1] != null && board[x-1][y-1].getColor().equals("white"))
				validMoves.add(new Point(x-1, y-1));
		}
		
		return validMoves;
	}
}