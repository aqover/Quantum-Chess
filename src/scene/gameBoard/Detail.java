package scene.gameBoard;

import controller.ChessController;
import helper.Team;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public abstract class Detail extends AnchorPane {

	protected long timePlayerA;
	protected long timePlayerB;
	protected ChessController gameControl;

	public abstract void update();
	public long getTimePlayerW() { return timePlayerA; }
	public long getTimePlayerB() { return timePlayerB; }
	public void setTimePlayerW(long timePlayerW) { this.timePlayerA = timePlayerW; }
	public void setTimePlayerB(long timePlayerB) { this.timePlayerB = timePlayerB; }

	@FXML Label labelNameA;
	@FXML Label labelNameB;

	public void setName(String a, String b) {
		labelNameA.setText(a);
		labelNameB.setText(b);
	}

	public void decreseTime(long decreseTime) {
		if (gameControl.getTurn() == Team.PLAYER_WHITE)
			timePlayerA = timePlayerA - decreseTime;
		else if (gameControl.getTurn() == Team.PLAYER_BLACK)
			timePlayerB = timePlayerB - decreseTime;
	}

	public Detail(ChessController gameControl) { 
		super();
		this.gameControl = gameControl;
	}
}
