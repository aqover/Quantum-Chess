package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Pawn extends Pieces implements StraightMove, Cloneable {
	private int straightMove = MAX_STRAIGHT_MOVE;
	public Pawn(int i, int j) {
		super(i, j);
		straightMove = 2;
	}
	
	public void disableFirstMove() {
		straightMove = 1;
	}

	@Override
	public ArrayList<Tuple<Integer, Integer> > getMoveValid(String[] board) {
//		System.out.println(String.format("Pawn can move in, %s", team));
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(board, team, straightMove, i, j));
		return data;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.bp: GameHolder.wp, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		Pawn copy = null;
	    try {
	      copy = (Pawn) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}
}
