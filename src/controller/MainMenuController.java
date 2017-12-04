package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainMenuController extends Pane {
	
	@FXML TextField playerA;
	@FXML TextField playerB;
	
	public MainMenuController() {		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/MainMenu.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
           ex.printStackTrace();
        }
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
        
        ChessController chessControl = new ChessController();
        chessControl.getDetail().setName(playerA.getText(), playerB.getText());
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
    }
	
	@FXML 
	public void handlerSelectGame(MouseEvent arg0) {
		SceneManager.setSceneSelectGame();
	}
	
	@FXML 
	public void handlerHTP(MouseEvent arg0) {
		SceneManager.setScene(new HTPController());
	}
	
	@FXML 
	public void handlerAbout(MouseEvent arg0) {
		SceneManager.setScene(new AboutController());
	}
	
	public void showAlert(AlertType type, String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.show();
	}
	
}
