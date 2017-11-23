package model.piece;

import helper.Team;
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
		
		Team kside = NormalChessGame.getSide(kingPiece);
		int krow = -1, kcol = -1;
		
		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getColumns(); ++j) {
				if (board.getAt(i, j) == kingPiece) {
					krow = i;
					kcol = j;
				}
			}
		}
		
		if (krow == -1) {
			return false;
		}
		
		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getColumns(); ++j) {
				char piece = board.getAt(i, j);
				Team pieceSide = NormalChessGame.getSide(piece);
				if (pieceSide != kside && pieceSide != Team.NONE) {
					ChessBoard.Move move = new ChessBoard.Move(i, j, krow, kcol);
					try {
						if (ChessPiece.getClassFromChar(piece).isValidMove(board, move)) {
							return true;
						}
					} catch (Exception e) {}
				}
			}
		}

		return false;
	}
}
