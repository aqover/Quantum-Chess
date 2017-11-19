package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helpper.Team;
import helpper.Tuple;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Knight extends Pieces implements LMove, Cloneable {
	public Knight(int i, int j) {
		super(i, j);
	}
	
	@Override
	public ArrayList<Tuple<Integer, Integer> > getMoveValid(String[] board) {
		// TODO Auto-generated method stub
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (LMove.DIRECTION_TOP_LEFT)
			data.add(this.drawHoverTopLeft(board, team, i, j));
		if (LMove.DIRECTION_TOP_RIGHT)
			data.add(this.drawHoverTopRight(board, team, i, j));
		if (LMove.DIRECTION_RIGHT_TOP)
			data.add(this.drawHoverRightTop(board, team, i, j));
		if (LMove.DIRECTION_RIGHT_BOTTOM)
			data.add(this.drawHoverRightBottom(board, team, i, j));
		if (LMove.DIRECTION_BOTTOM_RIGHT)
			data.add(this.drawHoverBottomRight(board, team, i, j));
		if (LMove.DIRECTION_BOTTOM_LEFT)
			data.add(this.drawHoverBottomLeft(board, team, i, j));
		if (LMove.DIRECTION_LEFT_BOTTOM)
			data.add(this.drawHoverLeftBottom(board, team, i, j));
		if (LMove.DIRECTION_LEFT_TOP)
			data.add(this.drawHoverLeftTop(board, team, i, j));
		return data;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.A)? GameHolder.bn: GameHolder.wn, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
	
	public Object clone() {
		Knight copy = null;
	    try {
	      copy = (Knight) super.clone();
	    } catch (CloneNotSupportedException e) {
	      e.printStackTrace();
	    }
	    return copy;
	}

}