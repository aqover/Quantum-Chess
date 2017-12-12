package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import helper.Team;

public class QuantumChessGame implements ChessGameInfo {

	// player turn
	public final Team firstTurn;
	public final String fin;
	
	// for all version
	private List<QuantumBoard> possibleBoards;
	public List<Character> deadPieces;
	
	protected ChessBoard currentGame;
	protected ChessBoard displayBoard;
	protected double[][] piecePossibility = null;
	
	protected boolean lastMoveHiddenStatus;
	
	// for undo
	private List<QuantumMove> moves;
	
	public QuantumChessGame(String fin) {
		
		this.fin = fin;
		
		currentGame = new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE);
		displayBoard = new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE);
		piecePossibility = new double[BOARD_SIZE][BOARD_SIZE];
		possibleBoards = new ArrayList<>();
		resetGame();
		
		moves = new ArrayList<>();
		deadPieces = new ArrayList<>();
		
		firstTurn = Team.PLAYER_WHITE;
		lastMoveHiddenStatus = false;
	}
	
	private void resetGame() {
		currentGame = new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE);
		displayBoard = new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE);
		possibleBoards.clear();
		possibleBoards.add(new QuantumBoard(1.0, new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE)));
		
		setDisplayBoard();
	}
	
	public QuantumChessGame() {
		this("RNBQKBNR" + "!@#$%^&*" + "........" + "........" + "........" + "........" + "12345678" + "rnbqkbnr");
	}

	// return the probability of the move being successful
	public double successProb(ChessBoard.Move move) {
		double ans = 0.0;
		for (QuantumBoard qboard : possibleBoards) {
			if (NormalChessGame.isMoveValidNoKing(qboard.board, move)) {
				ans += qboard.prob;
			}
		}
		return ans;
	}
	
	public double[][] getPossibilityMoves(int i, int j) {
		
		double[][] result = new double[BOARD_SIZE][BOARD_SIZE];
		for (int ii = 0; ii < BOARD_SIZE; ++ii) {
			for (int jj = 0; jj < BOARD_SIZE; ++jj) {
				result[ii][jj] = successProb(new ChessBoard.Move(i, j, ii, jj));
			}
		}
		return result;
	}
	
	protected void setDisplayBoard() {
		
		displayBoard.fill(Piece.EMPTY_SPACE);
		
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				piecePossibility[i][j] = 0.0;
			}
		}
		
		for (QuantumBoard qboard : possibleBoards) {
			ChessBoard board = qboard.board;
			for (int i = 0; i < board.getRows(); ++i) {
				for (int j = 0; j < board.getColumns(); ++j) {
					if (board.getAt(i, j) != Piece.EMPTY_SPACE) {
						piecePossibility[i][j] += qboard.getProb();
						displayBoard.setValue(i, j, board.getAt(i, j));
					}
				}
			}
		}
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
						currentGame.getAt(rows.get(i), cols.get(i))) {
						valid = false;
						break;
					}
				}
				
				if (valid) {
					newPossibleBoards.add(qboard);
					sumProb += qboard.getProb();
				}
			}

			possibleBoards.clear();
			possibleBoards = newPossibleBoards;
			for (QuantumBoard qboard : possibleBoards) {	
				qboard.setProb(qboard.getProb() / sumProb);
			}
			
			setDisplayBoard();
			
			System.gc();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected char checkBoard(int i, int j) {
		if (getDisplayBoard().getAt(i, j) == Piece.EMPTY_SPACE) return Piece.EMPTY_SPACE;
		measure(new ArrayList<>(Arrays.asList(i)), new ArrayList<>(Arrays.asList(j)));
		return getDisplayBoard().getAt(i, j);
	}
	
	protected void setPosition(int i, int j, char piece) {
		for (QuantumBoard qboard : possibleBoards) {
			qboard.board.setValue(i, j, piece);
		}
	}
	
	public void upgradePawn(char whitePiece, char blackPiece) {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Piece.isWhitePawn(getDisplayBoard().getAt(0, i)) && Piece.isWhitePawn(checkBoard(0, i))) {
				setPosition(0, i, whitePiece);
			}
			if (Piece.isBlackPawn(getDisplayBoard().getAt(BOARD_SIZE-1, i)) && Piece.isBlackPawn(checkBoard(BOARD_SIZE-1, i))) {
				setPosition(BOARD_SIZE-1, i, blackPiece);
			}
		}
		
		setDisplayBoard();
	}
	
	public boolean isUpgradePawnAvailable() {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Piece.isWhitePawn(getDisplayBoard().getAt(0, i)) && Piece.isWhitePawn(checkBoard(0, i))) {
				return true;
			}
			if (Piece.isBlackPawn(getDisplayBoard().getAt(BOARD_SIZE-1, i)) && Piece.isBlackPawn(checkBoard(BOARD_SIZE-1, i))) {
				return true;
			}
		}
		return false;
	}
	
	// actual move
	public void move(QuantumMove qmove, boolean hiddenMove) {
		
		if (qmove.prob <= 0) return;
		
		char purge = Piece.EMPTY_SPACE;
		lastMoveHiddenStatus = hiddenMove;
		if (hiddenMove) {
			if (NormalChessGame.isMoveValidNoKing(currentGame, qmove.move)) {
				char cell = this.currentGame.getAt(qmove.move.row2, qmove.move.col2);
				if (cell != Piece.EMPTY_SPACE) {
					this.deadPieces.add(cell);
					purge = cell;
				}
				
				this.currentGame.move(qmove.move, Piece.EMPTY_SPACE);
			}
		}
		
		ArrayList<QuantumBoard> newPossibleBoards = new ArrayList<>();
		for (QuantumBoard qboard : possibleBoards) {
			if (NormalChessGame.isMoveValidNoKing(qboard.board, qmove.move)) {
				if (qmove.prob < 1) {
					newPossibleBoards.add(
						new QuantumBoard(
							qboard.getProb() * qmove.prob, 
							qboard.board.moveDuplicate(qmove.move, Piece.EMPTY_SPACE)
						));
					
					qboard.setProb(qboard.getProb() * (1 - qmove.prob));
				} else {
					qboard.board.move(qmove.move, Piece.EMPTY_SPACE);
				}
			}
		}
		
		possibleBoards.addAll(newPossibleBoards);
		moves.add(qmove);

		char startPiece = displayBoard.getAt(qmove.move.row1, qmove.move.col1);
		char targetPiece = displayBoard.getAt(qmove.move.row2, qmove.move.col2);
		if (targetPiece != Piece.EMPTY_SPACE && targetPiece != startPiece) {
			measure(new ArrayList<>(Arrays.asList(qmove.move.row2)), new ArrayList<>(Arrays.asList(qmove.move.col2)));
		}
		
		// if there is a new dead
		if (purge != Piece.EMPTY_SPACE) {

			ArrayList<Integer> rows = new ArrayList<>();
			ArrayList<Integer> cols = new ArrayList<>();
			
			for (int i = 0; i < displayBoard.getRows(); ++i) {
				for (int j = 0; j < displayBoard.getColumns(); ++j) {
					if (displayBoard.getAt(i, j) == purge) {
						rows.add(i);
						cols.add(j);
					}
				}
			}
			
			measure(rows, cols);
		} else {
			setDisplayBoard();
		}
	}
	private boolean randomSuccess(double prob) {
		return Math.random() <= prob;
	}
	public void move(QuantumMove qmove) {
		move(qmove, randomSuccess(qmove.prob));
	}
	
	public void pass() {
		moves.add(null);
	}

	public boolean lastMoveStatus() { return lastMoveHiddenStatus; }
	
	public boolean isDead(char piece) {
		return deadPieces.contains(piece);
	}
	
	public Team getTurn() {
		return this.moves.size() % 2 == 0 ? this.firstTurn : 
			(this.firstTurn == Team.PLAYER_WHITE ? Team.PLAYER_BLACK : Team.PLAYER_WHITE);
	}

	public ChessBoard getDisplayBoard() {
		return displayBoard;
	}
	
	public double[][] getPiecePossibility() {
		return piecePossibility;
	}
	
	public int getGameResult() {
		
		// white king is dead
		if (isDead(Piece.WHITE_KING)) {
			return GAME_RESULT_BLACK_WINS;
		}
		
		// black king is dead
		if (isDead(Piece.BLACK_KING)) {
			return GAME_RESULT_WHITE_WINS;
		}
		
		return GAME_RESULT_ONGOING;
	}
	public static String getResultMessage(int result) {
		if (result == GAME_RESULT_DRAW) return "Draw";
		if (result == GAME_RESULT_WHITE_WINS) return "White wins";
		if (result == GAME_RESULT_BLACK_WINS) return "Black wins";
		return "game is on going";
	}
		
	
	private static class QuantumBoard {
		
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
	
	public static class QuantumMove {
		
		public final double prob;
		public final ChessBoard.Move move;
		
		public QuantumMove(double prob, ChessBoard.Move move) {
			this.prob = Math.max(0.0, Math.min(1.0, prob));
			this.move = move;			
		}
		
		public QuantumMove(String msg) {
			this(Double.valueOf(msg.substring(4)), new ChessBoard.Move(msg.substring(0, 4)));
		}
		
		public String encode() {
			return move.toString() + Double.toString(prob);
		}
		public String toString() {
			return encode();
		}
	}
}
