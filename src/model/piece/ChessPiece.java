package model.piece;

import model.ChessBoard;

public abstract class ChessPiece {

	public abstract boolean isValidMove(ChessBoard board, ChessBoard.Move move);
	
	public ChessBoard getChessBoardAfterMove(ChessBoard board, ChessBoard.Move move, char empty_cell) {
		ChessBoard new_board = new ChessBoard(board);
		new_board.setValue(move.row1, move.col1, board.getAt(move.row2, move.col2));
		new_board.setValue(move.row2, move.col2, empty_cell);
		return new_board;
	}
	
	public static ChessPiece getClassFromChar(char piece) throws NoPieceException {
		if (piece == 'r' || piece == 'R') return new Rook();
		if (piece == 'n' || piece == 'N') return new Knight();
		if (piece == 'b' || piece == 'B') return new Bishop();
		if (piece == 'k' || piece == 'K') return new King();
		if (piece == 'q' || piece == 'Q') return new Queen();
		if (piece == 'p' || piece == 'P') return new Pawn();
		
		throw new NoPieceException(piece);
	}
	
	/*
	 * Exception classes
	 */
	public static class NoPieceException extends Exception {
		
		private static final long serialVersionUID = 2122415123622064555L;
		private final String msg;

		public String getMessage() {
			return msg;
		}
		
		public NoPieceException(char ch) {
			this.msg = "This chess piece is unknown : " + ch;
		}
		
	}
}
