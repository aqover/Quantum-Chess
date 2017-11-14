package ui;

import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class Tile extends Canvas {
	private double size;
	
	private GraphicsContext gc;
	
	public Tile() {
		this.gc = getGraphicsContext2D();
	}
	
	public void setSize(double size) {
		this.size = size;
		this.setWidth(size);
		this.setHeight(size);
	}

	public void drawBackgroud(int type)
	{
		String img_path = "file:resource/square_light.jpg";
		if (type == 1)
			img_path = "file:resource/square_dark.jpg";
		
		this.gc.drawImage(new Image(img_path), 0, 0, this.size, this.size);
		
//		System.out.println(this.gc);
//		System.out.println(String.format("size = %f", this.size));
//		System.out.println(img_path);
	}
}
