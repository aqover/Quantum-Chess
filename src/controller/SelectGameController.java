package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class SelectGameController extends Pane {
	
	public SelectGameController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/SelectGameType.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }
	}
	
	@FXML
	public void handlerOnline(MouseEvent arg0) {
		SceneManager.setSceneGameOnilne();
	}
	
	@FXML
	public void handlerOffline(MouseEvent arg0) {
		SceneManager.setSceneGameOffline();
	}
	
	@FXML
	public void handlerBack(MouseEvent arg0) {
		SceneManager.setSceneMainMenu();
	}
}
