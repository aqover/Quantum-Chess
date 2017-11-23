package model.piece;

import helper.Team;
import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;

public class Bishop extends ChessPiece {

	private static Bishop instance = new Bishop();

	public static Bishop getInstance() {
		return instance;
	}
	
	@Override
	public boolean isValidMove(ChessBoard board, Move move) {
		
		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}
		
		if (move.row1 + move.col1 == move.row2 + move.col2) {
			int brow = Math.min(move.row1, move.row2);
			int bcol = Math.max(move.col1, move.col2);
			for (int i = 1; i < Math.abs(move.row2 - move.row1); ++i) {
				if (NormalChessGame.getSide(board.getAt(brow + i, bcol - i)) != Team.NONE) {
					return false;
				}
			}
			return true;
		}
		
		if (move.row1 - move.col1 == move.row2 - move.col2) {
			int brow = Math.min(move.row1, move.row2);
			int bcol = Math.min(move.col1, move.col2);
			for (int i = 1; i < Math.abs(move.row2 - move.row1); ++i) {
				if (NormalChessGame.getSide(board.getAt(brow + i, bcol + i)) != Team.NONE) {
					return false;
				}
			}
			return true;
		}
		
		return false;
	}

}
