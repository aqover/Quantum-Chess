package Input;

import control.Team;
import view.Tuple;

public class InputUtility {
	public static double mouseX, mouseY;
	
	private static boolean isMouseLeftClicked;
	private static boolean isMouseRightClicked;
		
	public static void mouseLeftDown() {
		isMouseLeftClicked = true;
	}
	
	public static void mouseRightDown() {
		isMouseRightClicked = true;
	}
	
	public static void mouseLeftRelease(){
//		isMouseLeftClicked = false;
	}
	
	public static void mouseRightRelease(){
		isMouseRightClicked = false;
	}

	public static boolean isMouseLeftClicked() {
		return isMouseLeftClicked;
	}

	public static boolean isMouseRightClicked() {
		return isMouseRightClicked;
	}
	
	public static void update() {
		isMouseLeftClicked = false;
		isMouseRightClicked = false;
	}
	
	public static Tuple<Integer, Integer> getMousePosition() {
		int i = (int) (mouseY / shareObject.RenderableHolder.size);
		int j = (int) (mouseX / shareObject.RenderableHolder.size);
//		System.out.println(String.format("x = %d, y = %d", i, j));
		return new Tuple<Integer, Integer>(i, j, Team.None);
	}
}
