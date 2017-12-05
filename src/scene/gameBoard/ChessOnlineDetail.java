package scene.gameBoard;

import java.io.IOException;

import controller.BoardGameOnlineController;
import helper.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ChessOnlineDetail extends AnchorPane {
	private long nanoSecond = 1000000000l;
	private long timePlayerA;
	private long timePlayerB;
	
	private BoardGameOnlineController gameControl;
	
	@FXML Label labelNameA;
	@FXML Label labelTimeA;
	@FXML Label labelNameB;
	@FXML Label labelTimeB;
	@FXML RadioButton radioA;
	@FXML RadioButton radioB;

	public void setName(String a, String b) {
		labelNameA.setText(a);
		labelNameB.setText(b);
	}
	
	public ChessOnlineDetail(BoardGameOnlineController gameControl) {
		super();
		
		this.gameControl = gameControl;		
		timePlayerA = timePlayerB = 60*60*nanoSecond; //unit in nanosecond, 60 mins;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChessOnlineDetail.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
	}
	
	public void update() {
		if (gameControl.getTurn() == Team.PLAYER_WHITE)
		{
			radioA.setSelected(true);
			radioB.setSelected(false);
		}
		else if (gameControl.getTurn() == Team.PLAYER_BLACK)
		{
			radioA.setSelected(false);
			radioB.setSelected(true);
		}
		
		labelTimeA.setText(String.format("%d:%d", (timePlayerA/nanoSecond)/60, (timePlayerA/nanoSecond)%60));
		labelTimeB.setText(String.format("%d:%d", (timePlayerB/nanoSecond)/60, (timePlayerB/nanoSecond)%60));
	}
	
	public void decreseTime(long decreseTime)
	{
		if (gameControl.getTurn() == Team.PLAYER_WHITE)
			timePlayerA = timePlayerA - decreseTime;
		else if (gameControl.getTurn() == Team.PLAYER_BLACK)
			timePlayerB = timePlayerB - decreseTime;
	}
	
	public long getTimePlayerW() {
		return timePlayerA;
	}

	public long getTimePlayerB() {
		return timePlayerB;
	}

	public void setTimePlayerW(long timePlayerW) {
		this.timePlayerA = timePlayerW;
	}

	public void setTimePlayerB(long timePlayerB) {
		this.timePlayerB = timePlayerB;
	}
}
