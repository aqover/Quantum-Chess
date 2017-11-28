package model.piece;

import helper.Team;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.ChessBoard;
import model.ChessGameInfo;
import scene.gameBoard.shareObject.GameHolder;
import scene.gameBoard.view.Entity;

public abstract class ChessPiece extends Entity implements ChessGameInfo {

	protected final Image displayImage;
	
	protected final Team team;
	protected int row, col;
	protected boolean isdead; 
	
	public abstract boolean isValidMove(ChessBoard board, ChessBoard.Move move);
	
	public static ChessPiece getClassFromChar(char piece) throws NoPieceException {
		if (piece == Piece.WHITE_ROOK || piece == Piece.BLACK_ROOK) return Rook.getInstance();
		if (piece == Piece.WHITE_KNIGHT || piece == Piece.BLACK_KNIGHT) return Knight.getInstance();
		if (piece == Piece.WHITE_BISHOP || piece == Piece.BLACK_BISHOP) return Bishop.getInstance();
		if (piece == Piece.WHITE_KING || piece == Piece.BLACK_KING) return King.getInstance();
		if (piece == Piece.WHITE_QUEEN || piece == Piece.BLACK_QUEEN) return Queen.getInstance();
		if (piece == Piece.WHITE_PAWN || piece == Piece.BLACK_PAWN) return Pawn.getInstance();
		
		throw new NoPieceException(piece);
	}
	
	public void draw(GraphicsContext gc) {
		if (displayImage != null && isVisible()) {
			gc.drawImage(displayImage, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
			if (this.isSelected) {
				this.drawHover(gc);
			}
		}
	}
	
	public Team getTeam() { return team; }
	public int getRow() { return row; }
	public int getI() { return row; }
	public int getColumn() { return col; }
	public int getJ() { return col; }
	public boolean isDead() { return isdead; }
	
	public void setOnlyPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
		this.setPositionOnScreen(col * GameHolder.size, row * GameHolder.size);
	}
	
	public ChessPiece(int row, int col, Team team, Image displayImage) {
		this.row = row;
		this.col = col;
		this.team = team;
		this.displayImage = displayImage;
		
		this.isdead = false;
		
		this.setPosition(this.row, this.col);
	}
	
	
	/*
	 * Exception classes
	 */
	public static class NoPieceException extends Exception {
		
		private static final long serialVersionUID = 2122415123622064555L;
		private final String msg;

		public String getMessage() {
			return msg;
		}
		
		public NoPieceException(char ch) {
			this.msg = "This chess piece is unknown : " + ch;
		}
		
	}
}
