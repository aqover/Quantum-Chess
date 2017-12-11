package scene.gameBoard;

import java.io.IOException;
import java.util.Arrays;

import controller.BoardGameOnlineController;
import controller.SceneManager;
import helper.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ChessOnlineDetail extends AnchorPane {
	private long nanoSecond = 1000000000l;
	
	@FXML private Label labelNameA;
	@FXML private Label labelNameB;
	@FXML private Label labelTimeA;
	@FXML private Label labelTimeB;
	@FXML private RadioButton radioA;
	@FXML private RadioButton radioB;

	protected long timePlayerA;
	protected long timePlayerB;
	protected BoardGameOnlineController gameControl;
	
	public long getTimePlayerW() { return timePlayerA; }
	public long getTimePlayerB() { return timePlayerB; }
	public void setTimePlayerW(long timePlayerW) { this.timePlayerA = timePlayerW; }
	public void setTimePlayerB(long timePlayerB) { this.timePlayerB = timePlayerB; }

	public String getNameWhite() { return labelNameA.getText(); }
	public String getNameBlack() { return labelNameB.getText(); }
	
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
	
	@FXML
	public void handleGiveUp(MouseEvent evt) {
		SceneManager.showMessage("Are you sure you want to give up?",
			Arrays.asList(ButtonType.OK, ButtonType.CANCEL), 
			new SceneManager.onFinish() {
			
				@Override
				public void run(ButtonType btn) {
					if (btn == ButtonType.OK) {
						gameControl.endGame();						
					}
				}
			});
	}

	@FXML
	public void handleFlip(MouseEvent evt) {
		gameControl.flipBoard();
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

}
