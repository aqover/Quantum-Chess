package view.pieces;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import shareObject.RenderableHolder;
import view.Team;
import view.Tuple;

public class Bishop extends Pieces implements CornerMove{
	private int cornerMove = MAX_CORNER_MOVE;
	
	public Bishop(int i, int j) {
		super(i, j);
		cornerMove = MAX_CORNER_MOVE;
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > drawCanMove(int i, int j) {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (CornerMove.DIRECTION_TOP_LEFT)
			data.addAll(this.drawHoverTopLeft(team, cornerMove, i, j));
		if (CornerMove.DIRECTION_TOP_RIGHT)
			data.addAll(this.drawHoverTopRight(team, cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_RIGHT)
			data.addAll(this.drawHoverBottomRight(team, cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_LEFT)
			data.addAll(this.drawHoverBottomLeft(team, cornerMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.B)? RenderableHolder.bb: RenderableHolder.wb, 
				x, 
				y, 
				RenderableHolder.size, 
				RenderableHolder.size);
	}
}
