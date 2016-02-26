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
	public static Move move(String color, int level, Board board)
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
		
		
		//Call appropriate AI method, send it a TestBoard to play with.
		if (level == 1)
			selectedMove = level1(availableMoves, new TestBoard(board), color);
		else if (level == 2)
			selectedMove = level2(availableMoves, new TestBoard(board), color);
		else if (level == 3)
			selectedMove = level3(availableMoves, new TestBoard(board), color);
		
		//return chosen move
		return selectedMove;
	}
	
	// NEW NOTES. LEVEL 1+2 FAIL TO DO ANYTHING MOST OF THE TIME WHEN ITS PUT IN CHECK. FIGURE IT OUT. 
	
	//Picks a random move from the list of available moves.
	public static Move level1(LinkedList<Move> availableMoves, TestBoard board, String color)
	{
		Random rn = new Random();
		int i = 0;
		boolean done = false;
		
		// WHY IS THIS LOOP NECESSARY? SHOULDN'T ALL MOVES IN "availableMoves" BE VALID MOVES ALREADY? CHECK IT OUT LATER
		//while (!done)
		//{
			i = rn.nextInt(availableMoves.size());
			Piece current = board.getPiece(availableMoves.get(i).from());
			if (current.validMove(board, availableMoves.get(i).to()))
				done = true;
		//}
		
		return availableMoves.get(i);
	}
	
	//Evaluate the resulting position of each move and pick the best. 
	public static Move level2(LinkedList<Move> availableMoves, TestBoard board, String color)
	{
		LinkedList<Integer> boardScores = new LinkedList<Integer>();
		
		//Iterate through all possible moves, create a testBoard for each, and evaluate the testBoard, save score in LinkedList
		for (int i = 0; i < availableMoves.size(); i++)
		{
			TestBoard testBoard = new TestBoard(board);									// clone the current board
			testBoard.move(availableMoves.get(i).from(), availableMoves.get(i).to());	// apply test move using original move method I made in Board (and now in TestBoard)
			boardScores.add(evaluate(testBoard, color));								// evaluate new board
		}
		
		boolean done = false;
		int value = 0;
		
		// LEVEL 2 WILL TAKE ANY PIECE IT CAN PRETTY MUCH, EVEN IF THE TRADE IS BAD FOR IT. Which is good, thats what it should be doing. 
		// ITS STILL SCREWED UP WHEN YOU PUT IT IN CHECK AND IT NEEDS TO MOVE OUT OF CHECK. 
		
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
		
		return availableMoves.get(value);
	}

	//Do what level2 does but look 3 moves in instead of 1.
	public static Move level3(LinkedList<Move> availableMoves, TestBoard board, String color)
	{
		int i = 0;
		return level2(availableMoves, board, color);
	}
		
	//Returns a numeric value representing the value of the board passed in
	public static int evaluate(TestBoard theBoard, String color)
	{
		Piece[][] board = theBoard.getBoard();
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