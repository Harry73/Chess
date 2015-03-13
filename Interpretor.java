/**
 * Class to take information about a move and store it, thus
 * creating a move history.
 * Mainly used for undoing a move, but the history is also written to 
 * the file "movelog.txt" if the user wishes to view it.
 * 
 * Author: Ian Patel
 */

import java.io.*;
import java.util.*;
import java.awt.Point;

public class Interpretor
{
	private PrintWriter out;
	private Scanner scan;
	private String moveList = "";
	private String takenInfo = "";
	
	//Basic constructor
	public Interpretor()
	{
		moveList = "";
		try
		{
			out = new PrintWriter(new File("moveLog.txt"));
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("The principal is death!");
		}
	}
	
	//Write information about the last move to a string and a file.
	public void addNextMove(Point from, Point to, Piece taken, String extraInfo)
	{
		String nextMove = "";
		
		nextMove += "" + (char)((int)(from.getX() + 65)) + ((int)from.getY() + 1);
		nextMove += " " + (char)((int)to.getX() + 65) + ((int)to.getY() + 1);
		
		nextMove += "\t";
		
		//If a piece was taken this move.
		if (taken != null)
		{
			nextMove += taken.getID() + " " + taken.getColor() + " ";
			nextMove += (char)((int)taken.getLocation().getX() + 65) + "" + (int)(taken.getLocation().getY() + 1) + " ";
			nextMove += String.valueOf(taken.hasItMoved());
		}
		
		nextMove += "\t";
		
		//Special moves.
		if (extraInfo == "promotion")
			nextMove += "promotion ";
		else if (extraInfo == "castle right white")
			nextMove += "0-0w ";
		else if (extraInfo == "castle left white")
			nextMove += "0-0-0w ";
		else if (extraInfo == "castle right black")
			nextMove += "0-0b ";
		else if (extraInfo == "castle left black")
			nextMove += "0-0-0b ";
		
		//Add next move to the front of the string.
		moveList = nextMove + "\n" + moveList;
		
		//Write move history to file for fun.
		try
		{
			out = new PrintWriter(new File("moveLog.txt"));
			out.print(moveList);
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("The principal is death!");
		}
	}
	
	//Checks if there is another move
	public boolean hasLastMove()
	{
		scan = new Scanner(moveList);
		return scan.hasNextLine();
	}
	
	//Returns the last move as an array of points.
	public Point[] getLastMove()
	{
		//0 for from square, 1 for to square, 2 for piece taken, 3 for castling, 4 for promotion
		Point[] returnInfo = new Point[5];
		
		//Get the most recent move
		scan = new Scanner(moveList);
		String lastMove = scan.nextLine();
		
		//Get data for returnInfo[0] and returnInfo[1]
		scan = new Scanner(lastMove);
		String fromSquare = scan.next();
		String toSquare = scan.next();
		returnInfo[0] = new Point((int)(fromSquare.charAt(0) - 65), Integer.parseInt(fromSquare.charAt(1) + "") - 1);
		returnInfo[1] = new Point((int)(toSquare.charAt(0) - 65), Integer.parseInt(toSquare.charAt(1) + "") - 1);		
		
		scan.useDelimiter("\t");
		String takenPart = scan.next();
		scan.useDelimiter(" ");
		String promotionCastlePart = scan.next();
				
		//Promotion or castling occurred
		if (promotionCastlePart.contains("promotion") || promotionCastlePart.contains("0-0"))
		{
			//Indicate promotion occurred
			if (promotionCastlePart.contains("promotion"))
				returnInfo[4] = new Point(4, 4);
			else if (promotionCastlePart.contains("0-0"))
				returnInfo[3] = new Point(3, 3);
		}
		
		//A piece was taken
		if (takenPart.contains("white") || takenPart.contains("black"))
		{
			//save info about taken piece
			takenInfo = takenPart;
			returnInfo[2] = new Point(2, 2);
		}
		
		//Remove first line from history
		scan = new Scanner(moveList);
		scan.nextLine();
		String temp = "";
		
		while (scan.hasNext())
		{
			String helper = scan.nextLine();
			temp += helper + "\n";
		}
		moveList = temp;
		
		//Write move history to file for fun.
		try
		{
			out = new PrintWriter(new File("moveLog.txt"));
			out.print(moveList);
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("The principal is death!");
		}
		
		return returnInfo;
	}
	
	//Reconstructs a taken piece from the info in takenInfo
	public Piece getTakenPiece()
	{
		scan = new Scanner(takenInfo);
		scan.useDelimiter(" ");
		String type = scan.next();
		String color = scan.next();
		String location = scan.next();
		int x = (int)(location.charAt(0) - 65);
		int y = Integer.parseInt(location.charAt(1) + "") - 1;
		boolean hasMoved = Boolean.parseBoolean(scan.next());
		
		Piece newPiece = null;
		if (type.contains("queen"))
			newPiece = new Queen("queen", color, new Point(x, y), hasMoved);
		else if (type.contains("rook"))
			newPiece = new Rook("rook", color, new Point(x, y), hasMoved);
		else if (type.contains("knight"))
			newPiece = new Knight("knight", color, new Point(x, y), hasMoved);
		else if (type.contains("bishop"))
			newPiece = new Bishop("bishop", color, new Point(x, y), hasMoved);
		else if (type.contains("pawn"))
			newPiece = new Pawn("pawn", color, new Point(x, y), hasMoved);
	
		return newPiece;
	}

	//If checkmate occurs, rewrite the move history to the correct order.
	public void checkmate(String message)
	{
		//Add checkmate to list.
		moveList = "Checkmate" + "\n" + moveList;
		
		String reversedMoveList = "";
		
		scan = new Scanner(moveList);
		while (scan.hasNext())
		{
			reversedMoveList = scan.nextLine() + "\n" + reversedMoveList;
		}		
		
		//Write move history to file for viewing.
		try
		{
			out = new PrintWriter(new File("moveLog.txt"));
			out.print(reversedMoveList);
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("The principal is death!");
		}
	}
}