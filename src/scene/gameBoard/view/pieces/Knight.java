package scene.gameBoard.view.pieces;

import helper.Team;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Knight extends Pieces{
	
	public Knight(int i, int j) {
		this(i, j, Team.NONE);
	}
	
	public Knight(int i, int j, Team t) {
		super(i, j, t);
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.bn: GameHolder.wn, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}

}