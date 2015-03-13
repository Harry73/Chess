/**
 * Class containing the static methods to determine 
 * which move a computer opponent should perform.
 * 
 * Author: Ian Patel
 */

import java.awt.Point;
import java.util.*;

public class AI
{
	public static void move(String mode, int level, Board board)
	{
		LinkedList<Piece> pieces = new LinkedList<Piece>();
		LinkedList<Move> availableMoves = new LinkedList<Move>();
		
		if (mode.equals("white"))
		{
			for (int i = 0; i <= 7; i++)
			{
				for (int j = 0; j <= 7; j++)
				{
					Point current = new Point(i, j);
					if (board.getPiece(current) != null && board.getPiece(current).getColor().equals("white"))
					{
						//Creating list of white pieces.
						pieces.add(board.getPiece(current));
						LinkedList<Point> validMoves = board.getPiece(current).getValidMoves(board);
						
						//Add all valid moves of the current piece to the linked list.
						for (int k = 0; k < validMoves.size(); k++)
						{
							availableMoves.add(new Move(new Point(i, j), validMoves.get(k)));
						}
					}
				}
			}
		}
		else if (mode.equals("black"))
		{
			for (int i = 0; i <= 7; i++)
			{
				for (int j = 0; j <= 7; j++)
				{
					Point current = new Point(i, j);
					if (board.getPiece(current) != null && board.getPiece(current).getColor().equals("black"))
					{
						//Creating list of black pieces.
						pieces.add(board.getPiece(current));
						LinkedList<Point> validMoves = board.getPiece(current).getValidMoves(board);
						
						//Add all valid moves of the current piece to the linked list.
						for (int k = 0; k < validMoves.size(); k++)
						{
							availableMoves.add(new Move(new Point(i, j), validMoves.get(k)));
						}
					}
				}
			}
		}

		Move selectedMove = null;
		
		//Call appropriate AI method.
		if (level == 1)
			selectedMove = level1(availableMoves, board);
		else if (level == 2)
			selectedMove = level2(availableMoves, board);
		
		//Perform the chosen move.
		board.move(selectedMove.from(), selectedMove.to());
	}
	
	//Picks a random move from the list of available moves.
	public static Move level1(LinkedList<Move> availableMoves, Board board)
	{
		Random rn = new Random();
		int i = 0;
		boolean done = false;
		
		while (!done)
		{
			i = rn.nextInt(availableMoves.size());
			Piece current = board.getPiece(availableMoves.get(i).from());
			if (current.validMove(board, availableMoves.get(i).to()))
				done = true;
		}
		
		return availableMoves.get(i);
	}
	
	//Evaluate the resulting position of each move and pick the best. 
	public static Move level2(LinkedList<Move> availableMoves, Board board)
	{
		return level1(availableMoves, board);
	}
	
	//Recreates the board and applies a test move, returning the board.
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
						testBoard[i][j] = new King("queen", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("rook"))
						testBoard[i][j] = new King("rook", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("bishop"))
						testBoard[i][j] = new King("bishop", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("knight"))
						testBoard[i][j] = new King("knight", current.getColor(), current.getLocation(), current.hasItMoved());
					else if (current.getID().equals("pawn"))
						testBoard[i][j] = new King("pawn", current.getColor(), current.getLocation(), current.hasItMoved());
				}
			}
		}
		
		//This is not good enough. Need to account for castling/promotion
		testBoard[(int)a.getX()][(int)a.getY()].setLocation(b);
		testBoard[(int)b.getX()][(int)b.getY()] = testBoard[(int)a.getX()][(int)a.getY()];
		testBoard[(int)a.getX()][(int)a.getY()] = null;
		
		return testBoard;
	}

	//Returns a numeric value representing the value of the current board
	public static int evaluate(Piece[][] board)
	{
		return 0;
	}
}