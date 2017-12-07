package scene.gameBoard;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import helper.InputUtility;
import helper.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.NormalChessGame;
import model.QuantumChessGame;
import model.ChessGameInfo.Piece;
import model.piece.ChessPiece;
import model.piece.Pawn;
import scene.gameBoard.shareObject.GameHolder;
import scene.gameBoard.shareObject.IRenderable;
import scene.gameBoard.view.ChessBackGround;
import scene.gameBoard.view.ChessValidMoves;

public class ChessBoard extends Canvas {
	
	private boolean flipBoard;
	
	public ChessBoard () {
		this.setHeight(8 * GameHolder.size);
		this.setWidth(8 * GameHolder.size);
		
		addListenEvents();
		
		flipBoard = false;
	}
	
	public void flipBoard() {
		flipBoard = !flipBoard;
			
		for (IRenderable tmp: GameHolder.getInstance().getEntity())
		{
			if (tmp instanceof ChessPiece) {
				ChessPiece piece = (ChessPiece) tmp;
				piece.setPositionOnScreen(GameHolder.size * 7 - piece.getX(), GameHolder.size * 7 - piece.getY());
			}
			
			if (tmp instanceof ChessValidMoves) {
				ChessValidMoves move = (ChessValidMoves) tmp;
				move.setPositionOnScreen(GameHolder.size * 7 - move.getX(), GameHolder.size * 7 - move.getY());
			}
		}	
	}
	
	public boolean isBoardFlipped() {
		return flipBoard;
	}
	
	private void addListenEvents() {
		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
			{
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
				InputUtility.mouseLeftDown();
			}
			
			if (event.getButton() == MouseButton.SECONDARY)
			{
				InputUtility.mouseRightDown();
			}
		});
		
		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
			{
				InputUtility.mouseLeftRelease();
			}
			
			if (event.getButton() == MouseButton.SECONDARY)
			{
				InputUtility.mouseRightRelease();
			}
		});
	}
	
	public void paintComponent() {
		
		GraphicsContext gc = this.getGraphicsContext2D();
		for (IRenderable tmp: GameHolder.getInstance().getEntity())
		{
			if (tmp instanceof ChessBackGround) {
				ChessBackGround bg = (ChessBackGround) tmp;
				if (bg.isVisible() && !bg.isDestroyed())
					bg.draw(gc);
			}
			
			if (tmp instanceof ChessPiece) {
				ChessPiece piece = (ChessPiece) tmp;
				if (piece.isVisible() && !piece.isDestroyed())
					piece.draw(gc);			
			}
		}
	}
	
	public void paintValidMoves(boolean[][] moves) {

		GraphicsContext gc = this.getGraphicsContext2D();
		for (IRenderable tmp: GameHolder.getInstance().getEntity()) {
			if (tmp instanceof ChessValidMoves) {
				ChessValidMoves piece = (ChessValidMoves) tmp;
				if (moves[piece.getI()][piece.getJ()] && piece.isVisible() && !piece.isDestroyed())
					piece.draw(gc);
			}
		}
		
	}

	public void paintPossibilityMoves(double[][] moves, double moveProb) {
		
		GraphicsContext gc = this.getGraphicsContext2D();
		for (IRenderable tmp: GameHolder.getInstance().getEntity()) {
			if (tmp instanceof ChessValidMoves) {
				ChessValidMoves piece = (ChessValidMoves) tmp;
				if (piece.isVisible() && !piece.isDestroyed())
					piece.drawProbabilty(gc, moves[piece.getI()][piece.getJ()] * moveProb);
			}
		}
	}
	public void updatePawn(Constructor<? extends ChessPiece> constructor) {
		
		ArrayList<IRenderable> newPawns = new ArrayList<>();
		
		for (IRenderable tmp : GameHolder.getInstance().getEntity()) {
			if (tmp instanceof Pawn) {
				Pawn pawn = (Pawn) tmp;
				if (pawn.getI() == 0 || pawn.getI() == 7) {
					try {
						ChessPiece npawn = (ChessPiece) constructor.newInstance(pawn.getI(), pawn.getJ(), pawn.getTeam());
						npawn.setPositionOnScreen(pawn.getX(), pawn.getY());
						newPawns.add(npawn);
						pawn.Destroy();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		GameHolder.getInstance().addEntity(newPawns);
	}
	
	public void setBoard(NormalChessGame game) {
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();
		
		boolean flipStatus = flipBoard;
		
		flipBoard = false;
		entity.add(new ChessBackGround());
		
		for (int i = 0; i < game.getBoard().getRows(); ++i) {
			for (int j = 0; j < game.getBoard().getColumns(); ++j) {

				char piece = game.getBoard().getAt(i, j);

				if (piece != Piece.EMPTY_SPACE) {
				
					Team team = NormalChessGame.getSide(piece);
					
					try {
						
						Class<? extends ChessPiece> pieceClass = (Class<? extends ChessPiece>) ChessPiece.getInstance(piece).getClass();
						
						Constructor<? extends ChessPiece> constructor = pieceClass.getConstructor(Integer.class, Integer.class, Team.class);
						ChessPiece chessPiece = (ChessPiece) constructor.newInstance(i, j, team);
						
						entity.add(chessPiece);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				entity.add(new ChessValidMoves(i, j));
			}
		}
		
		GameHolder.getInstance().setEntity(entity);
		
		if (flipStatus) { flipBoard(); }
	}

	public void setBoard(QuantumChessGame game) {
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();

		boolean flipStatus = flipBoard;
		
		flipBoard = false;
		entity.add(new ChessBackGround());
		
		model.ChessBoard board = game.getDisplayBoard();
		double[][] possibility = game.getPiecePossibility();
		
		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getColumns(); ++j) {

				char piece = board.getAt(i, j);
				if (piece != Piece.EMPTY_SPACE) {
				
					Team team = NormalChessGame.getSide(piece);
					
					try {
						
						Class<? extends ChessPiece> pieceClass = (Class<? extends ChessPiece>) ChessPiece.getInstance(piece).getClass();
						
						Constructor<? extends ChessPiece> constructor = pieceClass.getConstructor(Integer.class, Integer.class, Team.class);
						ChessPiece chessPiece = (ChessPiece) constructor.newInstance(i, j, team);
						
						chessPiece.setPossibility(possibility[i][j]);

						entity.add(chessPiece);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				entity.add(new ChessValidMoves(i, j));
			}
		}
		
		GameHolder.getInstance().setEntity(entity);

		if (flipStatus) { flipBoard(); }
	}
}
