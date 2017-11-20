package model.piece;

import model.ChessBoard;
<<<<<<< HEAD
import model.NormalChessGame;
=======
>>>>>>> UI
import model.ChessBoard.Move;

public class Rook extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, Move move) {
<<<<<<< HEAD
		
		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}
		
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
		
=======
		// TODO Auto-generated method stub
>>>>>>> UI
		return false;
	}

}
