package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Rook extends Pieces implements StraightMove, Cloneable {
	private int straightMove = MAX_STRAIGHT_MOVE;
	
	public Rook(int i, int j) {
		super(i, j);
		straightMove = MAX_STRAIGHT_MOVE;
	}
		
	@Override
	public ArrayList<Tuple<Integer, Integer> > getMoveValid(String[] board) {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (StraightMove.DIRECTION_TOP)
			data.addAll(this.drawHoverTop(board, team, straightMove, i, j));
		if (StraightMove.DIRECTION_RIGHT)
			data.addAll(this.drawHoverRight(board, team, straightMove, i, j));
		if (StraightMove.DIRECTION_BOTTOM)
			data.addAll(this.drawHoverBottom(board, team, straightMove, i, j));
		if (StraightMove.DIRECTION_LEFT)
			data.addAll(this.drawHoverLeft(board, team, straightMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.wr: GameHolder.br, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		Rook copy = null;
	    try {
	      copy = (Rook) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}
}
