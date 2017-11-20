package view.pieces;

import java.util.ArrayList;

import control.Team;
import javafx.scene.canvas.GraphicsContext;
import shareObject.RenderableHolder;
import view.Tuple;

public class Knight extends Pieces implements LMove {
	public Knight(int i, int j) {
		super(i, j);
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > drawCanMove() {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (LMove.DIRECTION_TOP_LEFT)
			data.add(this.drawHoverTopLeft(team, i, j));
		if (LMove.DIRECTION_TOP_RIGHT)
			data.add(this.drawHoverTopRight(team, i, j));
		if (LMove.DIRECTION_RIGHT_TOP)
			data.add(this.drawHoverRightTop(team, i, j));
		if (LMove.DIRECTION_RIGHT_BOTTOM)
			data.add(this.drawHoverRightBottom(team, i, j));
		if (LMove.DIRECTION_BOTTOM_RIGHT)
			data.add(this.drawHoverBottomRight(team, i, j));
		if (LMove.DIRECTION_BOTTOM_LEFT)
			data.add(this.drawHoverBottomLeft(team, i, j));
		if (LMove.DIRECTION_LEFT_BOTTOM)
			data.add(this.drawHoverLeftBottom(team, i, j));
		if (LMove.DIRECTION_LEFT_TOP)
			data.add(this.drawHoverLeftTop(team, i, j));
		return data;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.A)? RenderableHolder.bn: RenderableHolder.wn, 
				x, 
				y, 
				RenderableHolder.size, 
				RenderableHolder.size);
	}

}