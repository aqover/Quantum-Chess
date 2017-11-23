package scene.gameBoard;

import java.util.ArrayList;

import helper.InputUtility;
import helper.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import scene.gameBoard.shareObject.IRenderable;
import scene.gameBoard.view.pieces.Bishop;
import scene.gameBoard.view.pieces.King;
import scene.gameBoard.view.pieces.Knight;
import scene.gameBoard.view.pieces.Pawn;
import scene.gameBoard.view.pieces.Queen;
import scene.gameBoard.view.pieces.Rook;

public class ChessBoard extends Canvas {
	public ChessBoard () {
		this.setHeight(8 * scene.gameBoard.shareObject.GameHolder.size);
		this.setWidth(8 * scene.gameBoard.shareObject.GameHolder.size);
		
		drawBackground();
		addListenEvents();
	}
	
	private void drawBackground() {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				this.getGraphicsContext2D().drawImage(
					((i+j)%2 == 0)? scene.gameBoard.shareObject.GameHolder.bgDark: scene.gameBoard.shareObject.GameHolder.bgLight, 
					i * scene.gameBoard.shareObject.GameHolder.size, 
					j * scene.gameBoard.shareObject.GameHolder.size,
					scene.gameBoard.shareObject.GameHolder.size,
					scene.gameBoard.shareObject.GameHolder.size
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
		ArrayList<IRenderable> entity = scene.gameBoard.shareObject.GameHolder.getInstance().getEntity();
		for (IRenderable tmp: entity)
		{
			if (tmp.isVisible() && !tmp.isDestroyed())
				tmp.draw(gc);
		}
	}

	public void setBoard(String[] board) {
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();
		char c; 
		Team t;
		for(int j=0;j < board.length;j++)
		{
			for(int i=0;i < board[j].length(); i++)
			{
				t = Character.isUpperCase(board[j].charAt(i))? Team.PLAYER_BLACK: Team.PLAYER_WHITE;
				c = Character.toLowerCase(board[j].charAt(i));
				switch(c) {
					case 'b': entity.add(new Bishop(j, i, t)); break;
					case 'k': entity.add(new King(j, i, t)); break;
					case 'n': entity.add(new Knight(j, i, t)); break;
					case 'p': entity.add(new Pawn(j, i, t)); break;
					case 'q': entity.add(new Queen(j, i, t)); break;
					case 'r': entity.add(new Rook(j, i, t)); break;
					default:
						break;
				}
			}
		}
		scene.gameBoard.shareObject.GameHolder.getInstance().setEntity(entity);
	}
}
