package view.pieces;

import java.util.ArrayList;

import control.Team;
import javafx.scene.canvas.GraphicsContext;
import shareObject.RenderableHolder;
import view.Tuple;

public class Rook extends Pieces implements StraightMove {
	private int straightMove = MAX_STRAIGHT_MOVE;
	
	public Rook(int i, int j) {
		super(i, j);
		straightMove = MAX_STRAIGHT_MOVE;
	}
		
	@Override
	public ArrayList<Tuple<Integer, Integer> > drawCanMove() {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(team, straightMove, i, j));
		if (StraightMove.DIRECTION_RIGHT)
			data.addAll(this.drawHoverRight(team, straightMove, i, j));
		if (StraightMove.DIRECTION_BOTTOM)
			data.addAll(this.drawHoverBottom(team, straightMove, i, j));
		if (StraightMove.DIRECTION_LEFT)
			data.addAll(this.drawHoverLeft(team, straightMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.A)? RenderableHolder.br: RenderableHolder.wr, 
				x, 
				y, 
				RenderableHolder.size, 
				RenderableHolder.size);
	}
}
