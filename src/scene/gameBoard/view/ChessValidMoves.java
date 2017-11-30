package scene.gameBoard.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import scene.gameBoard.shareObject.GameHolder;

public class ChessValidMoves extends Entity {

	private static double h = GameHolder.size;
	private int row, col;
	
	public ChessValidMoves(int x, int y) {
		super();
		row = x;
		col = y;
		z = 2;
		setPositionOnScreen(y * h, x * h);
	}
	
	public int getI() { return row; }
	public int getJ() { return col; }
	
	@Override
	public void draw(GraphicsContext gc) {
		
		gc.setFill(Color.WHITE);
		gc.fillOval(x + h/4, y + h/4, h/2, h/2);
	}

}
