package view.pieces;

import java.util.ArrayList;

import control.Team;
import javafx.scene.canvas.GraphicsContext;
import shareObject.RenderableHolder;
import view.Tuple;

public class King extends Pieces implements CornerMove, StraightMove {
	private int straightMove = MAX_STRAIGHT_MOVE;
	private int cornerMove = MAX_CORNER_MOVE;
	
	public King(int i, int j) {
		super(i, j);
		straightMove = 1;
		cornerMove = 1;
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > drawCanMove() {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_RIGHT)
			data.addAll(this.drawHoverRight(this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_BOTTOM)
			data.addAll(this.drawHoverBottom(this.team, this.straightMove, i, j));
		if (StraightMove.DIRECTION_LEFT)
			data.addAll(this.drawHoverLeft(this.team, this.straightMove, i, j));
		if (CornerMove.DIRECTION_TOP_LEFT)
			data.addAll(this.drawHoverTopLeft(this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_TOP_RIGHT)
			data.addAll(this.drawHoverTopRight(this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_RIGHT)
			data.addAll(this.drawHoverBottomRight(this.team, this.cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_LEFT)
			data.addAll(this.drawHoverBottomLeft(this.team, this.cornerMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.A)? RenderableHolder.bk: RenderableHolder.wk, 
				x, 
				y, 
				RenderableHolder.size, 
				RenderableHolder.size);
	}
}
