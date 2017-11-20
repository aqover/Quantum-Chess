package model.piece;

import model.ChessBoard;
import model.NormalChessGame;

public class Queen extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, ChessBoard.Move move) {

		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}
		
		// check like rook
		if (move.row1 == move.row2) {
			for (int i = Math.min(move.col1, move.col2) + 1; i < Math.max(move.col1, move.col2); ++i) {
				if (NormalChessGame.getSide(board.getAt(move.row1, i)) != NormalChessGame.PLAYER_NOSIDE) {
					return false;
				}
			}
			
			return true;
		}
		
		if (move.col1 == move.col2) {
			for (int i = Math.min(move.row1, move.row2); i < Math.max(move.row1, move.row2); ++i) {
				if (NormalChessGame.getSide(board.getAt(i, move.col1)) != NormalChessGame.PLAYER_NOSIDE) {
					return false;
				}				
			}
			return true;
		}

		// check like bishop
		if (move.row1 + move.col1 == move.row2 + move.col2) {
			int brow = Math.min(move.row1, move.row2);
			int bcol = Math.max(move.col1, move.col2);
			for (int i = 1; i < Math.abs(move.row2 - move.row1); ++i) {
				if (NormalChessGame.getSide(board.getAt(brow + i, bcol - i)) != NormalChessGame.PLAYER_NOSIDE) {
					return false;
				}
			}
			return true;
		}
		
		if (move.row1 - move.col1 == move.row2 - move.col2) {
			int brow = Math.min(move.row1, move.row2);
			int bcol = Math.min(move.col1, move.col2);
			for (int i = 1; i < Math.abs(move.row2 - move.row1); ++i) {
				if (NormalChessGame.getSide(board.getAt(brow + i, bcol + i)) != NormalChessGame.PLAYER_NOSIDE) {
					return false;
				}
			}
			return true;
		}
				
		return false;
	}

}
