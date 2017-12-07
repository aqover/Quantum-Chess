package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class GQFController extends GFController {

	public GQFController() {
		super();
	}
	
	@Override 
	@FXML 
	public void startGame(MouseEvent arg0) {
        
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
        
        QuantumChessController chessControl = new QuantumChessController();
        chessControl.getQuantumDetail().setName(playerA.getText(), playerB.getText());
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
    }

}
