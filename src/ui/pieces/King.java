package ui.pieces;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class King extends Pieces implements CornerMove, StraightMove {
	public King(Canvas canvas) {
		super(canvas);
	}

	@Override
	public void drawStraightMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawCornerMove() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		String img_path = "file:resource/merida/bk.png";
		if (team == Team.B)
			img_path = "file:resource/merida/wk.png";
		
		gc.drawImage(new Image(img_path), 0, 0, canvas.getHeight(), canvas.getWidth());
	}
}
