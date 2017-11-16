package Input;

import view.Team;
import view.Tuple;

public class InputUtility {
	public static double mouseX, mouseY;
	
	public static void mouseLeftDown() {
		getMousePosition();
	}
	
	private static Tuple<Integer, Integer> getMousePosition() {
		int i = (int) (mouseY / shareObject.RenderableHolder.size);
		int j = (int) (mouseX / shareObject.RenderableHolder.size);
//		System.out.println(String.format("x = %d, y = %d", i, j));
		return new Tuple<Integer, Integer>(i, j, Team.None);
	}
}
