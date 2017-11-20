package control;

import Input.InputUtility;
import view.Tuple;
import view.pieces.Pieces;

public class GameControl {
	
	private Pieces selectedPiece;
	
	public GameControl() {
		shareObject.RenderableHolder.turnTeam = Team.A;
		shareObject.RenderableHolder.isHover = false;
	} 
	
	public void update() {
		if (InputUtility.isMouseLeftClicked())
		{
			if (shareObject.RenderableHolder.isHover)
			{
				selectedPiece = null;
				shareObject.RenderableHolder.isHover = false;
			}
			else
			{
				Tuple<Integer, Integer> tmp = InputUtility.getMousePosition();
				Pieces[][] stateGame = shareObject.RenderableHolder.getInstance().getStateGame();
				if (stateGame[tmp.getI()][tmp.getJ()].getTeam() == shareObject.RenderableHolder.turnTeam)
				{
					selectedPiece = stateGame[tmp.getI()][tmp.getJ()];
					shareObject.RenderableHolder.getInstance().setWalkWay(selectedPiece.drawCanMove());
//					System.out.println(shareObject.RenderableHolder.getInstance().getWalkWay().size());
					if (shareObject.RenderableHolder.getInstance().getWalkWay().size() > 0)
						shareObject.RenderableHolder.isHover = true;
				}
			}
		}
	}
}
