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

public class ChessOnlineDetail extends Detail {
	private long nanoSecond = 1000000000l;
	
	@FXML Label labelTimeA;
	@FXML Label labelTimeB;
	@FXML RadioButton radioA;
	@FXML RadioButton radioB;

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
	
	public ChessOnlineDetail(BoardGameOnlineController gameControl) {
		super(gameControl);
		
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
