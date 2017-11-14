package ui.pieces;

public interface StraightMove {
	int MAX_STRAIGHT_MOVE = 8;
	boolean DIRECTION_TOP = true;
	boolean DIRECTION_RIGHT = true;
	boolean DIRECTION_BOTTOM = true;
	boolean DIRECTION_LEFT = true;
	
	public void drawStraightMove();
}
