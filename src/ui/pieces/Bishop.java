package ui.pieces;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

public class Bishop extends Pieces {
	public Bishop(Canvas canvas) {
		super(canvas);
	}
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		String img_path = "file:resource/merida/bb.png";
		if (team == Team.B)
			img_path = "file:resource/merida/wb.png";
		
		gc.drawImage(new Image(img_path), 0, 0, canvas.getHeight(), canvas.getWidth());
	}
}
