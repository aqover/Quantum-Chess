package model.piece;

import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;

public class Knight extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, Move move) {

		if (NormalChessGame.getSide(board.getAt(move.row1, move.col1)) == 
			NormalChessGame.getSide(board.getAt(move.row2, move.col2))) {
			return false;
		}

		int drow = Math.abs(move.row1 - move.row2);
		int dcol = Math.abs(move.col1 - move.col2);
		return (drow == 1 && dcol == 2) || (drow == 2 && dcol == 1);
	}

}
