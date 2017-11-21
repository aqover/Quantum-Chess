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
	 * Move
	 * 
	 * apply move to the board
	 */
	public void move(Move move, char emptySpace) {
		try {
			if (move.isMoving()) {
				this.setValue(move.row2, move.col2, this.getAt(move.row1, move.col1));
				this.setValue(move.row1, move.col1, emptySpace);
			}
		} catch (Exception e) {}
	}
	
	public ChessBoard moveDuplicate(Move move, char emptySpace) {
		ChessBoard newBoard = new ChessBoard(this);
		newBoard.move(move, emptySpace);
		return newBoard;
	}

	/*
	 * Setter & Getter
	 */
	// set all cells to the given character
	public void fill(char ch) {
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < NUMBER_OF_COLUMNS; ++i) {
			buffer.append(ch);
		}
		
		for (int i = 0; i < NUMBER_OF_ROWS; ++i) {
			board[i] = buffer.toString();
		}
	}
	
	public int getRows() {
		return NUMBER_OF_ROWS;
	}
	public int getColumns() {
		return NUMBER_OF_COLUMNS;
	}

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
	 * Move class
	 * 
	 * Move String : "1A2B"
	 * meaning move from cell(1A) to cell(2B)
	 * Move String : "3H4C"
	 * meaning move from cell(3H) to cell(4C)
	 */
	public static class Move {
		
		private String message;
		public final int row1, col1, row2, col2;
		
		public Move(int row1, int col1, int row2, int col2) {
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
			this.message = "";
		}
		
		public Move(String move) throws Exception {
			this(move.charAt(0)-'1', move.charAt(1)-'A', move.charAt(2)-'1', move.charAt(3)-'A');
			this.message = move;	
			
			if (move.length() != 4) {
				throw new Exception("Move Invalid : " + move);
			}

		}
		
		public boolean isMoving() {
			return row1 != row2 || col1 != col2;
		}
		
		public boolean isInBound(int min_row, int min_col, int max_row, int max_col) {
			return min_row <= row1 && row1 <= max_row &&
				min_row <= row2 && row2 <= max_row &&
				min_col <= col1 && col1 <= max_col &&
				min_col <= col2 && col2 <= max_col;
		}
					
		public String toString() {
			return message;
		}
	}
}
