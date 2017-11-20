package model.piece;

import model.ChessBoard;
import model.NormalChessGame;

public class Pawn extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, ChessBoard.Move move) {

		System.out.println("Check Move : " + move.toString());
		System.out.println("" + move.row1 + " " + move.col1 + " # " + move.row2 + " " + move.col2);
		
		int sideFrom = board.getAt(move.row1, move.col1);
		int sideTo = board.getAt(move.row2, move.col2);

		if (sideFrom == sideTo) {
			return false;
		}
		
		int drow = move.row2 - move.row1;
		int dcol = move.col2 - move.col1;
		
		if (sideTo == NormalChessGame.PLAYER_NOSIDE) {
			if (sideFrom == NormalChessGame.PLAYER_WHITE) {
				if (move.row1 == 6 && drow == -2 && NormalChessGame.getSide(board.getAt(move.row1-1, move.col1)) == NormalChessGame.PLAYER_NOSIDE) {
					return true;
				}
				return drow == -1 && dcol == 0;
			}
			if (sideFrom == NormalChessGame.PLAYER_BLACK) {
				if (move.row1 == 1 && drow == 2 && NormalChessGame.getSide(board.getAt(move.row1+1, move.col1)) == NormalChessGame.PLAYER_NOSIDE) {
					return true;
				}
				return drow == 1 && dcol == 0;
			}
		}
		
		if (sideTo != sideFrom) {
			if (sideFrom == NormalChessGame.PLAYER_WHITE) {
				return drow == 1 && Math.abs(dcol) == 1;
			}
			
			if (sideFrom == NormalChessGame.PLAYER_BLACK) {
				return drow == -1 && Math.abs(dcol) == 1;
			}
		}
		
		return false;
	}
}
