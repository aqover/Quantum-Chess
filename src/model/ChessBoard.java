package model;

public class ChessBoard {

	// board variables
	private String[] board;
	private final int NUMBER_OF_ROWS;
	private final int NUMBER_OF_COLUMNS;
	
	/*
	 * Constructors
	 */
	public ChessBoard(String board, int number_of_rows, int number_of_columns) {
		
		assert(board.length() == number_of_rows * number_of_columns);
		this.NUMBER_OF_ROWS = number_of_rows;
		this.NUMBER_OF_COLUMNS = number_of_columns;
		
		this.board = new String[NUMBER_OF_ROWS];
		for (int i = 0; i < NUMBER_OF_ROWS; ++i) {
			this.board[i] = board.substring(i * NUMBER_OF_ROWS, (i+1) * NUMBER_OF_ROWS);
		}
	}
	public ChessBoard(ChessBoard board) {
		this.NUMBER_OF_ROWS = board.NUMBER_OF_ROWS;
		this.NUMBER_OF_COLUMNS = board.NUMBER_OF_COLUMNS;
		this.board = new String[NUMBER_OF_ROWS];
		for (int i = 0; i < NUMBER_OF_ROWS; ++i) {
			this.board[i] = board.getBoard()[i];
		}
	}

	/*
	 * Setter & Getter
	 */
	// return character at the given position (0-index) 
	public char getAt(int row, int col) throws IndexOutOfBoundsException {
		return this.board[row].charAt(col);
	}
	
	public void setValue(int row, int col, char piece) {
		board[row] = board[row].substring(0,  col) + piece + board[row].substring(col+1);
	}
	
	public String toString(String delimiter) {
		return String.join(delimiter, this.board);
	}
	public String toString() {
		return this.toString("\n");
	}
	public String[] getBoard() {
		return this.board;
	}

	/*
	 * Internal classes
	 */
	public static class Move {
		
		private String message;
		public final int row_from, col_from, row_to, col_to;
		
		public Move(int row_from, int col_from, int row_to, int col_to) {
			
			this.row_from = row_from;
			this.col_from = col_from;
			this.row_to = row_to;
			this.col_to = col_to;
			this.message = "";
		}
		
		public Move(String move) {
			this(move.charAt(0)-'1', move.charAt(1)-'A', move.charAt(2)-'1', move.charAt(3)-'A');
			this.message = move;
		}
		
		public String toString() {
			return message;
		}
	}
}
