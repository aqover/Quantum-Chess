package model.piece;

import helper.Team;
import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;
import model.ChessGameInfo.Piece;
import scene.gameBoard.shareObject.GameHolder;

public class King extends ChessPiece {

	private static King instance = new King(-1, -1, Team.NONE);
	
	public static King getInstance() {
		return instance;
	}
	
	public King(Integer row, Integer col, Team team) {
		super(row, col, team, (team != Team.NONE ? 
			team == Team.PLAYER_WHITE ? GameHolder.wk : GameHolder.bk 
			: null)
		);
	}

	public char getWhitePiece() { return Piece.WHITE_KING; }
	public char getBlackPiece() { return Piece.BLACK_KING; }

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
						if (ChessPiece.getInstance(piece).isValidMove(board, move)) {
							return true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return false;
	}
}
