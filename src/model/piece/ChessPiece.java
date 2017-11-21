package model.piece;

import model.ChessBoard;
import model.ChessGameInfo;

public abstract class ChessPiece implements ChessGameInfo {

	public abstract boolean isValidMove(ChessBoard board, ChessBoard.Move move);
	
	public static ChessPiece getClassFromChar(char piece) throws NoPieceException {
		if (piece == Piece.WHITE_ROOK || piece == Piece.BLACK_ROOK) return new Rook();
		if (piece == Piece.WHITE_KNIGHT || piece == Piece.BLACK_KNIGHT) return new Knight();
		if (piece == Piece.WHITE_BISHOP || piece == Piece.BLACK_BISHOP) return new Bishop();
		if (piece == Piece.WHITE_KING || piece == Piece.BLACK_KING) return new King();
		if (piece == Piece.WHITE_QUEEN || piece == Piece.BLACK_QUEEN) return new Queen();
		if (piece == Piece.WHITE_PAWN || piece == Piece.BLACK_PAWN) return new Pawn();
		
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
