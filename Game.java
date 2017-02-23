/**
 * Main processor class.
 * Handles all drawing and stores the board.
 *
 * Author: Ian Patel
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class Game
{
	// Drawing components
	JFrame frame;
	BoardPanel boardPanel;
	JMenuBar menuBar;
	JMenu mainMenu, newGameMenu;
	JMenuItem menuItem;

	// Images for each piece
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

	Board board;
	Player whitePlayer;
	Player blackPlayer;

	// Points for moving pieces and a little drawing.
	Coord first;
	Coord second;
	Coord currentSquare;

	volatile boolean done = false;
	volatile boolean checkmate = false;
	volatile String mode;
	
	// Detects mouse actions
	MouseAdapter mousey = new MouseAdapter() {
		// For selecting and moving a piece
		public void mousePressed(MouseEvent e) {
			if (!checkmate) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					int x = (int)Math.floor((e.getX()-8)/75);
					int y = (int)Math.floor((e.getY()-52)/75);
					y = 7 - y;
					if (first == null) {
						if (board.squareOccupiedPeriod(new Coord(x, y))) {
							if ((whitePlayer.isMyTurn() && board.getPiece(x, y).getColor().equals("white")) ||
									(blackPlayer.isMyTurn() && board.getPiece(x, y).getColor().equals("black"))) {
								first = new Coord(x, y);
								repainting();
							}
							else
								first = null;
						}
						else
							first = null;
					}
					else {
						second = new Coord(x, y);

						// Validate move and perform if the selected move is valid
						if (whitePlayer.isMyTurn()) {
							LinkedList<Move> validMoves = board.getValidMoves("white");

							if (validMoves != null && validMoves.contains(new Move(first, second))) {
								board.move(first, second);
								blackPlayer.isMyTurn(true);
								whitePlayer.isMyTurn(false);
							}
						}
						else if (blackPlayer.isMyTurn()) {
							LinkedList<Move> validMoves = board.getValidMoves("black");

							if (validMoves != null && validMoves.contains(new Move(first, second))) {
								board.move(first, second);
								whitePlayer.isMyTurn(true);
								blackPlayer.isMyTurn(false);
							}
						}

						first = null;
						second = null;
						repainting();
					}
				}
			}
		}

		// For highlighting what square the mouse is in
		public void mouseMoved(MouseEvent e) {
			if (!checkmate) {
				int x = (int)Math.floor((e.getX()-8)/75);
				int y = (int)Math.floor((e.getY()-52)/75);
				y = 7 - y;

				currentSquare = new Coord(x, y);
				repainting();
			}
		}
	};

	// Instantiates the game
	public Game() {
		board = new Board();
		whitePlayer = new Player("white");
		blackPlayer = new Player("black");

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

		boardPanel = new BoardPanel();

		// Set up menu bar
		menuBar = new JMenuBar();

		// Options menu
		mainMenu = new JMenu("Options");
		mainMenu.setMnemonic(KeyEvent.VK_O);
		menuBar.add(mainMenu);

		// New Game menu
		newGameMenu = new JMenu("New Game");
		newGameMenu.setMnemonic(KeyEvent.VK_N);

		// New game items
		menuItem = new JMenuItem("As White");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		newGameMenu.add(menuItem);
		menuItem = new JMenuItem("As Black");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
		newGameMenu.add(menuItem);
		menuItem = new JMenuItem("2-Player");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newGameMenu.add(menuItem);
		mainMenu.add(newGameMenu);

		// Undo item
		menuItem = new JMenuItem("Undo");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_U);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);

		// Move History
		menuItem = new JMenuItem("Move History");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_M);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);

		// Quit item
		menuItem = new JMenuItem("Quit");
		menuItem.addActionListener(boardPanel);
		menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);

		// AI level menu
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

		menuItem = new JMenuItem("Level 3");
		menuItem.addActionListener(boardPanel);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		mainMenu.add(menuItem);

		// Create main frame
		frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(boardPanel);
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
		frame.addMouseListener(mousey);
		frame.addMouseMotionListener(mousey);
	}

	// Small method to update the main frame
	public void repainting() {
		frame.repaint(1, -100, -100, 1000, 1000);
	}

	// Creates a new game
	// Feels clumsy but works well enough
	public void newGame() {		
		if (mode.equals("As White")) {
			whitePlayer = new Player("white");
			blackPlayer = new MinimaxAB("black");
		}
		else if (mode.equals("As Black")) {
			whitePlayer = new MinimaxAB("white");
			blackPlayer = new Player("black");
		}
		else if (mode.equals("2-Player")) {
			whitePlayer = new Player("white");
			blackPlayer = new Player("black");
		}

		board = new Board();
		repainting();
		checkmate = false;
		run();
	}

	// Run the game
	public void run() {
		Move move;
		
		while (true) {
			// Wait for white to move
			move = whitePlayer.move(board);
			if (move != null) {
				board.move(move);
				blackPlayer.isMyTurn(true);
				whitePlayer.isMyTurn(false);
			}
			repainting();

			// Check if game ended
			if (board.getValidMoves("black").size() == 0) {
				String message = "Checkmate! White wins!";
				checkmate = true;
				JOptionPane.showMessageDialog(null, message, "Checkmate", JOptionPane.INFORMATION_MESSAGE);
				board.reportCheckmate(message);
				break;
			}
			if (checkmate)
				break;

			// Wait for black to move
			move = blackPlayer.move(board);
			if (move != null) {
				board.move(move);
				whitePlayer.isMyTurn(true);
				blackPlayer.isMyTurn(false);
			}
			repainting();

			// Check if the game ended
			if (board.getValidMoves("white").size() == 0) {
				String message = "Checkmate! Black wins!";
				checkmate = true;
				JOptionPane.showMessageDialog(null, message, "Checkmate", JOptionPane.INFORMATION_MESSAGE);
				board.reportCheckmate(message);
				break;
			}
			if (checkmate)
				break;
		}
		
		newGame();
	}

	// Closes the frame and ends the program
	public void quit() {
		frame.dispose();
		System.exit(0);
	}

	// Undo the last 2 moves. A tad screwy...
	public void undo() {
		if (whitePlayer.isMyTurn()) {
			board.undo("white");
			board.undo("black");
		}
		else if (blackPlayer.isMyTurn()) {
			board.undo("black");
			board.undo("white");
		}
		
		repainting();
	}

	// Inner class for the Chess Board Frame
	class BoardPanel extends JPanel implements ActionListener {
		public Dimension getPreferredSize() {
			return new Dimension(600, 600);
		}

		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem)(e.getSource());
			String temp = source.getText();
			if (temp == "As White" || temp == "As Black" || temp == "2-Player") {
				mode = temp;
				checkmate = true;
				whitePlayer.isMyTurn(false);
				blackPlayer.isMyTurn(false);
			}
			else if (temp == "Undo")
				undo();
			else if (temp == "Move History") {
				try {
					Desktop.getDesktop().open(new File("moveLog.txt"));
				}
				catch (IOException exception) {
					System.out.println("The limit does not exist!");
				}
			}

			else if (temp == "Quit")
				quit();
			else if (temp == "Level 1") {
				whitePlayer.setMaxDepth(3);
				blackPlayer.setMaxDepth(3);
			}
			else if (temp == "Level 2") {
				whitePlayer.setMaxDepth(5);
				blackPlayer.setMaxDepth(5);
			}
			else if (temp == "Level 3") {
				whitePlayer.setMaxDepth(7);
				blackPlayer.setMaxDepth(7);
			}
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Draw chessboard
			g.drawImage(chessBoard_pic, 0, 0, this);

			// Draw currently selected square
			if (first != null) {
				g.setColor(new Color(255, 217, 0, 100)); //translucent yellow
				g.fillRect((int)(first.X() * 75), (7 - (int)first.Y()) * 75, 75, 75);
			}

			// Draw square mouse is hovering in
			if (currentSquare != null) {
				g.setColor(new Color(255, 0, 175, 100)); //pink
				g.fillRect((int)(currentSquare.X() * 75), (7 - (int)currentSquare.Y()) * 75, 75, 75);
			}

			// Draw all the pieces, based on board
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					Piece piece = board.getPiece(i, j);
					if (piece != null) {
						if (piece.getID().equals("king") && piece.getColor().equals("white"))
							g.drawImage(whiteKing_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("queen") && piece.getColor().equals("white"))
							g.drawImage(whiteQueen_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("rook") && piece.getColor().equals("white"))
							g.drawImage(whiteRook_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("knight") && piece.getColor().equals("white"))
							g.drawImage(whiteKnight_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("bishop") && piece.getColor().equals("white"))
							g.drawImage(whiteBishop_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("pawn") && piece.getColor().equals("white"))
							g.drawImage(whitePawn_pic, piece.drawX(), piece.drawY(), this);

						if (piece.getID().equals("king") && piece.getColor().equals("black"))
							g.drawImage(blackKing_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("queen") && piece.getColor().equals("black"))
							g.drawImage(blackQueen_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("rook") && piece.getColor().equals("black"))
							g.drawImage(blackRook_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("knight") && piece.getColor().equals("black"))
							g.drawImage(blackKnight_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("bishop") && piece.getColor().equals("black"))
							g.drawImage(blackBishop_pic, piece.drawX(), piece.drawY(), this);
						if (piece.getID().equals("pawn") && piece.getColor().equals("black"))
							g.drawImage(blackPawn_pic, piece.drawX(), piece.drawY(), this);
					}
				}
			}
		}
	}
}