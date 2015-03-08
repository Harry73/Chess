/**
 * Class representing a King piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

public class King implements Piece
{
	private String id;
	private String color;
	private Point location;
	private LinkedList<Point> validMoves;
	private boolean hasMoved;
	
	public King(String id, String color, Point location, boolean hasMoved)
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
		
		//Create a list of invalid positions (due to checking)
		LinkedList<Point> invalidMoves = new LinkedList<Point>();
		
		//Search the whole board
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				//Save the piece in the current square
				Piece current = board.getPiece(new Point(i, j ));
				
				//if there is a piece here...
				if (current != null)
				{
					//if the piece is of a different color...
					if (!current.getColor().equals(color))
					{
						invalidMoves.addAll(current.attackSquares(board));
					}
				}
			}
		}
		
		//Check top three spaces. King can't move into check
		if (y+1<=7)
		{
			if (!board.squareOccupied(new Point(x, y+1), color) && !invalidMoves.contains(new Point(x, y+1)))
				validMoves.add(new Point(x, y+1));
			
			if (x-1>=0 && !board.squareOccupied(new Point(x-1, y+1), color) && !invalidMoves.contains(new Point(x-1, y+1)))
				validMoves.add(new Point(x-1, y+1));
			
			if (x+1<=7 && !board.squareOccupied(new Point(x+1, y+1), color) && !invalidMoves.contains(new Point(x+1, y+1)))
				validMoves.add(new Point(x+1, y+1));
		}
		
		//Check bottom three spaces. King can't move into check
		if (y-1>=0)
		{
			if (!board.squareOccupied(new Point(x, y-1), color) && !invalidMoves.contains(new Point(x, y-1)))
				validMoves.add(new Point(x, y-1));
			
			if (x-1>=0 && !board.squareOccupied(new Point(x-1, y-1), color) && !invalidMoves.contains(new Point(x-1, y-1)))
				validMoves.add(new Point(x-1, y-1));
			
			if (x+1<=7 && !board.squareOccupied(new Point(x+1, y-1), color) && !invalidMoves.contains(new Point(x+1, y-1)))
				validMoves.add(new Point(x+1, y-1));
		}
			
		//Check left and right spaces. King can't move into check
		if (x+1<=7)
			if (!board.squareOccupied(new Point(x+1, y), color) && !invalidMoves.contains(new Point(x+1, y)))
				validMoves.add(new Point(x+1, y));
			
		if (x-1>=0)
			if (!board.squareOccupied(new Point(x-1, y), color) && !invalidMoves.contains(new Point(x-1, y)))
				validMoves.add(new Point(x-1, y));
			
		//Check Castling (can't castle through/into check)
		if (!hasMoved)
		{
			x = (int)location.getX();
			y = (int)location.getY();
			if (board.getPiece(new Point(x-4, y)) != null)
				if (!board.getPiece(new Point(x-4, y)).hasItMoved() && !board.squareOccupiedPeriod(new Point(x-1, y)) && !invalidMoves.contains(new Point(x-1, y))
																	&& !board.squareOccupiedPeriod(new Point(x-2, y)) && !invalidMoves.contains(new Point(x-2, y))
																	&& !board.squareOccupiedPeriod(new Point(x-3, y)))
					validMoves.add(new Point(x-2, y));
					
			if (board.getPiece(new Point(x+3, y)) != null)
				if (!board.getPiece(new Point(x+3, y)).hasItMoved() && !board.squareOccupiedPeriod(new Point(x+1, y)) && !invalidMoves.contains(new Point(x+1, y))
																	&& !board.squareOccupiedPeriod(new Point(x+2, y)) && !invalidMoves.contains(new Point(x+2, y)))
					validMoves.add(new Point(x+2, y));
		}
	}
	
	//Check if the piece has moved yet.
	public boolean hasItMoved()
	{
		return hasMoved;
	}
	
	//Moves a piece. Assumes the move is valid.
	//Castling is handled in Board class.
	public void move(Board board, Point p)
	{
		hasMoved = true;
		location = p;
	}

	public LinkedList<Point> attackSquares(Board board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//Check top three spaces
		if (y+1<=7)
		{
			if (!board.squareOccupied(new Point(x, y+1), color))
				validMoves.add(new Point(x, y+1));
			
			if (x-1>=0 && !board.squareOccupied(new Point(x-1, y+1), color))
				validMoves.add(new Point(x-1, y+1));
			
			if (x+1<=7 && !board.squareOccupied(new Point(x+1, y+1), color))
				validMoves.add(new Point(x+1, y+1));
		}
		
		//Check bottom three spaces
		if (y-1>=0)
		{
			if (!board.squareOccupied(new Point(x, y-1), color))
				validMoves.add(new Point(x, y-1));
			
			if (x-1>=0 && !board.squareOccupied(new Point(x-1, y-1), color))
				validMoves.add(new Point(x-1, y-1));
			
			if (x+1<=7 && !board.squareOccupied(new Point(x+1, y-1), color))
				validMoves.add(new Point(x+1, y-1));
		}
			
		//Check left and right spaces
		if (x+1<=7)
			if (!board.squareOccupied(new Point(x+1, y), color))
				validMoves.add(new Point(x+1, y));
			
		if (x-1>=0)
			if (!board.squareOccupied(new Point(x-1, y), color))
				validMoves.add(new Point(x-1, y));
			
		return validMoves;
	}

	public LinkedList<Point> attackSquares(Piece[][] board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//Check top three spaces
		if (y+1<=7)
		{
			if ((board[x][y+1] == null) || (!board[x][y+1].getColor().equals(color)))
				validMoves.add(new Point(x, y+1));
			
			if (x-1>=0 && ((board[x-1][y+1] == null) || (!board[x-1][y+1].getColor().equals(color))))
				validMoves.add(new Point(x-1, y+1));
			
			if (x+1<=7 && ((board[x+1][y+1] == null) || (!board[x+1][y+1].getColor().equals(color))))
				validMoves.add(new Point(x+1, y+1));
		}
		
		//Check bottom three spaces
		if (y-1>=0)
		{
			if ((board[x][y-1] == null) || (!board[x][y-1].getColor().equals(color)))
				validMoves.add(new Point(x, y-1));
			
			if (x-1>=0 && ((board[x-1][y-1] == null) || (!board[x-1][y-1].getColor().equals(color))))
				validMoves.add(new Point(x-1, y-1));
			
			if (x+1<=7 && ((board[x+1][y-1] == null) || (!board[x+1][y-1].getColor().equals(color))))
				validMoves.add(new Point(x+1, y-1));
		}
			
		//Check left and right spaces
		if (x+1<=7)
			if ((board[x+1][y] == null) || (!board[x+1][y].getColor().equals(color)))
				validMoves.add(new Point(x+1, y));
			
		if (x-1>=0)
			if ((board[x-1][y] == null) || (!board[x-1][y].getColor().equals(color)))
				validMoves.add(new Point(x-1, y));
			
		return validMoves;
	}
}