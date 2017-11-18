package model.piece;

import model.ChessBoard;
import model.ChessBoard.Move;

public class King extends ChessPiece {

	@Override
	public boolean isValidMove(ChessBoard board, Move move) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isKingThreaten(ChessBoard board, char kingPiece) {
		// TODO implements this
		return false;
	}
}
