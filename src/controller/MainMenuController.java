package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainMenuController extends Pane {
	
	public MainMenuController() {		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/MainMenu.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
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
	
	
}
