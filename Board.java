/**
 * Main processor class.
 * Handles all drawing and stores the state of the actual board .
 *
 * Author: Ian Patel
 */

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

public class Board
{
	// Pieces in the board
	Piece[] whitePawn = new Piece[8];
	Piece[] blackPawn = new Piece[8];
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

	// Stores the actual state of the board
	Piece[][] board;

	// Interpretor records move history
	Interpretor history = new Interpretor();

	boolean checkmate = false;

	// Instantiates all the pieces.
	public Board() {
		board = new Piece[8][8];

		// Pawns!
		for (int i = 0; i <= 7; i++) {
			whitePawn[i] = new Pawn("pawn", "white", new Coord(i, 1), false);
			board[i][1] = whitePawn[i];
			blackPawn[i] = new Pawn("pawn", "black", new Coord(i, 6), false);
			board[i][6] = blackPawn[i];
		}

		// Rooks!
		whiteRook[0] = new Rook("rook", "white", new Coord(0, 0), false);
		whiteRook[1] = new Rook("rook", "white", new Coord(7, 0), false);
		blackRook[0] = new Rook("rook", "black", new Coord(0, 7), false);
		blackRook[1] = new Rook("rook", "black", new Coord(7, 7), false);
		board[0][0] = whiteRook[0];
		board[7][0] = whiteRook[1];
		board[0][7] = blackRook[0];
		board[7][7] = blackRook[1];

		// Knights!
		whiteKnight[0] = new Knight("knight", "white", new Coord(1, 0), false);
		whiteKnight[1] = new Knight("knight", "white", new Coord(6, 0), false);
		blackKnight[0] = new Knight("knight", "black", new Coord(1, 7), false);
		blackKnight[1] = new Knight("knight", "black", new Coord(6, 7), false);
		board[1][0] = whiteKnight[0];
		board[6][0] = whiteKnight[1];
		board[1][7] = blackKnight[0];
		board[6][7] = blackKnight[1];

		// Bishops!
		whiteBishop[0] = new Bishop("bishop", "white", new Coord(2, 0), false);
		whiteBishop[1] = new Bishop("bishop", "white", new Coord(5, 0), false);
		blackBishop[0] = new Bishop("bishop", "black", new Coord(2, 7), false);
		blackBishop[1] = new Bishop("bishop", "black", new Coord(5, 7), false);
		board[2][0] = whiteBishop[0];
		board[5][0] = whiteBishop[1];
		board[2][7] = blackBishop[0];
		board[5][7] = blackBishop[1];

		// Queens!
		whiteQueen = new Queen("queen", "white", new Coord(3, 0), false);
		blackQueen = new Queen("queen", "black", new Coord(3, 7), false);
		board[3][0] = whiteQueen;
		board[3][7] = blackQueen;

		// Kings!
		whiteKing = new King("king", "white", new Coord(4, 0), false);
		blackKing = new King("king", "black", new Coord(4, 7), false);
		board[4][0] = whiteKing;
		board[4][7] = blackKing;
	}

