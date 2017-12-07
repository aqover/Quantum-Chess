package scene.gameBoard.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
		gc.setFill(Color.rgb(255, 0, 0, 0.5));
		gc.fillOval(x + h/4, y + h/4, h/2, h/2);
	}
	
	public void drawProbabilty(GraphicsContext gc, double prob) {
		if (prob > 0) {
			gc.setFont(new Font(10));
			gc.setFill(Color.BLACK);
			gc.fillText(String.format("%d", (int) (prob * 100)), x, y + h);
		}
	}

}
