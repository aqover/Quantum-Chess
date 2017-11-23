package scene.gameBoard.view.pieces;

import helper.Team;
import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class Rook extends Pieces{
	
	public Rook(int i, int j) {
		this(i, j, Team.NONE);
	}
	
	public Rook(int i, int j, Team t) {
		super(i, j, t);
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		gc.drawImage((team == Team.PLAYER_WHITE)? GameHolder.br: GameHolder.wr, 
				x, 
				y, 
				GameHolder.size, 
				GameHolder.size);
		if (this.isSelected) this.drawHover(gc);
	}
}
