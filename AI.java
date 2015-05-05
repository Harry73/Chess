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
	public static void move(String color, int level, Board board)
	{
		LinkedList<Piece> pieces = new LinkedList<Piece>();
		LinkedList<Move> availableMoves = new LinkedList<Move>();
		
		if (color.equals("white"))
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
		else if (color.equals("black"))
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
		
		//Shuffle the list for purposes we will not speak of (level2 needs it)
		Collections.shuffle(availableMoves);

		Move selectedMove = null;
		
		//Call appropriate AI method.
		if (level == 1)
			selectedMove = level1(availableMoves, board, color);
		else if (level == 2)
			selectedMove = level2(availableMoves, board, color);
		else if (level == 3)
			selectedMove = level3(availableMoves, board, color);
		
		//Perform the chosen move.
		board.move(selectedMove.from(), selectedMove.to());
	}
	
	//Picks a random move from the list of available moves.
	public static Move level1(LinkedList<Move> availableMoves, Board board, String color)
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
	public static Move level2(LinkedList<Move> availableMoves, Board board, String color)
	{
		LinkedList<Integer> boardScores = new LinkedList<Integer>();
				
		//Iterate through all possible moves, create a testBoard for each, and evaluate the testBoard, save score in LinkedList
		for (int i = 0; i < availableMoves.size(); i++)
		{
			Piece[][] testBoard = testMove(board, availableMoves.get(i).from(), availableMoves.get(i).to());
			boardScores.add(evaluate(testBoard, color));
		}
		
		boolean done = false;
		int value = 0;
		
		//Force this process to repeat until a valid move is selected
		while (!done)
		{
			//Find maximum value in the boardScores LinkedList
			int max = -500;
			value = 0;
			for (int i = 0; i < boardScores.size(); i++)
			{
				if (boardScores.get(i) > max)
				{
					max = boardScores.get(i);
					value = i;
				}
			}
			
			Move chosen = availableMoves.get(value);
			if (board.getPiece(chosen.from()).validMove(board, chosen.to()))
				done = true;
			else
			{
				availableMoves.remove(value);
				boardScores.remove(value);
			}
		}
		
		return availableMoves.get(value);
	}

	//Do what level2 does but look 3 moves in instead of 1.
	public static Move level3(LinkedList<Move> availableMoves, Board board, String color)
	{
		
	}
	
	//Recreates the Board and applies a test move, returning the board as Piece array.
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
	
	//Recreates the Piece array and applies a test move, returning the new Piece array.
	public static Piece[][] testMove(Piece[][] board, Point a, Point b)
	{
		Piece[][] testBoard = new Piece[8][8];
		
		//Duplicate the board as testBoard
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board[i][j];
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

	//Returns a numeric value representing the value of the board passed in
	public static int evaluate(Piece[][] board, String color)
	{
		int score = 0;
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				if (board[i][j] != null)
				{
					if (board[i][j].getColor().equals(color))
					{
						if (board[i][j].getID() == "king")
							score += 200;
						else if (board[i][j].getID() == "queen")
							score += 9;
						else if (board[i][j].getID() == "rook")
							score += 5;
						else if (board[i][j].getID() == "bishop")
							score += 3;
						else if (board[i][j].getID() == "knight")
							score += 3;
						else if (board[i][j].getID() == "pawn")
							score += 1;
					}
					else
					{
						if (board[i][j].getID() == "king")
							score -= 200;
						else if (board[i][j].getID() == "queen")
							score -= 9;
						else if (board[i][j].getID() == "rook")
							score -= 5;
						else if (board[i][j].getID() == "bishop")
							score -= 3;
						else if (board[i][j].getID() == "knight")
							score -= 3;
						else if (board[i][j].getID() == "pawn")
							score -= 1;
					}
				}
			}
		}
		
		//System.out.println(score);
		return score;
	}
}