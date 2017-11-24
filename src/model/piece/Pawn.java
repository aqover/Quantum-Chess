package model.piece;

import helper.Team;
import model.ChessBoard;
import model.NormalChessGame;
import scene.gameBoard.shareObject.GameHolder;

public class Pawn extends ChessPiece {

	private static Pawn instance = new Pawn(-1, -1, Team.NONE);
	
	public static Pawn getInstance() {
		return instance;
	}

	public Pawn(Integer row, Integer col, Team team) {
		super(row, col, team, (team != Team.NONE ? 
			team == Team.PLAYER_WHITE ? GameHolder.wp : GameHolder.bp 
			: null)
		);
	}

	
	@Override
	public boolean isValidMove(ChessBoard board, ChessBoard.Move move) {

		final Team sideFrom = NormalChessGame.getSide(board.getAt(move.row1, move.col1));
		final Team sideTo = NormalChessGame.getSide(board.getAt(move.row2, move.col2));

		if (sideFrom == sideTo) {
			return false;
		}
		
		int drow = move.row2 - move.row1;
		int dcol = move.col2 - move.col1;
		
		if (sideTo == Team.NONE) {
			if (sideFrom == Team.PLAYER_WHITE) {
				if (move.row1 == 6 && drow == -2 && NormalChessGame.getSide(board.getAt(move.row1-1, move.col1)) == Team.NONE) {
					return true;
				}
				return drow == -1 && dcol == 0;
			}
			if (sideFrom == Team.PLAYER_BLACK) {
				if (move.row1 == 1 && drow == 2 && NormalChessGame.getSide(board.getAt(move.row1+1, move.col1)) == Team.NONE) {
					return true;
				}
				return drow == 1 && dcol == 0;
			}
		}
		
		if (sideFrom != sideTo) {
			if (sideFrom == Team.PLAYER_WHITE) {
				return drow == -1 && Math.abs(dcol) == 1;
			}
			
			if (sideFrom == Team.PLAYER_BLACK) {
				return drow == 1 && Math.abs(dcol) == 1;
			}
		}
		
		return false;
	}
}