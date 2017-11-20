package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helpper.Team;
import helpper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Queen extends Pieces implements CornerMove, StraightMove, Cloneable  {
	private int straightMove = MAX_STRAIGHT_MOVE;
	private int cornerMove = MAX_CORNER_MOVE;
	
	public Queen(int i, int j) {
		super(i, j);
		straightMove = MAX_STRAIGHT_MOVE;
		cornerMove = MAX_CORNER_MOVE;
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
		gc.drawImage((team == Team.A)? GameHolder.bq: GameHolder.wq, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		Queen copy = null;
	    try {
	      copy = (Queen) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}
}