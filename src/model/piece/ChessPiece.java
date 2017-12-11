package model.piece;

import helper.Team;
import helper.Ultility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import model.ChessBoard;
import model.ChessGameInfo;
import scene.gameBoard.shareObject.GameHolder;
import scene.gameBoard.view.Entity;

public abstract class ChessPiece extends Entity implements ChessGameInfo {

	protected final Image displayImage;
	
	protected final Team team;
	protected int row, col;
	protected boolean isdead; 
	
	protected double prob;
	
	protected boolean isSelected;
	protected boolean isLastMoved;
	
	public abstract boolean isValidMove(ChessBoard board, ChessBoard.Move move);
	
	public static ChessPiece getInstance(char piece) throws NoPieceException {
		if (piece == Piece.WHITE_ROOK || piece == Piece.BLACK_ROOK) return Rook.getInstance();
		if (piece == Piece.WHITE_KNIGHT || piece == Piece.BLACK_KNIGHT) return Knight.getInstance();
		if (piece == Piece.WHITE_BISHOP || piece == Piece.BLACK_BISHOP) return Bishop.getInstance();
		if (piece == Piece.WHITE_KING || piece == Piece.BLACK_KING) return King.getInstance();
		if (piece == Piece.WHITE_QUEEN || piece == Piece.BLACK_QUEEN) return Queen.getInstance();
		if (Piece.isPawn(piece)) return Pawn.getInstance();
		throw new NoPieceException(piece);
	}
	
	public abstract char getWhitePiece();
	public abstract char getBlackPiece();
	
	public void draw(GraphicsContext gc) {
		if (displayImage != null && isVisible()) {
			gc.setStroke(Ultility.rgbFade(Color.RED, Color.GREENYELLOW, prob));
			gc.setLineWidth(6);
			gc.strokeArc(x + 6, y + 6, GameHolder.size - 12, GameHolder.size - 12, -90, 360*prob, ArcType.OPEN);
			
			gc.drawImage(displayImage, 
				x + ((prob < 1.0)? GameHolder.size*0.05: 0), 
				y + ((prob < 1.0)? GameHolder.size*0.05: 0), 
				(prob < 1.0)? GameHolder.size*0.9 : GameHolder.size, 
				(prob < 1.0)? GameHolder.size*0.9 : GameHolder.size);
			
			if (this.isLastMoved) {
				this.drawLastMoved(gc);
			}
			
			if (this.isSelected) {
				this.drawHover(gc);
			}
		}
	}
	
	public void drawLastMoved(GraphicsContext gc) {
		gc.setFill(Color.rgb(255, 255, 0, 0.3));
		gc.fillRect(x,  y, GameHolder.size, GameHolder.size);
	}
	
	public void drawHover(GraphicsContext gc) {
		gc.setFill(Color.rgb(100, 100, 0, 0.5));
		gc.fillRect(x,  y, GameHolder.size, GameHolder.size);
	}

	
	public Team getTeam() { return team; }
	public int getRow() { return row; }
	public int getI() { return row; }
	public int getColumn() { return col; }
	public int getJ() { return col; }
	public boolean isDead() { return isdead; }
	

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
	public boolean isLastMoved() {
		return isLastMoved;
	}
	
	public void setLastMoved(boolean isLastMoved) {
		this.isLastMoved = isLastMoved;
	}

	public void setOnlyPosition(int row, int col) {
		this.row = row;
		this.col = col;
	}
	
	public void setPositionOnScreen(int row, int col) {
		this.setPositionOnScreen(row * GameHolder.size, col * GameHolder.size);
	}
	
	public void setPosition(int row, int col) {
		this.row = row;
		this.col = col;
		this.setPositionOnScreen(col * GameHolder.size, row * GameHolder.size);
	}
	
	public void setPossibility(double prob) {
		this.prob = prob;
	}
	
	public ChessPiece(int row, int col, Team team, Image displayImage) {
		this.row = row;
		this.col = col;
		this.team = team;
		this.displayImage = displayImage;
		this.z = 1;
		this.prob = 1.0;
		
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
