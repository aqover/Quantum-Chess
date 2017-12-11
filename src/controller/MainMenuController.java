package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainMenuController extends Pane {
	
	@FXML private TextField playerA;
	@FXML private TextField playerB;
	
	@FXML private Button normalChess;
	@FXML private Button quantumChess;
	
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
	public void handlerSelectNormal(MouseEvent arg0) {;
		SceneManager.setSceneSelectGame(false);
	}
	
	@FXML 
	public void handlerSelectQuantum(MouseEvent arg0) {;
		SceneManager.setSceneSelectGame(true);
	}
	
	public void showAlert(AlertType type, String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.show();
	}
	
}
