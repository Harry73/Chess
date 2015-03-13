/**
 * Main processor class.
 * Handles all drawing and stores the state of the actual board .
 *
 * Author: Ian Patel
 */
 
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Board
{
	//Drawing components
	JFrame frame = new JFrame("Chess");
	JFrame promotion;		
	PromotionPanel promoPanel;
	BoardPanel boardPanel;
	JButton queenButton = new JButton("Queen");
	JButton rookButton = new JButton("Rook");
	JButton knightButton = new JButton("Knight");
	JButton bishopButton = new JButton("Bishop");
	JMenuBar menuBar;
	JMenu mainMenu, newGameMenu;
	JMenuItem menuItem;
	
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
	
	//Images for each piece
	Image chessBoard_pic;
	Image whiteKing_pic;
	Image blackKing_pic;
	Image whiteQueen_pic;
	Image blackQueen_pic;
	Image whiteBishop_pic;
	Image blackBishop_pic;
	Image whiteKnight_pic;
	Image blackKnight_pic;
	Image whiteRook_pic;
	Image blackRook_pic;
	Image whitePawn_pic;
	Image blackPawn_pic;
	
	//Stores the actual state of the board
	Piece[][] board = new Piece[8][8];

	//Interpretor records move history
	Interpretor history = new Interpretor();
	
	//Points for moving pieces and a little drawing.
	Point first;
	Point second;
	Point currentSquare;
	
	//For turns
	int turn = 1; //white's turn.
	String mode;
	boolean checkmate = false;
	
	//AI "intelligence" level
	int level = 1;
	
	//Detects mouse actions
	MouseAdapter mousey = new MouseAdapter()
	{
		//For selecting and moving a piece
		public void mousePressed(MouseEvent e)
		{
			if (!checkmate)
			{
				if (SwingUtilities.isLeftMouseButton(e))
				{
					int x = (int)Math.floor((e.getX()-8)/75);
					int y = (int)Math.floor((e.getY()-52)/75);
					y = 7 - y;
					if (first == null)
					{
						if (squareOccupiedPeriod(new Point(x, y)))
						{
							if ((turn == 1 && board[x][y].getColor().equals("white")) ||
								(turn == -1 && board[x][y].getColor().equals("black")))
							{
								first = new Point(x, y);
								repainting();
							}
							else
								first = null;
						}
						else
							first = null;
					}
					else
					{
						second = new Point(x, y);
						move(first, second);
					}		
				}
			}		
		}
		
		//For highlighting what square the mouse is in
		public void mouseMoved(MouseEvent e)
		{
			if (!checkmate)
			{
				int x = (int)Math.floor((e.getX()-8)/75);
				int y = (int)Math.floor((e.getY()-52)/75);
				y = 7 - y;
				
				currentSquare = new Point(x, y);
				repainting();
			}
		}
	};
	
	//Instantiates all the pieces.
	public Board(String mode)
	{
		this.mode = mode;
		
		boardPanel = new BoardPanel();
		
		//Set up menu bar
		menuBar = new JMenuBar();
		
		//Options menu
		mainMenu = new JMenu("Options");
		mainMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(mainMenu);
		
		//New Game menu
		newGameMenu = new JMenu("New Game");
		newGameMenu.setMnemonic(KeyEvent.VK_N);
		
		//New game items
		menuItem = new JMenuItem("As White");
		menuItem.addActionListener(boardPanel);
		newGameMenu.add(menuItem);
		menuItem = new JMenuItem("As Black");
		menuItem.addActionListener(boardPanel);
		newGameMenu.add(menuItem);
		menuItem = new JMenuItem("2-Player");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGameMenu.add(menuItem);
		mainMenu.add(newGameMenu);
		
		//Undo item
		menuItem = new JMenuItem("Undo");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);
		
		//Move History
		menuItem = new JMenuItem("Move History");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_H);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);
		
		//Quit item
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);
		
		//AI level menu
		mainMenu = new JMenu("AI Level");
		mainMenu.setMnemonic(KeyEvent.VK_L);
		menuBar.add(mainMenu);
		
		menuItem = new JMenuItem("Level 1");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);

		menuItem = new JMenuItem("Level 2");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);
		
		//Create main frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel);
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		frame.addMouseListener(mousey);
		frame.addMouseMotionListener(mousey);
		
		//Create the promotion frame
		promotion = new JFrame("Promote a Pawn");
		promotion.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		promoPanel = new PromotionPanel();

		//Add the buttons
		promoPanel.add(queenButton);
		promoPanel.add(rookButton);
		promoPanel.add(knightButton);
		promoPanel.add(bishopButton);
		
		//Button listeners
		queenButton.addActionListener(promoPanel);
		rookButton.addActionListener(promoPanel);
		knightButton.addActionListener(promoPanel);
		bishopButton.addActionListener(promoPanel);
		
		//Don't display this frame yet
		promotion.add(promoPanel);
		promotion.pack();
		promotion.setVisible(false);
		
		//Pawns!
		for (int i = 0; i <= 7; i++)
		{
			whitePawn[i] = new Pawn("pawn", "white", new Point(i, 1), false);
			board[i][1] = whitePawn[i];
			blackPawn[i] = new Pawn("pawn", "black", new Point(i, 6), false);
			board[i][6] = blackPawn[i];
		}
		
		//Rooks!
		whiteRook[0] = new Rook("rook", "white", new Point(0, 0), false);
		whiteRook[1] = new Rook("rook", "white", new Point(7, 0), false);
		blackRook[0] = new Rook("rook", "black", new Point(0, 7), false);
		blackRook[1] = new Rook("rook", "black", new Point(7, 7), false);
		board[0][0] = whiteRook[0];
		board[7][0] = whiteRook[1];
		board[0][7] = blackRook[0];
		board[7][7] = blackRook[1];
				
		//Knights!
		whiteKnight[0] = new Knight("knight", "white", new Point(1, 0), false);
		whiteKnight[1] = new Knight("knight", "white", new Point(6, 0), false);
		blackKnight[0] = new Knight("knight", "black", new Point(1, 7), false);
		blackKnight[1] = new Knight("knight", "black", new Point(6, 7), false);
		board[1][0] = whiteKnight[0];
		board[6][0] = whiteKnight[1];
		board[1][7] = blackKnight[0];
		board[6][7] = blackKnight[1];
		
		//Bishops!
		whiteBishop[0] = new Bishop("bishop", "white", new Point(2, 0), false);
		whiteBishop[1] = new Bishop("bishop", "white", new Point(5, 0), false);
		blackBishop[0] = new Bishop("bishop", "black", new Point(2, 7), false);
		blackBishop[1] = new Bishop("bishop", "black", new Point(5, 7), false);
		board[2][0] = whiteBishop[0];
		board[5][0] = whiteBishop[1];
		board[2][7] = blackBishop[0];
		board[5][7] = blackBishop[1];
		
		//Queens!
		whiteQueen = new Queen("queen", "white", new Point(3, 0), false);
		blackQueen = new Queen("queen", "black", new Point(3, 7), false);
		board[3][0] = whiteQueen;
		board[3][7] = blackQueen;
		
		//Kings!
		whiteKing = new King("king", "white", new Point(4, 0), false);
		blackKing = new King("king", "black", new Point(4, 7), false);
		board[4][0] = whiteKing;
		board[4][7] = blackKing;
		
		//Getting all the images.
		chessBoard_pic = Toolkit.getDefaultToolkit().getImage("Images/board.png");
		whiteKing_pic = Toolkit.getDefaultToolkit().getImage("Images/white_king.png");
		blackKing_pic = Toolkit.getDefaultToolkit().getImage("Images/black_king.png");
		whiteQueen_pic = Toolkit.getDefaultToolkit().getImage("Images/white_queen.png");
		blackQueen_pic = Toolkit.getDefaultToolkit().getImage("Images/black_queen.png");
		whiteRook_pic = Toolkit.getDefaultToolkit().getImage("Images/white_rook.png");
		blackRook_pic = Toolkit.getDefaultToolkit().getImage("Images/black_rook.png");
		whiteKnight_pic = Toolkit.getDefaultToolkit().getImage("Images/white_knight.png");
		blackKnight_pic = Toolkit.getDefaultToolkit().getImage("Images/black_knight.png");
		whiteBishop_pic = Toolkit.getDefaultToolkit().getImage("Images/white_bishop.png");
		blackBishop_pic = Toolkit.getDefaultToolkit().getImage("Images/black_bishop.png");
		whitePawn_pic = Toolkit.getDefaultToolkit().getImage("Images/white_pawn.png");
		blackPawn_pic = Toolkit.getDefaultToolkit().getImage("Images/black_pawn.png");
		
		if (mode.equals("white ai"))
			AI.move("white", level, this);
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
		String extraInfo = "";
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
			if (turn == 1)
				frame.setTitle("Chess - White's Turn");
			else
				frame.setTitle("Chess - Black's Turn");
			
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
				extraInfo = "castle right white";
			}
			//White castle left
			else if (a.equals(new Point(4, 0)) && b.equals(new Point(2, 0)))
			{
				board[3][0] = board[0][0];
				board[0][0] = null;
				board[3][0].setLocation(new Point(3, 0));
				extraInfo = "castle left white";
			}
			//Black castle right
			else if (a.equals(new Point(4, 7)) && b.equals(new Point(6, 7)))
			{
				board[5][7] = board[7][7];
				board[7][7] = null;
				board[5][7].setLocation(new Point(5, 7));
				extraInfo = "castle right black";
			}
			//Black castle left
			else if (a.equals(new Point(4, 7)) && b.equals(new Point(2, 7)))
			{
				board[3][7] = board[0][7];
				board[0][7] = null;
				board[3][7].setLocation(new Point(3, 7));
				extraInfo = "castle left black";
			}
			
			//Pawn promotion check.
			if ((current.getID().equals("pawn")) && ((current.getColor().equals("white") && (int)b.getY() == 7) ||
													 (current.getColor().equals("black") && (int)b.getY() == 0)))
			{
				promotion(current);
				extraInfo = "promotion";
			}
			
			//Record the last move.
			history.addNextMove(a, b, taken, extraInfo);
			
			//Redraw and check for checkmate.
			repainting();
			checkmate();
	
			//Run computer's turn
			if (!checkmate)
			{	
				if (mode.equals("white ai") && turn == 1)
				{
					AI.move("white", level, this);
				}
				else if (mode.equals("black ai") && turn == -1)
				{
					AI.move("black", level, this);
				}
			}
		}
		
		//Redraw frame.
		repainting();
	}
	
	//Small method to update the main frame
	public void repainting()
	{
		frame.repaint(1, -100, -100, 1000, 1000);
	}
	
	//Promote a pawn to another piece
	public void promotion(Piece current)
	{
		//Show the promotion panel
		promoPanel.setCurrent(current);
		promotion.setVisible(true);
	}
	
	//Creates a new game
	public void newGame(String type)
	{
		if (type == "As White")
		{
			frame.dispose();
			Board chess = new Board("black ai");
		}
		else if (type == "As Black")
		{
			frame.dispose();
			Board chess = new Board("white ai");
		}
		else
		{
			frame.dispose();
			Board chess = new Board("2-Player");
		}
	}
	
	//Undoes the last move
	public void undo()
	{
		if (history.hasLastMove() && !checkmate)
		{
			//Update whose turn it is
			turn = -turn;
			if (turn == 1)
				frame.setTitle("Chess - White's Turn");
			else
				frame.setTitle("Chess - Black's Turn");
			
			//Get last move
			Point[] lastMove = history.getLastMove();
			Point fromSquare = lastMove[0];
			Point toSquare = lastMove[1];		
			
			//Castling check
			if (lastMove[3] != null)
			{
				//Move the king back to where it belongs
				getPiece(toSquare).setLocation(fromSquare);
				getPiece(toSquare).setHasMoved(false);
				board[(int)fromSquare.getX()][(int)fromSquare.getY()] = board[(int)toSquare.getX()][(int)toSquare.getY()];
				board[(int)toSquare.getX()][(int)toSquare.getY()] = null;
				
				if (toSquare.equals(new Point(2, 0)))
				{
					//Move the rook back to where it belongs
					getPiece(new Point(3, 0)).setLocation(new Point(0,0));
					getPiece(new Point(3, 0)).setHasMoved(false);
					board[0][0] = board[3][0];
					board[3][0] = null;

				}
				else if (toSquare.equals(new Point(6, 0)))
				{
					//Move the rook back to where it belongs
					getPiece(new Point(5, 0)).setLocation(new Point(7,0));
					getPiece(new Point(5, 0)).setHasMoved(false);
					board[7][0] = board[5][0];
					board[5][0] = null;
				}
				else if (toSquare.equals(new Point(2, 7)))
				{
					//Move the rook back to where it belongs
					getPiece(new Point(3, 7)).setLocation(new Point(0,7));
					getPiece(new Point(3, 7)).setHasMoved(false);
					board[0][7] = board[3][7];
					board[3][7] = null;
				}
				else if (toSquare.equals(new Point(6, 7)))
				{
					//Move the rook back to where it belongs
					getPiece(new Point(5, 7)).setLocation(new Point(7,7));
					getPiece(new Point(5, 7)).setHasMoved(false);
					board[7][7] = board[5][7];
					board[5][7] = null;
				}
			}
			
			//Promotion and piece taken check
			else if (lastMove[4] != null && lastMove[2] != null)
			{
				Piece takenPiece = history.getTakenPiece();
				
				if (turn == 1)
					board[(int)fromSquare.getX()][(int)fromSquare.getY()] = new Pawn("pawn", "white", fromSquare, true);
				else
					board[(int)fromSquare.getX()][(int)fromSquare.getY()] = new Pawn("pawn", "black", fromSquare, true);
					
				board[(int)toSquare.getX()][(int)toSquare.getY()] = takenPiece;
			}
			
			//Promotion only
			else if (lastMove[4] != null)
			{
				//Move pawn back to its original place (on the board
				if (turn == 1)
					board[(int)fromSquare.getX()][(int)fromSquare.getY()] = new Pawn("pawn", "white", fromSquare, true);
				else
					board[(int)fromSquare.getX()][(int)fromSquare.getY()] = new Pawn("pawn", "black", fromSquare, true);
					
				board[(int)toSquare.getX()][(int)toSquare.getY()] = null;
			}
			
			//Piece taken only
			else if (lastMove[2] != null)
			{
				Piece takenPiece = history.getTakenPiece();
				
				//Move piece back to its original place (within its class)
				getPiece(toSquare).setLocation(fromSquare);
				
				//Check if a pawn is returning to starting point
				if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("white") && fromSquare.getY() == 1)
					getPiece(toSquare).setHasMoved(false);
				else if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("black") && fromSquare.getY() == 6)
					getPiece(toSquare).setHasMoved(false);				
				
				//Move pieces back to their original place (on the board)
				board[(int)fromSquare.getX()][(int)fromSquare.getY()] = board[(int)toSquare.getX()][(int)toSquare.getY()];
				board[(int)toSquare.getX()][(int)toSquare.getY()] = takenPiece;
			}
			
			//Regular move occurred
			else
			{
				//Move piece back to its original place (within its class)
				getPiece(toSquare).setLocation(fromSquare);
				
				//Check if a pawn is returning to starting point
				if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("white") && fromSquare.getY() == 1)
					getPiece(toSquare).setHasMoved(false);
				else if (getPiece(toSquare).getID().equals("pawn") && getPiece(toSquare).getColor().equals("black") && fromSquare.getY() == 6)
					getPiece(toSquare).setHasMoved(false);
				
				//Move piece back to its original place (on the board)
				board[(int)fromSquare.getX()][(int)fromSquare.getY()] = board[(int)toSquare.getX()][(int)toSquare.getY()];
				board[(int)toSquare.getX()][(int)toSquare.getY()] = null;
			}
			
			repainting();
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
			history.checkmate(message);
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
	
	//Closes the frame and ends the program
	public void quit()
	{
		frame.dispose();
		System.exit(0);
	}
	
	//Inner class for the Chess Board Frame
	class BoardPanel extends JPanel implements ActionListener
	{		
		public Dimension getPreferredSize() 
		{
			return new Dimension(600, 600);
		}

		public void actionPerformed(ActionEvent e)
		{
			JMenuItem source = (JMenuItem)(e.getSource());
			String temp = source.getText();
			if (temp == "As White" || temp == "As Black" || temp == "2-Player")
				newGame(temp);
			else if (temp == "Undo")
				undo();	
			else if (temp == "Move History")
			{
				try 
				{
					Desktop.getDesktop().open(new File("moveLog.txt"));
				}
				catch (IOException exception)
				{
					System.out.println("The limit does not exist!");
				}
			}
				
			else if (temp == "Quit")
				quit();
			else if (temp == "Level 1")
				level = 1;
			else if (temp == "Level 2")
				level = 2;
		}		
		
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);

			//Draw chessboard
			g.drawImage(chessBoard_pic, 0, 0, this);
			
			//Draw currently selected square
			if (first != null)
			{
				g.setColor(new Color(255, 217, 0, 100)); //translucent yellow
				g.fillRect((int)(first.getX() * 75), (7 - (int)first.getY()) * 75, 75, 75);
			}
			
			//Draw square mouse is hovering in
			if (currentSquare != null)
			{
				g.setColor(new Color(255, 0, 175, 100)); //pink
				g.fillRect((int)(currentSquare.getX() * 75), (7 - (int)currentSquare.getY()) * 75, 75, 75);
			}
			
			//Draw all the pieces, based on board
			for (int i = 0; i <= 7; i++)
			{
				for (int j = 0; j <= 7; j++)
				{
					if (board[i][j] != null)
					{
						if (board[i][j].getID().equals("king") && board[i][j].getColor().equals("white"))
							g.drawImage(whiteKing_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("queen") && board[i][j].getColor().equals("white"))
							g.drawImage(whiteQueen_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("rook") && board[i][j].getColor().equals("white"))
							g.drawImage(whiteRook_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("knight") && board[i][j].getColor().equals("white"))
							g.drawImage(whiteKnight_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("bishop") && board[i][j].getColor().equals("white"))
							g.drawImage(whiteBishop_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("pawn") && board[i][j].getColor().equals("white"))
							g.drawImage(whitePawn_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						
						if (board[i][j].getID().equals("king") && board[i][j].getColor().equals("black"))
							g.drawImage(blackKing_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("queen") && board[i][j].getColor().equals("black"))
							g.drawImage(blackQueen_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("rook") && board[i][j].getColor().equals("black"))
							g.drawImage(blackRook_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("knight") && board[i][j].getColor().equals("black"))
							g.drawImage(blackKnight_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("bishop") && board[i][j].getColor().equals("black"))
							g.drawImage(blackBishop_pic, board[i][j].drawX(), board[i][j].drawY(), this);
						if (board[i][j].getID().equals("pawn") && board[i][j].getColor().equals("black"))
							g.drawImage(blackPawn_pic, board[i][j].drawX(), board[i][j].drawY(), this);
					}
				}
			}
		}  
	}
	
	//Inner class for the selection a pawn promotion
	class PromotionPanel extends JPanel implements ActionListener
	{
		Piece current;
		
		//Save the piece being promoted
		public void setCurrent(Piece p)
		{
			current = p;
		}
		
		public Dimension getPreferredSize()
		{
			return new Dimension(300, 50);
		}
		
		//Button listener
		public void actionPerformed(ActionEvent e)
		{
			Point location = current.getLocation();
			String color = current.getColor();
			
			if (e.getSource() == queenButton)
			{
				current = new Queen("queen", color, location, true);
			}
			else if (e.getSource() == rookButton)
			{
				current = new Rook("rook", color, location, true);
			}
			else if (e.getSource() == knightButton)
			{
				current = new Knight("knight", color, location, true);
			}
			else if (e.getSource() == bishopButton)
			{
				current = new Bishop("bishop", color, location, true);
			}
			
			//Update board and hide promotion panel
			board[(int)location.getX()][(int)location.getY()] = current;
			promotion.setVisible(false);
			repainting();
		}  
	}
}

/**
 * Certain things with hasMoved boolean don't work correctly .
 * Namely if you move a rook and then undo, you can't castle now.
 * But I don't care to fix this, its minor.
 * 
 * There's also some kind of issues with undoing in general. Like pieces sometimes get lost somehow...
 * ^ this may have been fixed, I can't remember.
 */