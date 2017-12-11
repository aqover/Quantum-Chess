package scene.gameBoard.view;

import helper.Ultility;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import model.piece.ChessPiece;
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
		gc.setFill(Color.rgb(166, 166, 166, 0.35));
		gc.fillOval(x + h/4, y + h/4, h/2, h/2);
	}
	
	public void drawProbabilty(GraphicsContext gc, ChessPiece piece, double prob) {
		if (prob > 0) {
			gc.setStroke(Ultility.rgbFade(Color.RED, Color.GREENYELLOW, prob));	
			gc.setLineWidth(6);

            gc.setGlobalAlpha(prob / 2);
            
			gc.strokeArc(x + 6, y + 6, GameHolder.size - 12, GameHolder.size - 12, -90, 360*prob, ArcType.OPEN);
			gc.drawImage(piece.getDisplayImage(), x, y, h, h);
			
			gc.setGlobalAlpha(1);
		}
	}

}
