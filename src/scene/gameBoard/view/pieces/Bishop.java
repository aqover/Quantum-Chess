package scene.gameBoard.view.pieces;

import helper.Team;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Bishop extends Pieces{
	
	public Bishop(int i, int j) {
		this(i, j, Team.NONE);
	}
	
	public Bishop(int i, int j, Team t) {
		super(i, j, t);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.bb: GameHolder.wb, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
}
