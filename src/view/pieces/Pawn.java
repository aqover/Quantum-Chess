package view.pieces;

import java.util.ArrayList;

import control.Team;
import javafx.scene.canvas.GraphicsContext;
import shareObject.RenderableHolder;
import view.Tuple;

public class Pawn extends Pieces implements StraightMove{
	private int straightMove = MAX_STRAIGHT_MOVE;
	private boolean firstMove;
	public Pawn(int i, int j) {
		super(i, j);
		straightMove = 2;
		firstMove = true;
	}
	
	public void disFirstMove() {
		firstMove = false;
		straightMove = 1;
	}

	@Override
	public ArrayList<Tuple<Integer, Integer> > drawCanMove() {
//		System.out.println(String.format("Pawn can move in, %s", team));
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(team, straightMove, i, j));
		return data;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.A)? RenderableHolder.bp: RenderableHolder.wp, 
				x, 
				y, 
				RenderableHolder.size, 
				RenderableHolder.size);
	}
}
