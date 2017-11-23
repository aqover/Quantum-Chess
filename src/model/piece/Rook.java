package model.piece;

import helper.Team;
import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;

public class Rook extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, Move move) {

		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}
		
		if (move.row1 == move.row2) {
			for (int i = Math.min(move.col1, move.col2) + 1; i < Math.max(move.col1, move.col2); ++i) {
				if (NormalChessGame.getSide(board.getAt(move.row1, i)) != Team.NONE) {
					return false;
				}
			}
			
			return true;
		}
		
		if (move.col1 == move.col2) {
			for (int i = Math.min(move.row1, move.row2); i < Math.max(move.row1, move.row2); ++i) {
				if (NormalChessGame.getSide(board.getAt(i, move.col1)) != Team.NONE) {
					return false;
				}				
			}
			return true;
		}
		
		return false;
	}

}
