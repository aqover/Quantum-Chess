package model.piece;

import helper.Team;
import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;
import model.ChessGameInfo.Piece;
import scene.gameBoard.shareObject.GameHolder;

public class Knight extends ChessPiece {

	private static Knight instance = new Knight(-1, -1, Team.NONE);
	
	public static Knight getInstance() {
		return instance;
	}
	
	public char getWhitePiece() { return Piece.WHITE_KNIGHT; }
	public char getBlackPiece() { return Piece.BLACK_KNIGHT; }

	public Knight(Integer row, Integer col, Team team) {
		super(row, col, team, (team != Team.NONE ? 
			team == Team.PLAYER_WHITE ? GameHolder.wn : GameHolder.bn 
			: null)
		);
	}

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
