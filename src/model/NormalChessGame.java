package model;

import java.util.ArrayList;
import java.util.List;

import helper.Team;
import model.piece.ChessPiece;
import model.piece.King;

public class NormalChessGame implements ChessGameInfo {

	public final Team firstTurn;
		
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
		firstTurn = Team.PLAYER_WHITE;
		
	}
	public NormalChessGame(String fin) {
		this(new ChessBoard(fin, BOARD_SIZE, BOARD_SIZE));	
	}
	
	public NormalChessGame() {
		this("RNBQKBNR" + "PPPPPPPP" + "........" + "........" + "........" + "........" + "pppppppp" + "rnbqkbnr");
	}
	
	// copy constructor
	public NormalChessGame(NormalChessGame game) {

		this.firstTurn = game.firstTurn;
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
			
			if (!ChessPiece.getClassFromChar(piece).isValidMove(board, move)) {
				return false;
			}
			
			char king = (this.getTurn() == Team.PLAYER_WHITE ? Piece.WHITE_KING : Piece.BLACK_KING);
			return !King.isKingThreaten(board.moveDuplicate(move, Piece.EMPTY_SPACE), king);
			
		} catch (Exception e) {
			return false;
		}
	}
	
	public static boolean isMoveValid(ChessBoard board, ChessBoard.Move move) {
		return (new NormalChessGame(board)).isMoveValid(move);
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
	
	public int gameResult() {
		
		// white is being threaten
		if (getTurn() == Team.PLAYER_WHITE && getPossibleMoves(Team.PLAYER_WHITE).size() == 0) {
			if (King.isKingThreaten(this.getBoard(), Piece.WHITE_KING)) {
				return GAME_RESULT_DRAW;
			} else {
				return GAME_RESULT_BLACK_WINS;
			}
		}
		
		// black is being threaten
		if (getTurn() == Team.PLAYER_BLACK && getPossibleMoves(Team.PLAYER_BLACK).size() == 0) {
			if (King.isKingThreaten(this.getBoard(), Piece.BLACK_KING)) {
				return GAME_RESULT_DRAW;
			} else {
				return GAME_RESULT_WHITE_WINS;
			}		
		}
		
		return GAME_RESULT_ONGOING;
	}
	
	public static Team getSide(char ch) {
		if (Character.isLowerCase(ch)) return Team.PLAYER_WHITE;
		if (Character.isUpperCase(ch)) return Team.PLAYER_BLACK;
		return Team.NONE;
	}
	
	/*
	 * Getters
	 */
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
		return this.versionIndex % 2 == 0 ? this.firstTurn : 
			(this.firstTurn == Team.PLAYER_WHITE ? Team.PLAYER_BLACK : Team.PLAYER_WHITE);
	}
	
	// toString function
	public String toString(String delimiter) {
		return this.getBoard().toString(delimiter);
	}
	public String toString() {
		return this.getBoard().toString();
	}
}
