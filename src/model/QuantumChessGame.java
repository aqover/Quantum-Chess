package model;

import java.util.ArrayList;

import helper.Team;

public class QuantumChessGame implements ChessGameInfo {

	// player turn
	protected final Team firstTurn;
	
	// for all version
	private ArrayList<QuantumBoard> possibleBoards;
	public ArrayList<Character> deadPieces;
	
	private final NormalChessGame currentGame;
	private final ChessBoard displayBoard;
	
	// for undo
	private ArrayList<QuantumMove> moves;
	
	public QuantumChessGame(String fin) {
		
		possibleBoards = new ArrayList<>();
		currentGame = new NormalChessGame(new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE));
		displayBoard = new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE);
		
		resetGame();
		firstTurn = Team.PLAYER_WHITE;
	}
	
	public QuantumChessGame() {
		this("RNBQKBNR" + "PPPPPPPP" + "........" + "........" + "........" + "........" + "pppppppp" + "rnbqkbnr");
	}

	// return the probability of the move being successful
	public double successProb(ChessBoard.Move move) {
		double ans = 0.0;
		for (QuantumBoard qboard : possibleBoards) {
			if (NormalChessGame.isMoveValid(qboard.board, move)) {
				ans += qboard.prob;
			}
		}
		return ans;
	}
	
	// measure one of the cell
	public void measure(ArrayList<Integer> rows, ArrayList<Integer> cols) {
		
		try {
			ArrayList<QuantumBoard> newPossibleBoards = new ArrayList<>();
			
			double sumProb = 0.0;
			for (QuantumBoard qboard : possibleBoards) {

				boolean valid = true;
				for (int i = 0; i < rows.size(); ++i) {
					if (qboard.board.getAt(rows.get(i), cols.get(i)) !=
						currentGame.getBoard().getAt(rows.get(i), cols.get(i))) {
						valid = false;
						break;
					}
				}
				
				if (valid) {
					newPossibleBoards.add(qboard);
					sumProb += qboard.getProb();
				}
			}
			
			displayBoard.fill(Piece.EMPTY_SPACE);
			
			possibleBoards = newPossibleBoards;
			for (QuantumBoard qboard : possibleBoards) {
				
				qboard.setProb(qboard.getProb() / sumProb);
				
				ChessBoard board = qboard.board;
				for (int i = 0; i < board.getRows(); ++i) {
					for (int j = 0; j < board.getColumns(); ++j) {
						if (board.getAt(i, j) != Piece.EMPTY_SPACE) {
							displayBoard.setValue(i, j, board.getAt(i, j));
						}
					}
				}
			}
			
		} catch (Exception e) {
			return;
		}
	}
	
	// actual move
	public void move(QuantumMove qmove) {
		
		if (qmove.prob <= 0) return;
		
		char purge = Piece.EMPTY_SPACE;
		if (randomSuccess(qmove.prob)) {
			
			if (this.currentGame.isMoveValid(qmove.move)) {
				char cell = this.currentGame.getBoard().getAt(qmove.move.row2, qmove.move.row1);
				if (cell != Piece.EMPTY_SPACE) {
					this.deadPieces.add(cell);
					purge = cell;
				}
				
				this.currentGame.move(qmove.move);
			}
		}
		
		ArrayList<QuantumBoard> newPossibleBoards = new ArrayList<>();
		for (QuantumBoard qboard : possibleBoards) {
			if (NormalChessGame.isMoveValid(qboard.board, qmove.move)) {
				newPossibleBoards.add(
					new QuantumBoard(
						qboard.getProb() * qmove.prob, 
						qboard.board.moveDuplicate(qmove.move, Piece.EMPTY_SPACE)
					));
				qboard.setProb(qboard.getProb() * (1 - qmove.prob));
			}
		}
		
		possibleBoards.addAll(newPossibleBoards);
		moves.add(qmove);

		if (displayBoard.getAt(qmove.move.row2, qmove.move.col2) != Piece.EMPTY_SPACE) {
			measure(new ArrayList<>(qmove.move.row2), new ArrayList<>(qmove.move.col2));
		}
		
		// if there is a new dead
		if (purge != Piece.EMPTY_SPACE) {

			ArrayList<Integer> rows = new ArrayList<>();
			ArrayList<Integer> cols = new ArrayList<>();
			
			for (int i = 0; i < displayBoard.getRows(); ++i) {
				for (int j = 0; j < displayBoard.getColumns(); ++j) {
					if (displayBoard.getAt(i, j) == purge) {
						rows.add(i);
						cols.add(i);
					}
				}
			}
			
			measure(rows, cols);
		}
	}

	public boolean isDead(char piece) {
		return deadPieces.contains(piece);
	}
	
	public int getWinner() { 
		
		if (deadPieces.contains(Piece.BLACK_KING)) {
			return GAME_RESULT_WHITE_WINS;
		}
		
		if (deadPieces.contains(Piece.WHITE_KING)) {
			return GAME_RESULT_BLACK_WINS;
		}
		
		return GAME_RESULT_ONGOING;
	}
	
	public Team getTurn() {
		return this.moves.size() % 2 == 0 ? this.firstTurn : 
			(this.firstTurn == Team.PLAYER_WHITE ? Team.PLAYER_BLACK : Team.PLAYER_WHITE);
	}

	private void resetGame() {

		this.currentGame.setVersion(0);
		
		this.possibleBoards.clear();
		this.possibleBoards.add(new QuantumBoard(1.0, this.currentGame.getBoard()));
	}
	
	private boolean randomSuccess(double prob) {
		return Math.random() <= prob;
	}
	
	private class QuantumBoard {
		
		private double prob;
		public final ChessBoard board;
		
		public QuantumBoard(double prob, ChessBoard board) {
			this.prob = prob;
			this.board = board;
		}
		
		public double getProb() {
			return prob;
		}
		
		public void setProb(double prob) {
			this.prob = prob;
		}
	}
	
	public class QuantumMove {
		
		public final double prob;
		public final ChessBoard.Move move;
		
		public QuantumMove(double prob, ChessBoard.Move move) {
			this.prob = Math.max(0.0, Math.min(1.0, prob));
			this.move = move;			
		}
	}
}
