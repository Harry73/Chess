/**
 * A replica of the Board class with less functionality. 
 * Intended to be a clone of a Board object and allow for test moves to be applied. 
 *
 * Author: Ian Patel
 */
 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class TestBoard
{
	//Pieces in the board
	Piece[] whitePawn = new Pawn[8];
	Piece[] blackPawn = new Pawn[8];
	Rook[] whiteRook = new Rook[2];
	Rook[] blackRook = new Rook[2];
	Knight[] whiteKnight = new Knight[2];
	Knight[] blackKnight = new Knight[2];
	Bishop[] whiteBishop = new Bishop[2];
	Bishop[] blackBishop = new Bishop[2];
	Queen whiteQueen;
	Queen blackQueen;
	King whiteKing;
	King blackKing;
	
	//Stores the actual state of the board
	Piece[][] board = new Piece[8][8];
	
	//Points for moving pieces and a little drawing.
	Point first;
	Point second;
	Point currentSquare;
	
	//For turns
	int turn = 1; //white's turn.
	String mode;
	boolean checkmate = false;
	
	//Instantiates all the pieces.
	public TestBoard(Board theBoard)
	{
		int whiteRookCounter = 0;
		int whiteBishopCounter = 0;
		int whiteKnightCounter = 0;
		int whitePawnCounter = 0;
		int blackRookCounter = 0;
		int blackBishopCounter = 0;
		int blackKnightCounter = 0;
		int blackPawnCounter = 0;
		
		//Duplicate the board as testBoard
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = theBoard.getPiece(new Point(i, j));
				if (current != null)
				{
					if (current.getID().equals("king"))
					{
						if (current.getColor() == "white")
						{
							whiteKing = new King("king", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteKing;
						}
						else
						{
							blackKing = new King("king", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackKing;
						}
					}
					else if (current.getID().equals("queen"))
					{
						if (current.getColor() == "white")
						{
							whiteQueen = new Queen("queen", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteQueen;
						}
						else
						{
							blackQueen = new Queen("queen", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackQueen;
						}
					}
					else if (current.getID().equals("rook"))
					{
						if (current.getColor() == "white")
						{
							whiteRook[whiteRookCounter] = new Rook("rook", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteRook[whiteRookCounter];
							whiteRookCounter++;
						}
						else
						{
							blackRook[blackRookCounter] = new Rook("rook", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackRook[blackRookCounter];
							blackRookCounter++;
						}
					}
					else if (current.getID().equals("bishop"))
					{
						if (current.getColor() == "white")
						{
							whiteBishop[whiteBishopCounter] = new Bishop("bishop", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteBishop[whiteBishopCounter];
							whiteBishopCounter++;
						}
						else
						{
							blackBishop[blackBishopCounter] = new Bishop("bishop", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackBishop[blackBishopCounter];
							blackBishopCounter++;
						}
					}
					else if (current.getID().equals("knight"))
					{
						if (current.getColor() == "white")
						{
							whiteKnight[whiteKnightCounter] = new Knight("knight", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteKnight[whiteKnightCounter];
							whiteKnightCounter++;
						}
						else
						{
							blackKnight[blackKnightCounter] = new Knight("knight", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackKnight[blackKnightCounter];
							blackKnightCounter++;
						}
					}
					else if (current.getID().equals("pawn"))
					{
						if (current.getColor() == "white")
						{
							whitePawn[whitePawnCounter] = new Pawn("pawn", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whitePawn[whitePawnCounter];
							whitePawnCounter++;
						}
						else
						{
							blackPawn[blackPawnCounter] = new Pawn("pawn", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackPawn[blackPawnCounter];
							blackPawnCounter++;
						}
					}
				}
			}
		}
	}
	
	public TestBoard(TestBoard theBoard)
	{
		int whiteRookCounter = 0;
		int whiteBishopCounter = 0;
		int whiteKnightCounter = 0;
		int whitePawnCounter = 0;
		int blackRookCounter = 0;
		int blackBishopCounter = 0;
		int blackKnightCounter = 0;
		int blackPawnCounter = 0;
		
		//Duplicate the board as testBoard
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = theBoard.getPiece(new Point(i, j));
				if (current != null)
				{
					if (current.getID().equals("king"))
					{
						if (current.getColor() == "white")
						{
							whiteKing = new King("king", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteKing;
						}
						else
						{
							blackKing = new King("king", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackKing;
						}
					}
					else if (current.getID().equals("queen"))
					{
						if (current.getColor() == "white")
						{
							whiteQueen = new Queen("queen", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteQueen;
						}
						else
						{
							blackQueen = new Queen("queen", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackQueen;
						}
					}
					else if (current.getID().equals("rook"))
					{
						if (current.getColor() == "white")
						{
							whiteRook[whiteRookCounter] = new Rook("rook", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteRook[whiteRookCounter];
							whiteRookCounter++;
						}
						else
						{
							blackRook[blackRookCounter] = new Rook("rook", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackRook[blackRookCounter];
							blackRookCounter++;
						}
					}
					else if (current.getID().equals("bishop"))
					{
						if (current.getColor() == "white")
						{
							whiteBishop[whiteBishopCounter] = new Bishop("bishop", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteBishop[whiteBishopCounter];
							whiteBishopCounter++;
						}
						else
						{
							blackBishop[blackBishopCounter] = new Bishop("bishop", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackBishop[blackBishopCounter];
							blackBishopCounter++;
						}
					}
					else if (current.getID().equals("knight"))
					{
						if (current.getColor() == "white")
						{
							whiteKnight[whiteKnightCounter] = new Knight("knight", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whiteKnight[whiteKnightCounter];
							whiteKnightCounter++;
						}
						else
						{
							blackKnight[blackKnightCounter] = new Knight("knight", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackKnight[blackKnightCounter];
							blackKnightCounter++;
						}
					}
					else if (current.getID().equals("pawn"))
					{
						if (current.getColor() == "white")
						{
							whitePawn[whitePawnCounter] = new Pawn("pawn", "white", current.getLocation(), current.hasItMoved());
							board[i][j] = whitePawn[whitePawnCounter];
							whitePawnCounter++;
						}
						else
						{
							blackPawn[blackPawnCounter] = new Pawn("pawn", "black", current.getLocation(), current.hasItMoved());
							board[i][j] = blackPawn[blackPawnCounter];
							blackPawnCounter++;
						}
					}
				}
			}
		}
	}
	
	public Piece[][] getBoard()
	{
		return board;
	}
	
	//Determines if a square contains a piece of the same color.
	public boolean squareOccupied(Point p, String color)
	{
		if (board[(int)p.getX()][(int)p.getY()] == null)
			return false;
		else
		{
			if (color == getPiece(p).getColor())
				return true;
			else
				return false;
		}
	}
	
	//Determines if a square contains a piece. Period.
	public boolean squareOccupiedPeriod(Point p)
	{
		if (board[(int)p.getX()][(int)p.getY()] == null)
			return false;
		else
			return true;
	}
	
	//Returns a piece from the board
	public Piece getPiece(Point p)
	{
		return board[(int)p.getX()][(int)p.getY()];
	}
	
	//Move a piece. Delete a piece if its taken
	public void move(Point a, Point b)
	{
		Piece current = getPiece(a);
		
		//Used for move history.
		Piece taken = null;
		
		//Create a list of valid moves for the selected piece.
		current.determineValidMoves(this);
		
		//Allow for future moves
		first = null;
		second = null;
		
		//If the move is valid, move the piece, update board.
		//Opponent's piece gets overwritten if its taken, thus removing it from the board.
		if (current.validMove(this, b))
		{
			//Update whose turn it is
			turn = -turn;
						
			//Move the piece within its class
			current.move(this, b);
			
			//If a piece is taken, save it.
			if (squareOccupiedPeriod(b))
			{
				taken = getPiece(b);
			}
			
			//Move the piece on the board.
			board[(int)a.getX()][(int)a.getY()] = null;
			board[(int)b.getX()][(int)b.getY()] = current;
		
			//Check if castling occurred.
			//White castle right
			if (a.equals(new Point(4, 0)) && b.equals(new Point(6, 0)))
			{
				board[5][0] = board[7][0];
				board[7][0] = null;
				board[5][0].setLocation(new Point(5, 0));
			}
			//White castle left
			else if (a.equals(new Point(4, 0)) && b.equals(new Point(2, 0)))
			{
				board[3][0] = board[0][0];
				board[0][0] = null;
				board[3][0].setLocation(new Point(3, 0));
			}
			//Black castle right
			else if (a.equals(new Point(4, 7)) && b.equals(new Point(6, 7)))
			{
				board[5][7] = board[7][7];
				board[7][7] = null;
				board[5][7].setLocation(new Point(5, 7));
			}
			//Black castle left
			else if (a.equals(new Point(4, 7)) && b.equals(new Point(2, 7)))
			{
				board[3][7] = board[0][7];
				board[0][7] = null;
				board[3][7].setLocation(new Point(3, 7));
			}
			
			//Pawn promotion check.
			if ((current.getID().equals("pawn")) && ((current.getColor().equals("white") && (int)b.getY() == 7) ||
													 (current.getColor().equals("black") && (int)b.getY() == 0)))
			{
				Point location = current.getLocation();
				
				current = new Queen("queen", current.getColor(), current.getLocation(), true);
				board[(int)location.getX()][(int)location.getY()] = current;
			}
			
			//Redraw and check for checkmate.
			checkmate();
		}
	}
			
	//Checks if it is checkmate for either side
	public void checkmate()
	{
		String message = "";
		if (turn == 1 && checkmateWhite())
		{
			message = "Checkmate! Black wins!.";
			checkmate = true;
		}
		
		if (turn == -1 && checkmateBlack())
		{
			message = "Checkmate! White wins!";
			checkmate = true;
		}
		
		if (!message.equals(""))
		{
			JOptionPane.showMessageDialog(null, message, "Checkmate", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	//Check if white is in checkmate
	private boolean checkmateWhite()
	{
		//See if white is in checkmate
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board[i][j];
				if (current != null && current.getColor().equals("white"))
				{
					LinkedList<Point> availableMoves = current.getValidMoves(this);
					if (availableMoves.size() > 0)
					{	
						for (int k = 0; k < availableMoves.size(); k++)
						{
							if (!Check.checkWhite(this, current.getLocation(), availableMoves.get(k)))
								return false;
						}
					}
				}
			}
		}
		
		return true;
	}
	
	//Check if black is in checkmate
	private boolean checkmateBlack()
	{
		//See if white is in checkmate
		for (int i = 0; i <= 7; i++)
		{
			for (int j = 0; j <= 7; j++)
			{
				Piece current = board[i][j];
				if (current != null && current.getColor().equals("black"))
				{
					LinkedList<Point> availableMoves = current.getValidMoves(this);
					if (availableMoves.size() > 0)
					{
						for (int k = 0; k < availableMoves.size(); k++)
						{
							if (!Check.checkBlack(this, current.getLocation(), availableMoves.get(k)))
								return false;
						}
					}
				}
			}
		}
		
		return true;

	}
}