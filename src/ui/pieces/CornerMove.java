package ui.pieces;

public interface CornerMove {
	int MAX_CORNER_MOVE = 8;
	boolean DIRECTION_TOP_LEFT = true;
	boolean DIRECTION_TOP_RIGHT = true;
	boolean DIRECTION_BOTTOM_LEFT = true;
	boolean DIRECTION_BOTTOM_RIGHT = true;
	
	public void drawCornerMove();
}
