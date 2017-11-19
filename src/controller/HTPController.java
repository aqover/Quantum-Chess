package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class HTPController extends Pane {
	public HTPController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/HTP.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
	}
	
	@FXML
	public void handlerBack(MouseEvent arg0) {
		SceneManager.setSceneMainMenu();
	}
}
