package scene.gameBoard;

import java.io.IOException;
import java.util.Arrays;

import controller.QuantumChessController;
import controller.SceneManager;
import helper.Team;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class QuantumChessDetail extends AnchorPane {
	
	private long nanoSecond = 1000000000l;
	
	protected long timePlayerA;
	protected long timePlayerB;
	protected QuantumChessController gameControl;
	
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

	public void increseTime(long decreseTime) {
		if (gameControl.getTurn() == Team.PLAYER_WHITE)
			timePlayerA = timePlayerA + decreseTime;
		else if (gameControl.getTurn() == Team.PLAYER_BLACK)
			timePlayerB = timePlayerB + decreseTime;
	}
	
	@FXML private Label labelNameA;
	@FXML private Label labelTimeA;
	@FXML private Label labelNameB;
	@FXML private Label labelTimeB;
	@FXML private RadioButton radioA;
	@FXML private RadioButton radioB;
	
	@FXML private Slider moveProb;
	@FXML private Label labelProb;
	
	@FXML
	protected void handleFlipBoard(MouseEvent event) {
		gameControl.flipBoard();
    }

	@FXML
	protected void handlePass(MouseEvent event) {
		gameControl.pass();
	}
	
	protected void handleMoveProbChange(Number oldValue, Number newValue) {
		gameControl.setMoveProb((double) newValue.intValue() / 10.0);
		labelProb.setText(Integer.toString(newValue.intValue() * 10));
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
	
	public QuantumChessDetail(QuantumChessController gameControl) {
		super();
		this.gameControl = gameControl;

		timePlayerA = timePlayerB = 0; //unit in nanosecond, 60 mins;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("QuantumChessDetail.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
			
			moveProb.valueProperty().addListener(new ChangeListener<Number>() {
				
				@Override
				public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
					handleMoveProbChange(oldValue, newValue);
				}
			});

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
