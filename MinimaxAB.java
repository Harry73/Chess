/**
 * A computer Player that picks its move according the the minimax algorithm with alpha/beta pruning.
 * 
 * Author: Ian Patel
 */

import java.util.LinkedList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class MinimaxAB extends Player {

	// Simply use Player constructor for initialization
	public MinimaxAB(String color) {
		super(color);
		MAX_DEPTH = 0;
	}

	// Analyze the board and return the best move
	public Move move(Board board) {
		// Play randomly for depth 0
		if (MAX_DEPTH == 0) {
			LinkedList<Move> validMoves = board.getValidMoves(this.color);
			return validMoves.get(ThreadLocalRandom.current().nextInt(0, validMoves.size()));
		}
		
		// Otherwise use the min-max algorithm
		else {
			//System.out.println("Depth = " + MAX_DEPTH);
			Board nextBoard = new Board(board);
			Combo choice = max(nextBoard, 1, this.color, -10000, 10000);
			return choice.getMove();
		}
	}

	private Combo max(Board board, int depth, String color, double alpha, double beta) {
		LinkedList<Move> validMoves = board.getValidMoves(color);
		Collections.shuffle(validMoves);
		if (depth == MAX_DEPTH || validMoves.size() == 0) {
			return new Combo(board.evaluateBoard(color), null);
		}

		double maxValue = -10000;
		int bestIndex = 0;
		for (int i = 0; i < validMoves.size(); i++) {
			Board nextBoard = new Board(board);
			nextBoard.move(validMoves.get(i));
			Combo combo = min(nextBoard, depth+1, opposite(color), alpha, beta);
			if (combo.getValue() > maxValue) {
				maxValue = combo.getValue();
				bestIndex = i;
			}
			
			if (combo.getValue() >= beta)
				return combo;
			
			alpha = Math.max(alpha, combo.getValue());
		}
		
		return new Combo(maxValue, validMoves.get(bestIndex));
	}

	private Combo min(Board board, int depth, String color, double alpha, double beta) {
		LinkedList<Move> validMoves = board.getValidMoves(color);
		Collections.shuffle(validMoves);
		if (depth == MAX_DEPTH || validMoves.size() == 0)
			return new Combo(board.evaluateBoard(color), null);

		double minValue = 10000;
		int bestIndex = 0;
		for (int i = 0; i < validMoves.size(); i++) {
			Board nextBoard = new Board(board);
			nextBoard.move(validMoves.get(i));
			Combo combo = max(nextBoard, depth+1, opposite(color), alpha, beta);
			if (combo.getValue() < minValue) {
				minValue = combo.getValue();
				bestIndex = i;
			}
			
			if (combo.getValue() <= alpha)
				return combo;
			
			beta = Math.min(beta, combo.getValue());
		}

		return new Combo(minValue, validMoves.get(bestIndex));
	}

	private String opposite(String color) {
		if (color.compareTo("white") == 0)
			return "black";
		else if (color.compareTo("black") == 0)
			return "white";
		else
			return "";
	}
}