package model;

import java.util.ArrayList;
import java.util.List;

import helper.Team;
import model.piece.ChessPiece;
import model.piece.King;

public class NormalChessGame implements ChessGameInfo {

	// first turn
	public final Team FIRST_TURN;
		
	// for undo state
	private int versionIndex;
	private ArrayList<ChessBoard> versions;
	private ArrayList<ChessBoard.Move> moves;
	
	/*
	 * Constructor
	 * @param board or default board
	 */
	
	public NormalChessGame(ChessBoard board) {
		
		versions = new ArrayList<>();
		moves = new ArrayList<>();
		versions.add(board);
		
		versionIndex = 0;
		FIRST_TURN = Team.PLAYER_WHITE;
		
	}
	public NormalChessGame(String fin) {
		this(new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE));	
	}
	
	public NormalChessGame() {
		this("RNBQKBNR" + "!@#$%^&*" + "........" + "........" + "........" + "........" + "12345678" + "rnbqkbnr");
	}
	
	// copy constructor
	public NormalChessGame(NormalChessGame game) {

		this.FIRST_TURN = game.FIRST_TURN;
		this.versionIndex = game.versionIndex;

		this.versions = new ArrayList<>(game.versions);
		this.moves = new ArrayList<>(game.moves);
	}
	
	/*
	 * Change version (undo / redo)
	 */
	public void setVersion(int new_version) {
		this.versionIndex = new_version;
		if (this.versionIndex < 0) {
			System.out.println("Error: out of bound");
			this.versionIndex = 0;
		} else if (this.versionIndex >= versions.size()) {
			System.out.println("Error: out of bound");
			this.versionIndex = versions.size() - 1;
		}
	}
	
	public void moveVersion(int offset_version) {
		this.setVersion(this.versionIndex + offset_version);
	}
	
	public boolean undo() {
		if (this.versionIndex == 0) return false;
		setVersion(this.versionIndex - 1);
		return true;
	}
	
	public boolean redo() {
		if (this.versionIndex == versions.size()-1) return false;
		setVersion(this.versionIndex + 1);
		return true;
		
	}
	/*
	 * Move function
	 */
	// return validation of the move
	public boolean isMoveValid(ChessBoard.Move move) {
		try {
			ChessBoard board = this.getBoard();
			
			// out of bound
			if (!move.isInBound(0, 0, BOARD_SIZE-1, BOARD_SIZE-1)) {
				return false;
			}
			
			char piece = board.getAt(move.row1, move.col1);
			if (this.getTurn() != NormalChessGame.getSide(piece)) {
				return false;
			}
			
			if (!ChessPiece.getInstance(piece).isValidMove(board, move)) {
				return false;
			}
			
			char king = (this.getTurn() == Team.PLAYER_WHITE ? Piece.WHITE_KING : Piece.BLACK_KING);
			return !King.isKingThreaten(board.moveDuplicate(move, Piece.EMPTY_SPACE), king);
			
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isMoveValidNoKing(ChessBoard board, ChessBoard.Move move) {
		try {
			
			// out of bound
			if (!move.isInBound(0, 0, BOARD_SIZE-1, BOARD_SIZE-1)) {
				return false;
			}
			
			return ChessPiece.getInstance(board.getAt(move.row1, move.col1)).isValidMove(board, move);
			
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isMoveValid(ChessBoard board, ChessBoard.Move move) {
		try {
			
			// out of bound
			if (!move.isInBound(0, 0, BOARD_SIZE-1, BOARD_SIZE-1)) {
				return false;
			}
			
			char piece = board.getAt(move.row1, move.col1);
			if (!ChessPiece.getInstance(piece).isValidMove(board, move)) {
				return false;
			}
			
			char king = (Piece.isWhite(piece) ? Piece.WHITE_KING : Piece.BLACK_KING);
			return !King.isKingThreaten(board.moveDuplicate(move, Piece.EMPTY_SPACE), king);
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean isUpgradePawnAvailable() {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Piece.isWhitePawn(getBoard().getAt(0, i))) {
				return true;
			}
			
			if (Piece.isBlackPawn(getBoard().getAt(BOARD_SIZE-1, i))) {
				return true;
			}
		}
		return false;
	}
	
	protected void setPosition(int i, int j, char piece) {
		getBoard().setValue(i, j, piece);
	}
	public void upgradePawn(char whitePiece, char blackPiece) {
		for (int i = 0; i < BOARD_SIZE; ++i) {
			if (Piece.isWhitePawn(getBoard().getAt(0, i))) {
				setPosition(0, i, whitePiece);
			}
			if (Piece.isBlackPawn(getBoard().getAt(BOARD_SIZE-1, i))) {
				setPosition(BOARD_SIZE-1, i, blackPiece);
			}
		}
	}
	
	// create new version and add
	// return whether the move is successful
	public boolean move(ChessBoard.Move move) {
		try {

			if (!isMoveValid(move)) {
				return false;
			}
			
			ChessBoard board = this.getBoard();
			
			// adjust history
			while (this.versions.size() > this.versionIndex + 1) {
				this.versions.remove(this.versions.size()-1);
				this.moves.remove(this.moves.size()-1);
			}
				
			// add new version
			this.versions.add(board.moveDuplicate(move, Piece.EMPTY_SPACE));
			this.moves.add(move);
			this.versionIndex++;
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	/*
	 * Winner status
	 */
	
	public int getGameResult() {
		
		// white is being threaten
		if (getTurn() == Team.PLAYER_WHITE && getPossibleMoves(Team.PLAYER_WHITE).size() == 0) {
			if (King.isKingThreaten(this.getBoard(), Piece.WHITE_KING)) {
				return GAME_RESULT_BLACK_WINS;
			} else {
				return GAME_RESULT_DRAW;
			}
		}
		
		// black is being threaten
		if (getTurn() == Team.PLAYER_BLACK && getPossibleMoves(Team.PLAYER_BLACK).size() == 0) {
			if (King.isKingThreaten(this.getBoard(), Piece.BLACK_KING)) {
				return GAME_RESULT_WHITE_WINS;
			} else {
				return GAME_RESULT_DRAW;
			}		
		}
		
		return GAME_RESULT_ONGOING;
	}
	public static String getResultMessage(int result) {
		if (result == GAME_RESULT_DRAW) return "draw";
		if (result == GAME_RESULT_WHITE_WINS) return "White wins";
		if (result == GAME_RESULT_BLACK_WINS) return "Black wins";
		return "game is on going";
	}
	
	
	/*
	 * Getters
	 */
	public static Team getSide(char ch) {
		if (Piece.isWhite(ch)) return Team.PLAYER_WHITE;
		if (Piece.isBlack(ch)) return Team.PLAYER_BLACK;
		return Team.NONE;
	}
	
	public List<ChessBoard.Move> getPossibleMoves(Team player) { 

		ChessBoard board = getBoard();
		ArrayList<ChessBoard.Move> moves = new ArrayList<>();
		
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				
				if (getSide(board.getAt(i, j)) != player) {
					continue;
				}
				
				for (int ii = 0; ii < BOARD_SIZE; ++ii) {
					for (int jj = 0; jj < BOARD_SIZE; ++jj) {
						ChessBoard.Move move = new ChessBoard.Move(i, j, ii, jj);
						if (this.isMoveValid(move)) {
							moves.add(move);
						}
					}
				}
			}
		}

		return moves;
	}
	
	public boolean[][] getValidMoves(int row, int col) {
		
		boolean[][] moves = new boolean[BOARD_SIZE][BOARD_SIZE];	
		for (int i = 0; i < BOARD_SIZE; ++i) {
			for (int j = 0; j < BOARD_SIZE; ++j) {
				ChessBoard.Move move = new ChessBoard.Move(row, col, i, j);
				moves[i][j] = this.isMoveValid(move);
			}
		}
		
		return moves;
	}
	
	public ChessBoard getBoard(int version) throws IndexOutOfBoundsException {
		return this.versions.get(version);
	}
	public ChessBoard getBoard() throws IndexOutOfBoundsException {
		return this.versions.get(this.versionIndex);
	}
	public ChessBoard.Move getMove(int version) throws IndexOutOfBoundsException {
		return this.moves.get(version);
	}
	public ChessBoard.Move getMove() throws IndexOutOfBoundsException {
		return this.moves.get(this.versionIndex);
	}
	
	public Team getTurn() {
		return this.versionIndex % 2 == 0 ? this.FIRST_TURN : 
			(this.FIRST_TURN == Team.PLAYER_WHITE ? Team.PLAYER_BLACK : Team.PLAYER_WHITE);
	}
	
	// toString function
	public String toString(String delimiter) {
		return this.getBoard().toString(delimiter);
	}
	public String toString() {
		return this.getBoard().toString();
	}
}
