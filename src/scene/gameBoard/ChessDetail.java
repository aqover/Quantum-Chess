package scene.gameBoard;

import java.io.IOException;

import controller.ChessController;
import helpper.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

public class ChessDetail extends AnchorPane {
	private long nanoSecond = 1000000000l;
	private long timePlayerA;
	private long timePlayerB;
	
	private ChessController gameControl;
	
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
	
	public ChessDetail(ChessController gameControl) {
		super();
		
		this.gameControl = gameControl;		
		timePlayerA = timePlayerB = 60*60*nanoSecond; //unit in nanosecond, 60 mins;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChessDetail.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
	}
	
	public void update() {
		if (gameControl.getTurnTeam() == Team.A)
		{
			radioA.setSelected(true);
			radioB.setSelected(false);
		}
		else if (gameControl.getTurnTeam() == Team.B)
		{
			radioA.setSelected(false);
			radioB.setSelected(true);
		}
		
		labelTimeA.setText(String.format("%d:%d", (timePlayerA/nanoSecond)/60, (timePlayerA/nanoSecond)%60));
		labelTimeB.setText(String.format("%d:%d", (timePlayerB/nanoSecond)/60, (timePlayerB/nanoSecond)%60));
	}
	
	public void decreseTime(long decreseTime)
	{
		if (gameControl.getTurnTeam() == Team.A)
			timePlayerA = timePlayerA - decreseTime;
		else if (gameControl.getTurnTeam() == Team.B)
			timePlayerB = timePlayerB - decreseTime;
	}
}