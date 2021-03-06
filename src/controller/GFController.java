package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GFController extends Pane {
	
	@FXML protected TextField playerA;
	@FXML protected TextField playerB;
	
	protected ChessController chessControl;
	
	public GFController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/GameOffline.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
	}
	
	public void clear() {
		playerA.setText("");
		playerB.setText("");
	}
	
	@FXML
	public void handlerBack(MouseEvent arg0) {
		SceneManager.setSceneMainMenu();
	}
	
	@FXML 
	public void startGame(MouseEvent arg0) {
//        System.out.println(playerA.getText());
        
        if (playerA.getText().equals(""))
       	{
        	showAlert(AlertType.WARNING, "Please, insert player a name.");
        	return;
       	}
        
        if (playerB.getText().equals(""))
       	{
        	showAlert(AlertType.WARNING, "Please, insert player b name.");
        	return;
       	}
        
        createGameController();
    }

	public void showAlert(AlertType type, String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.show();
	}
	
	protected void createGameController() {
		chessControl = new ChessController();
        chessControl.getDetail().setName(playerA.getText(), playerB.getText());
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
}
