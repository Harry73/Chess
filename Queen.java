/**
 * Class representing a Queen piece
 * 
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.Point;

public class Queen implements Piece
{
	private String id;
	private String color;
	private Point location;
	private LinkedList<Point> validMoves;
	private boolean hasMoved;
	
	public Queen(String id, String color, Point location, boolean hasMoved)
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
	
	//Check is the list of valid moves contains the desired move and that the king is not in check
	public boolean validMove(Board board, Point p)
	{
		if (color.equals("white"))
		{
			if (!Check.checkWhite(board, location, p))
			{	
				if (validMoves.contains(p))
					return true;
				else 
					return false;
			}
		}
		else
		{
			if (!Check.checkBlack(board, location, p))
			{	
				if (validMoves.contains(p))
					return true;
				else 
					return false;
			}
		}
		
		return false;
	}
	
	public void determineValidMoves(Board board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//Combo of Rook and Bishop methods
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
		y = (int)location.getY();
		
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
		y = (int)location.getY();
		
		
		while (y+1<=7 && !board.squareOccupied(new Point(x, y+1), color) && done)
		{
			validMoves.add(new Point(x, y+1));
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while (y-1>=0 && !board.squareOccupied(new Point(x, y-1), color) && done)
		{
			validMoves.add(new Point(x, y-1));
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		x = (int)location.getX();
		y = (int)location.getY();
		done = true;
		
		
		
		while (x+1<=7 && y+1<=7 && !board.squareOccupied(new Point(x+1, y+1), color) && done)
		{
			validMoves.add(new Point(x+1, y+1));
			x++;
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while(x-1>=0 && y+1<=7 && !board.squareOccupied(new Point(x-1, y+1), color) && done)
		{
			validMoves.add(new Point(x-1, y+1));
			x--;
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
				
		while (x+1<=7 && y-1>=0 && !board.squareOccupied(new Point(x+1, y-1), color) && done)
		{
			validMoves.add(new Point(x+1, y-1));
			x++;
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board.squareOccupiedPeriod(new Point(x, y)))
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while (x-1>=0 && y-1>=0 && !board.squareOccupied(new Point(x-1, y-1), color) && done)
		{
			validMoves.add(new Point(x-1, y-1));
			x--;
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
	
	//Returns a list of squares that the piece can attack
	public LinkedList<Point> attackSquares(Piece[][] board)
	{
		validMoves.clear();
		int x = (int)location.getX();
		int y = (int)location.getY();
		
		//Combo of Rook and Bishop methods
		boolean done = true;
		while (x+1<=7 && ((board[x+1][y] == null) || (!board[x+1][y].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x+1, y));
			x++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while(x-1>=0 && ((board[x-1][y] == null) || (!board[x-1][y].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x-1, y));
			x--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		
		while (y+1<=7 && ((board[x][y+1] == null) || (!board[x][y+1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x, y+1));
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while (y-1>=0 && ((board[x][y-1] == null) || (!board[x][y-1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x, y-1));
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		x = (int)location.getX();
		y = (int)location.getY();
		done = true;
		
		
		
		while (x+1<=7 && y+1<=7 && ((board[x+1][y+1] == null) || (!board[x+1][y+1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x+1, y+1));
			x++;
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while(x-1>=0 && y+1<=7 && ((board[x-1][y+1] == null) || (!board[x-1][y+1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x-1, y+1));
			x--;
			y++;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
				
		while (x+1<=7 && y-1>=0 && ((board[x+1][y-1] == null) || (!board[x+1][y-1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x+1, y-1));
			x++;
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		done = true;
		x = (int)location.getX();
		y = (int)location.getY();
		
		while (x-1>=0 && y-1>=0 && ((board[x-1][y-1] == null) || (!board[x-1][y-1].getColor().equals(color))) && done)
		{
			validMoves.add(new Point(x-1, y-1));
			x--;
			y--;
			//check if this square actually had a opposite piece. if it did, end.
			if (board[x][y] != null)
				done = false;
		}
		
		return validMoves;
	}
}