package scene.gameBoard;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import helper.InputUtility;
import helper.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.NormalChessGame;
import model.ChessGameInfo.Piece;
import model.piece.ChessPiece;
import scene.gameBoard.shareObject.GameHolder;
import scene.gameBoard.shareObject.IRenderable;

public class ChessBoard extends Canvas {
	
	private boolean flipBoard;
	
	public ChessBoard () {
		this.setHeight(8 * GameHolder.size);
		this.setWidth(8 * GameHolder.size);
		
		drawBackground();
		addListenEvents();
		
		flipBoard = false;
	}
	
	public synchronized void flipBoard() {
		flipBoard = !flipBoard;
			
		for (IRenderable tmp: GameHolder.getInstance().getEntity())
		{
			if (tmp instanceof ChessPiece) {
				ChessPiece piece = (ChessPiece) tmp;
				piece.setPositionOnScreen(piece.getX(), GameHolder.size * 7 - piece.getY());
			}
		}	
	}
	
	public synchronized boolean isBoardFlipped() {
		return flipBoard;
	}
	
	private void drawBackground() {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				this.getGraphicsContext2D().drawImage(
					((i+j)%2 == 0)? GameHolder.bgDark: GameHolder.bgLight, 
					i * GameHolder.size, 
					j * GameHolder.size,
					GameHolder.size,
					GameHolder.size
				);
			}
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
		drawBackground();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		for (IRenderable tmp: GameHolder.getInstance().getEntity())
		{
			if (tmp instanceof ChessPiece) {
				ChessPiece piece = (ChessPiece) tmp;
				if (piece.isVisible() && !piece.isDestroyed())
					piece.draw(gc);			
			}
		}
	}

	public void setBoard(NormalChessGame game) {
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();
		
		for (int i = 0; i < game.getBoard().getRows(); ++i) {
			for (int j = 0; j < game.getBoard().getColumns(); ++j) {

				char piece = game.getBoard().getAt(i, j);
				
				if (piece != Piece.EMPTY_SPACE) {
				
					Team team = NormalChessGame.getSide(piece);
					
					try {
						
						@SuppressWarnings("unchecked")
						Class<? extends ChessPiece> pieceClass = (Class<? extends ChessPiece>) Class.forName(ChessPiece.getClassFromChar(piece).getClass().getName());
						
						Constructor<? extends ChessPiece> constructor = pieceClass.getConstructor(Integer.class, Integer.class, Team.class);
						ChessPiece chessPiece = (ChessPiece) constructor.newInstance(i, j, team);
							
						 entity.add(chessPiece);
					
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		GameHolder.getInstance().setEntity(entity);
	}
}
