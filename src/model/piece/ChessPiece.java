package model.piece;

import model.ChessBoard;
import model.ChessGameInfo;

public abstract class ChessPiece implements ChessGameInfo {

	public abstract boolean isValidMove(ChessBoard board, ChessBoard.Move move);
	
	public static ChessPiece getClassFromChar(char piece) throws NoPieceException {
		if (piece == Piece.WHITE_ROOK || piece == Piece.BLACK_ROOK) return Rook.getInstance();
		if (piece == Piece.WHITE_KNIGHT || piece == Piece.BLACK_KNIGHT) return Knight.getInstance();
		if (piece == Piece.WHITE_BISHOP || piece == Piece.BLACK_BISHOP) return Bishop.getInstance();
		if (piece == Piece.WHITE_KING || piece == Piece.BLACK_KING) return King.getInstance();
		if (piece == Piece.WHITE_QUEEN || piece == Piece.BLACK_QUEEN) return Queen.getInstance();
		if (piece == Piece.WHITE_PAWN || piece == Piece.BLACK_PAWN) return Pawn.getInstance();
		
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
