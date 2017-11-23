package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class King extends Pieces implements CornerMove, StraightMove, Cloneable {
	private int straightMove = MAX_STRAIGHT_MOVE;
	private int cornerMove = MAX_CORNER_MOVE;
	
	public King(int i, int j) {
		super(i, j);
		straightMove = 1;
		cornerMove = 1;
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > getMoveValid(String[] board) {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(board, this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_RIGHT)
			data.addAll(this.drawHoverRight(board, this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_BOTTOM)
			data.addAll(this.drawHoverBottom(board, this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_LEFT)
			data.addAll(this.drawHoverLeft(board, this.team, this.straightMove, i, j));
		if (CornerMove.DIRECTION_TOP_LEFT)
			data.addAll(this.drawHoverTopLeft(board, this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_TOP_RIGHT)
			data.addAll(this.drawHoverTopRight(board, this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_RIGHT)
			data.addAll(this.drawHoverBottomRight(board, this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_LEFT)
			data.addAll(this.drawHoverBottomLeft(board, this.team, this.cornerMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.bk: GameHolder.wk, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		King copy = null;
	    try {
	      copy = (King) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}
}
