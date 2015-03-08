/**
 * Class containing static methods to determine if the king is in check
 * and to create a temporary board to test moves on.
 *
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.*;

public class Check
{
	private static Point findKing(Board board, String color)
	{
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board.getPiece(new Point(i, j));
				if (current != null && current.getID().equals("king") && current.getColor().equals(color))
					return (new Point(i, j));
			}
		}
		
		return null;
	}
	
	public static boolean checkWhite(Board board)
	{
		Point kingPoint = findKing(board, "white");
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board.getPiece(new Point(i, j));
				if (current != null && current.getColor().equals("black"))
				{
					LinkedList<Point> attacks = current.attackSquares(board);
					if (attacks.contains(kingPoint))
						return true;
				}				
			}
		}
		
		return false;
	}
	
	public static boolean checkBlack(Board board)
	{
		Point kingPoint = findKing(board, "black");
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board.getPiece(new Point(i, j));
				if (current != null && current.getColor().equals("white"))
				{
					LinkedList<Point> attacks = current.attackSquares(board);
					if (attacks.contains(kingPoint))
						return true;
				}				
			}
		}
		
		return false;
	}
	
	public static boolean checkWhite(Board board, Point a, Point b)
	{
		Point kingPoint = findKing(board, "white");
		Piece[][] testBoard = testMove(board, a, b);
		
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = testBoard[i][j];
				
				if (current != null && current.getColor().equals("black"))
				{
					LinkedList<Point> attacks = current.attackSquares(testBoard);
					if (attacks.contains(kingPoint))
						return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean checkBlack(Board board, Point a, Point b)
	{
		Point kingPoint = findKing(board, "black");
		Piece[][] testBoard = testMove(board, a, b);
		
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = testBoard[i][j];
				if (current != null && current.getColor().equals("white"))
				{
					LinkedList<Point> attacks = current.attackSquares(testBoard);
					if (attacks.contains(kingPoint))
						return true;
				}
			}
		}
		
		return false;
	}	
	
	public static Piece[][] testMove(Board board, Point a, Point b)
	{
		Piece[][] testBoard = new Piece[8][8];
		
		//Duplicate the board as testBoard
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board.getPiece(new Point(i, j));
				if (current != null)
				{
					if (current.getID().equals("king"))
						testBoard[i][j] = new King("king", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("queen"))
						testBoard[i][j] = new Queen("queen", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("rook"))
						testBoard[i][j] = new Rook("rook", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("bishop"))
						testBoard[i][j] = new Bishop("bishop", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("knight"))
						testBoard[i][j] = new Knight("knight", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("pawn"))
						testBoard[i][j] = new Pawn("pawn", current.getColor(), current.getLocation(), current.hasItMoved());
				}
			}
		}
		
		testBoard[(int)a.getX()][(int)a.getY()].setLocation(b);
		testBoard[(int)b.getX()][(int)b.getY()] = testBoard[(int)a.getX()][(int)a.getY()];
		testBoard[(int)a.getX()][(int)a.getY()] = null;
		
		return testBoard;
	}
}