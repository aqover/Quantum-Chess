package helper;

import javafx.scene.paint.Color;

public class Ultility {
	public static Color rgbFade(Color start, Color stop, double prob) {
		double r,g,b;
		r = start.getRed() + (stop.getRed() - start.getRed())*prob;
		b = start.getBlue() + (stop.getBlue() - start.getBlue())*prob;
		g = start.getGreen() + (stop.getGreen() - start.getGreen())*prob;
		return Color.rgb((int)(255*r), (int)(255*g), (int)(255*b));
	}
}
