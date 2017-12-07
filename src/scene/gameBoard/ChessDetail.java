package scene.gameBoard;

import java.io.IOException;
import java.util.Arrays;

import controller.ChessController;
import controller.SceneManager;
import helper.Team;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ChessDetail extends AnchorPane {
	private long nanoSecond = 1000000000l;
	
	protected long timePlayerA;
	protected long timePlayerB;
	protected ChessController gameControl;
	
	public long getTimePlayerW() { return timePlayerA; }
	public long getTimePlayerB() { return timePlayerB; }
	public void setTimePlayerW(long timePlayerW) { this.timePlayerA = timePlayerW; }
	public void setTimePlayerB(long timePlayerB) { this.timePlayerB = timePlayerB; }

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

	@FXML Label labelNameA;
	@FXML Label labelTimeA;
	@FXML Label labelNameB;
	@FXML Label labelTimeB;
	@FXML RadioButton radioA;
	@FXML RadioButton radioB;
	
	@FXML
	protected void handleFlipBoard(MouseEvent event) {
		gameControl.flipBoard();
    }

	@FXML
	protected void handleUndo(MouseEvent event) {
		gameControl.undo();
    }

	@FXML
	protected void handleRedo(MouseEvent event) {
		gameControl.redo();
    }

	@FXML
	protected void handleQuit(MouseEvent event) {
		SceneManager.showMessage("Are you sure?",
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
