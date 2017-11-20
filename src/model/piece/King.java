package model.piece;

import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;

public class King extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, Move move) {

		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}
		
		int drow = Math.abs(move.row1 - move.row2);
		int dcol = Math.abs(move.col1 - move.col2);
		return Math.max(drow, dcol) == 1;
	}

	public static boolean isKingThreaten(ChessBoard board, char kingPiece) {
		
		int kingI = -1, kingJ = -1;
		for (int i = 0; i < board.rows(); ++i) {
			for (int j = 0; j < board.cols(); ++j) {
				if (board.getAt(i, j) == kingPiece) {
					kingI = i;
					kingJ = j;
				}
			}
		}
		
		if (kingI == -1) {
			return false;
		}

		for (int i = 0; i < board.rows(); ++i) {
			for (int j = 0; j < board.cols(); ++j) {
				char piece = board.getAt(i, j);
				if (NormalChessGame.getSide(piece) == NormalChessGame.PLAYER_NOSIDE) {
					continue;
				}
				if (NormalChessGame.getSide(piece) == NormalChessGame.getSide(kingPiece)) {
					continue;
				}
				
				try {
					if (ChessPiece.getClassFromChar(piece).isValidMove(board, new Move(i, j, kingI, kingJ))) {
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}
}
