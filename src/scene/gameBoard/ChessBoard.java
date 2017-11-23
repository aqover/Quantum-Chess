package scene.gameBoard;

import java.util.ArrayList;

import helper.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import scene.gameBoard.shareObject.IRenderable;

public class ChessBoard extends Canvas {
	
	private boolean flipBoard;
	
	public ChessBoard () {
		this.setHeight(8 * scene.gameBoard.shareObject.GameHolder.size);
		this.setWidth(8 * scene.gameBoard.shareObject.GameHolder.size);
		
		drawBackground();
		addListenEvents();
		
		flipBoard = true;
	}
	
	public void flipBoard() {
		flipBoard = !flipBoard;
	}
	
	private void drawBackground() {
		
		for(int i=0;i<8;i++) {
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
}