	// Clone a board
	public Board(Board oldBoard) {
		board = new Piece[8][8];

		int whitePawnCount = 0;
		int blackPawnCount = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Piece current = oldBoard.getPiece(i, j);
				if (current != null) {
					if (current.getColor().equals("white")) {
						if (current.getID().equals("pawn")) {
							board[i][j] = new Pawn("pawn", "white", new Coord(i, j), current.hasItMoved());
							whitePawn[whitePawnCount] = (Pawn)board[i][j];
							whitePawnCount += 1;
						}
						else if (current.getID().equals("rook")) {
							board[i][j] = new Rook("rook", "white", new Coord(i, j), current.hasItMoved());
							if (whiteRook[0] == null)
								whiteRook[0] = (Rook)board[i][j];
							else if (whiteRook[1] == null)
								whiteRook[1] = (Rook)board[i][j];
							else {
								whitePawn[whitePawnCount] = (Rook)board[i][j];
								whitePawnCount += 1;
							}
						}
						else if (current.getID().equals("knight")) {
							board[i][j] = new Knight("knight", "white", new Coord(i, j), current.hasItMoved());
							if (whiteKnight[0] == null)
								whiteKnight[0] = (Knight)board[i][j];
							else if (whiteKnight[1] == null)
								whiteKnight[1] = (Knight)board[i][j];
							else {
								whitePawn[whitePawnCount] = (Knight)board[i][j];
								whitePawnCount += 1;
							}
						}
						else if (current.getID().equals("bishop")) {
							board[i][j] = new Bishop("bishop", "white", new Coord(i, j), current.hasItMoved());
							if (whiteBishop[0] == null)
								whiteBishop[0] = (Bishop)board[i][j];
							else if (whiteBishop[1] == null)
								whiteBishop[1] = (Bishop)board[i][j];
							else {
								whitePawn[whitePawnCount] = (Bishop)board[i][j];
								whitePawnCount += 1;
							}
						}
						else if (current.getID().equals("queen")) {
							board[i][j] = new Queen("queen", "white", new Coord(i, j), current.hasItMoved());
							if (whiteQueen == null)
								whiteQueen = (Queen)board[i][j];
							else {
								whitePawn[whitePawnCount] = (Queen)board[i][j];
								whitePawnCount += 1;
							}
						}
						else if (current.getID().equals("king")) {
							board[i][j] = new King("king", "white", new Coord(i, j), current.hasItMoved());
							whiteKing = (King)board[i][j];
						}
					}
					else if (current.getColor().equals("black")) {
						if (current.getID().equals("pawn")) {
							board[i][j] = new Pawn("pawn", "black", new Coord(i, j), current.hasItMoved());
							blackPawn[blackPawnCount] = (Pawn)board[i][j];
							blackPawnCount += 1;
						}
						else if (current.getID().equals("rook")) {
							board[i][j] = new Rook("rook", "black", new Coord(i, j), current.hasItMoved());
							if (blackRook[0] == null)
								blackRook[0] = (Rook)board[i][j];
							else if (blackRook[1] == null)
								blackRook[1] = (Rook)board[i][j];
							else {
								blackPawn[blackPawnCount] = (Rook)board[i][j];
								blackPawnCount += 1;
							}
						}
						else if (current.getID().equals("knight")) {
							board[i][j] = new Knight("knight", "black", new Coord(i, j), current.hasItMoved());
							if (blackKnight[0] == null)
								blackKnight[0] = (Knight)board[i][j];
							else if (blackKnight[1] == null)
								blackKnight[1] = (Knight)board[i][j];
							else {
								blackPawn[blackPawnCount] = (Knight)board[i][j];
								blackPawnCount += 1;
							}
						}
						else if (current.getID().equals("bishop")) {
							board[i][j] = new Bishop("bishop", "black", new Coord(i, j), current.hasItMoved());
							if (blackBishop[0] == null)
								blackBishop[0] = (Bishop)board[i][j];
							else if (blackBishop[1] == null)
								blackBishop[1] = (Bishop)board[i][j];
							else {
								blackPawn[blackPawnCount] = (Bishop)board[i][j];
								blackPawnCount += 1;
							}
						}
						else if (current.getID().equals("queen")) {
							board[i][j] = new Queen("queen", "black", new Coord(i, j), current.hasItMoved());
							if (blackQueen == null)
								blackQueen = (Queen)board[i][j];
							else {
								blackPawn[blackPawnCount] = (Queen)board[i][j];
								blackPawnCount += 1;
							}
						}
						else if (current.getID().equals("king")) {
							board[i][j] = new King("king", "black", new Coord(i, j), current.hasItMoved());
							blackKing = (King)board[i][j];
						}
					}
				}
			}
		}
	}

	public String colorAtSquare(Coord p) {
		return board[p.X()][p.Y()].getColor();
	}

	// Determines if a square contains a piece of the same color.
	public boolean squareOccupied(Coord p, String color) {
		if (board[p.X()][p.Y()] == null)
			return false;
		else {
			if (color == getPiece(p).getColor())
				return true;
			else
				return false;
		}
	}

	// Determines if a square contains a piece. Period.
	public boolean squareOccupiedPeriod(Coord p) {
		if (board[p.X()][p.Y()] == null)
			return false;
		else
			return true;
	}

	// Returns a piece from the board
	public Piece getPiece(Coord p) {
		return board[p.X()][p.Y()];
	}

	public Piece getPiece(int x, int y) {
		return board[x][y];
	}

	// Assume move is valid. Move a piece. Delete a piece if its taken
	public void move(Coord a, Coord b, boolean promotion, String promoteTo) {
		Piece current = getPiece(a);

		// Used for move history.
		String extraInfo = "";
		Piece taken = null;

		// Move the piece within its class
		current.move(this, b);

		// If a piece is taken, save it.
		if (squareOccupiedPeriod(b))
			taken = getPiece(b);

		// Move the piece on the board.
		board[a.X()][a.Y()] = null;
		board[b.X()][b.Y()] = current;

		// Check if castling occurred.
		// White castle right
		if (a.equals(new Coord(4, 0)) && b.equals(new Coord(6, 0))) {
			board[5][0] = board[7][0];
			board[7][0] = null;
			board[5][0].setLocation(new Coord(5, 0));
			extraInfo = "castle right white";
		}
		// White castle left
		else if (a.equals(new Coord(4, 0)) && b.equals(new Coord(2, 0))) {
			board[3][0] = board[0][0];
			board[0][0] = null;
			board[3][0].setLocation(new Coord(3, 0));
			extraInfo = "castle left white";
		}
		// Black castle right
		else if (a.equals(new Coord(4, 7)) && b.equals(new Coord(6, 7))) {
			board[5][7] = board[7][7];
			board[7][7] = null;
			board[5][7].setLocation(new Coord(5, 7));
			extraInfo = "castle right black";
		}
		// Black castle left
		else if (a.equals(new Coord(4, 7)) && b.equals(new Coord(2, 7))) {
			board[3][7] = board[0][7];
			board[0][7] = null;
			board[3][7].setLocation(new Coord(3, 7));
			extraInfo = "castle left black";
		}

		// Pawn promotion check.
		if (promotion) {
			extraInfo = "promotion";
			String newPieceType;

			if (promoteTo.equals("")) {
				// Get user to select piece to promote to
				String[] types = {"Queen", "Rook", "Knight", "Bishop"};

				JFrame pieceSelectFrame = new JFrame();
				pieceSelectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				int pieceSelect = JOptionPane.showOptionDialog(
					pieceSelectFrame,
					"Select a game board to play",
					"Board Selection",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					types,
					types[0]
				);
				pieceSelectFrame.dispose();

				newPieceType = types[pieceSelect];
			}
			else {
				newPieceType = promoteTo;
			}

			Coord location = current.getLocation();
			String color = current.getColor();

			// Create new piece
			if (newPieceType.equals("Queen")) {
				current = new Queen("queen", color, location, true);
			}
			else if (newPieceType.equals("Rook")) {
				current = new Rook("rook", color, location, true);
			}
			else if (newPieceType.equals("Knight")) {
				current = new Knight("knight", color, location, true);
			}
			else if (newPieceType.equals("Bishop")) {
				current = new Bishop("bishop", color, location, true);
			}

			// Update board and hide promotion panel
			board[location.X()][location.Y()] = current;
		}

		// Record the last move.
		history.addNextMove(a, b, taken, extraInfo);
	}

	public void move(Move move) {
		move(move.from(), move.to(), move.promotion(), move.promoteTo());
	}

	public void move(Coord a, Coord b) {
		move(a, b, false, "");
	}

	// Generate list of all valid moves for a player
	public LinkedList<Move> getValidMoves(String color) {
		LinkedList<Move> validMoves = new LinkedList<Move>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null && board[i][j].getColor().equals(color)) {
					LinkedList<Move> pieceMoves = board[i][j].getValidMoves(this);
					for (int k = 0; k < pieceMoves.size(); k++) {
						// If king is in check after move, it is not a valid move
						if (!futureCheck(pieceMoves.get(k), color))
							validMoves.add(pieceMoves.get(k));
					}
				}
			}
		}

		return validMoves;
	}

	// Copy the board, apply a move, and see if the king of "color" is in check
	public boolean futureCheck (Move testMove, String color) {
		Board testBoard = new Board(this);
		testBoard.move(testMove);
		return testBoard.isChecked(color);
	}

	// Reports whether or not the "color" king is in check
	public boolean isChecked(String color) {
		// Get list of square opponent can attack
		LinkedList<Coord> attacks = attackSquares(opposite(color));
		if (color.equals("white")) {
			if (attacks.contains(whiteKing.getLocation()))
				return true;
			else
				return false;
		}
		else if (color.equals("black")) {
			if (attacks.contains(blackKing.getLocation()))
				return true;
			else
				return false;
		}
		else {
			System.out.println("something's wrong");
			return false;
		}
	}

	// Gives list of squares a side is attacking
	public LinkedList<Coord> attackSquares(String color) {
		LinkedList<Coord> attacks = new LinkedList<Coord>();

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null && board[i][j].getColor().equals(color)) {
					attacks.addAll(board[i][j].attackSquares(this));
				}
			}
		}

		return attacks;
	}

	public String opposite(String color) {
		if (color.equals("white"))
			return "black";
		else if (color.equals("black"))
			return "white";
		else
			return null;
	}

	public void reportCheckmate(String message) {
		history.checkmate(message);
	}

	public double evaluateBoard(String color) {
		int score = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] != null) {
					if (board[i][j].getColor().equals(color)) {
						if (board[i][j].getID() == "queen")
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
					else {
						if (board[i][j].getID() == "queen")
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

		// Make checkmate most desirable position
		if (getValidMoves(opposite(color)).size() == 0) {
			score += 500;
		}

		return score;
	}

	// Undoes the last move
	public void undo(String color) {
		if (history.hasLastMove()) {
			// Get last move
			Coord[] lastMove = history.getLastMove();
			Coord fromSquare = lastMove[0];
			Coord toSquare = lastMove[1];

			// Castling check
			if (lastMove[3] != null) {
				// Move the king back to where it belongs
				getPiece(toSquare).setLocation(fromSquare);
				getPiece(toSquare).setHasMoved(false);
				board[(int)fromSquare.X()][(int)fromSquare.Y()] = board[(int)toSquare.X()][(int)toSquare.Y()];
				board[(int)toSquare.X()][(int)toSquare.Y()] = null;

				if (toSquare.equals(new Coord(2, 0))) {
					// Move the rook back to where it belongs
					getPiece(new Coord(3, 0)).setLocation(new Coord(0,0));
					getPiece(new Coord(3, 0)).setHasMoved(false);
					board[0][0] = board[3][0];
					board[3][0] = null;
				}
				else if (toSquare.equals(new Coord(6, 0))) {
					// Move the rook back to where it belongs
					getPiece(new Coord(5, 0)).setLocation(new Coord(7,0));
					getPiece(new Coord(5, 0)).setHasMoved(false);
					board[7][0] = board[5][0];
					board[5][0] = null;
				}
				else if (toSquare.equals(new Coord(2, 7))) {
					// Move the rook back to where it belongs
					getPiece(new Coord(3, 7)).setLocation(new Coord(0,7));
					getPiece(new Coord(3, 7)).setHasMoved(false);
					board[0][7] = board[3][7];
					board[3][7] = null;
				}
				else if (toSquare.equals(new Coord(6, 7))) {
					// Move the rook back to where it belongs
					getPiece(new Coord(5, 7)).setLocation(new Coord(7,7));
					getPiece(new Coord(5, 7)).setHasMoved(false);
					board[7][7] = board[5][7];
					board[5][7] = null;
				}
			}

			// Promotion and piece taken check
			else if (lastMove[4] != null && lastMove[2] != null) {
				Piece takenPiece = history.getTakenPiece();

				if (color.equals("white"))
					board[fromSquare.X()][fromSquare.Y()] = new Pawn("pawn", "white", fromSquare, true);
				else
					board[fromSquare.X()][fromSquare.Y()] = new Pawn("pawn", "black", fromSquare, true);

				board[toSquare.X()][toSquare.Y()] = takenPiece;
			}

			// Promotion only
			else if (lastMove[4] != null) {
				// Move pawn back to its original place (on the board
				if (color.equals("white"))
					board[(int)fromSquare.X()][(int)fromSquare.Y()] = new Pawn("pawn", "white", fromSquare, true);
				else
					board[(int)fromSquare.X()][(int)fromSquare.Y()] = new Pawn("pawn", "black", fromSquare, true);

				board[(int)toSquare.X()][(int)toSquare.Y()] = null;
			}

			// Piece taken only
			else if (lastMove[2] != null) {
				Piece takenPiece = history.getTakenPiece();

				// Move piece back to its original place (within its class)
				getPiece(toSquare).setLocation(fromSquare);

				// Check if a pawn is returning to starting point
				if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("white") && fromSquare.Y() == 1)
					getPiece(toSquare).setHasMoved(false);
				else if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("black") && fromSquare.Y() == 6)
					getPiece(toSquare).setHasMoved(false);

				// Move pieces back to their original place (on the board)
				board[fromSquare.X()][fromSquare.Y()] = board[toSquare.X()][toSquare.Y()];
				board[toSquare.X()][toSquare.Y()] = takenPiece;
			}

			// Regular move occurred
			else {
				// Move piece back to its original place (within its class)
				getPiece(toSquare).setLocation(fromSquare);

				// Check if a pawn is returning to starting point
				if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("white") && fromSquare.Y() == 1)
					getPiece(toSquare).setHasMoved(false);
				else if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("black") && fromSquare.Y() == 6)
					getPiece(toSquare).setHasMoved(false);

				// Move piece back to its original place (on the board)
				board[fromSquare.X()][fromSquare.Y()] = board[toSquare.X()][toSquare.Y()];
				board[toSquare.X()][toSquare.Y()] = null;
			}
		}
	}
}