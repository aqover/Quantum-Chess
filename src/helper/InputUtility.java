package helper;

import scene.gameBoard.shareObject.GameHolder;

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
		int i = (int) (mouseY / GameHolder.size);
		int j = (int) (mouseX / GameHolder.size);
		return new Tuple<Integer, Integer>(i, j);
	}
	
	public static Tuple<Integer, Integer> getMousePosition(double x, double y) {
		int i = (int) (y / GameHolder.size);
		int j = (int) (x / GameHolder.size);
		return new Tuple<Integer, Integer>(i, j);
	}
}
