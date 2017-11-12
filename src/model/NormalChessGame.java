package model;

import java.util.ArrayList;

import model.piece.ChessPiece;

public class NormalChessGame {

	// player turn
	public static final int PLAYER_WHITE = 0;
	public static final int PLAYER_BLACK = 1;
	protected final int firstTurn;
	
	// board size
	public static final int BOARD_SIZE = 8;
	
	// for undo state
	private int versionIndex;
	private ArrayList<ChessBoard> versions;
	private ArrayList<String> moves;
	
	/*
	 * Constructor
	 * @param board or default board
	 */
	public NormalChessGame(String fin) {
		
		assert(fin.length() == BOARD_SIZE * BOARD_SIZE);
		
		versions = new ArrayList<>();
		moves = new ArrayList<>();
		versions.add(new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE));	
		
		versionIndex = 0;
		firstTurn = PLAYER_WHITE;
	}
	
	public NormalChessGame() {
		this("RNBQKBNR" + "PPPPPPPP" + "........" + "........" + "........" + "........" + "pppppppp" + "rnbqkbnr");
	}
	
	/*
	 * Change version (undo / redo)
	 */
	public void setVersion(int new_version) {
		this.versionIndex = new_version;
		if (this.versionIndex < 0) {
			System.out.println("Error: out of bound");
			this.versionIndex = 0;
		} else if (this.versionIndex >= versions.size()) {
			System.out.println("Error: out of bound");
			this.versionIndex = versions.size() - 1;
		}
	}
	public void moveVersion(int offset_version) {
		this.setVersion(this.versionIndex + offset_version);
	}
	
	/*
	 * Move function
	 */
	// return validation of the move
	public boolean isMoveValid(String moveString) {
		
		// TO DO: check validation of the move
		if (move.length() != 4) return false;
		
		ChessBoard board = this.getBoard();
		ChessBoard.Move move = new ChessBoard.Move(moveString);
		
		// out of bound
		if (!(0 <=  move.row_from && move.row_from < BOARD_SIZE && 0 <= move.col_from && move.col_from < BOARD_SIZE)) {
			return false;
		}
		if (!(0 <= move.row_to && move.row_to < BOARD_SIZE && 0 <= move.col_to && move.col_to < BOARD_SIZE)) {
			return false;
		}
		
		char piece = board.getAt(move.row_from, move.col_from);
		if (this.getTurn() == PLAYER_WHITE) {
			// move lower-case letter	
			if (!Character.isLowerCase(piece)) {
				return false;
			}
			try {
				return ChessPiece.getClassFromChar(piece).isValidMove(board, move);
			} catch (Exception e) {
				return false;
			}
		} else {
			// move upper-case letter
			if (!Character.isUpperCase(piece)) {
				return false;
			}
			try {
				return ChessPiece.getClassFromChar(piece).isValidMove(board, move);
			} catch (Exception e) {
				return false;
			}
		}
	}
	
	// create new version and add
	// return whether the move is successful
	public boolean move(String move) {
		if (!this.isMoveValid(move)) {
			return false;
		}
		
		ChessBoard board = this.getBoard();
		int row_from = move.charAt(0) - '1', col_from = move.charAt(1) - 'A';
		int row_to = move.charAt(2) - '1', col_to = move.charAt(3) - 'A';

		// add new version
		while (this.versions.size() > this.versionIndex + 1) {
			this.versions.remove(this.versions.size()-1);
			this.moves.remove(this.moves.size()-1);
		}
		this.versions.add(board.move(row_from, col_from, row_to, col_to, '.'));
		this.moves.add(move);
		this.versionIndex++;
		
		return true;
	}
	
	
	/*
	 * Winner status
	 */
	
	
	/*
	 * Getters
	 */
	public ChessBoard getBoard(int version) throws IndexOutOfBoundsException {
		return this.versions.get(version);
	}
	public ChessBoard getBoard() throws IndexOutOfBoundsException {
		return this.versions.get(this.versionIndex);
	}
	public String getMove(int version) throws IndexOutOfBoundsException {
		return this.moves.get(version);
	}
	public String getMove() throws IndexOutOfBoundsException {
		return this.moves.get(this.versionIndex);
	}
	public int getTurn() {
		return this.versionIndex % 2 == 0 ? this.firstTurn : 
			(this.firstTurn == PLAYER_WHITE ? PLAYER_BLACK : PLAYER_WHITE);
	}
	
	// toString function
	public String toString(String delimiter) {
		return this.getBoard().toString(delimiter);
	}
	public String toString() {
		return this.getBoard().toString();
	}
}
