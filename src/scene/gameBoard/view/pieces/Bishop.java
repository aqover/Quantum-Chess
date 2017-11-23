package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Bishop extends Pieces implements CornerMove, Cloneable{
	private int cornerMove = MAX_CORNER_MOVE;
	
	public Bishop(int i, int j) {
		super(i, j);
		cornerMove = MAX_CORNER_MOVE;
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > getMoveValid(String[] board) {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (CornerMove.DIRECTION_TOP_LEFT)
			data.addAll(this.drawHoverTopLeft(board, team, cornerMove, i, j));
		if (CornerMove.DIRECTION_TOP_RIGHT)
			data.addAll(this.drawHoverTopRight(board,team, cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_RIGHT)
			data.addAll(this.drawHoverBottomRight(board,team, cornerMove, i, j));
		if (CornerMove.DIRECTION_BOTTOM_LEFT)
			data.addAll(this.drawHoverBottomLeft(board,team, cornerMove, i, j));
		return data;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.wb: GameHolder.bb, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		Bishop copy = null;
	    try {
	      copy = (Bishop) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}
}
