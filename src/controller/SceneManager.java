package controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public final class SceneManager {
	
	private static Stage primaryStage;
	private static Scene mainMenu;
	private static Scene selectGame;
	private static Scene gameOffline;
	private static Scene gameOnline;

	private static boolean disable;
	
	static {
		mainMenu = new Scene(new MainMenuController());
		selectGame = new Scene(new SelectGameController());
		gameOffline = new Scene(new GFController());
		gameOnline = new Scene(new GOController());
		
		disable = false;
	}
	
	public static void initialize(Stage stage) {
		primaryStage = stage;
		primaryStage.setTitle("Quantum Chess");
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		
		setSceneMainMenu();
		primaryStage.show();
	}
	
	public static void setSceneMainMenu() {
		primaryStage.setScene(mainMenu);
	}
	
	public static void setSceneSelectGame() {
		primaryStage.setScene(selectGame);
	}
	
	public static void setSceneGameOnilne() {
		primaryStage.setScene(gameOnline);
	}
	
	public static void setSceneGameOffline() {
		primaryStage.setScene(gameOffline);
	}
	
	public static void setScene(Pane pane)
	{
		primaryStage.setScene(new Scene(pane));
		System.gc();
	}
	
	
	public static synchronized void showMessage(String msg, Runnable onDone) {
		
		if (disable) return;
		disable = true;
		
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.setOnHidden(e -> { onDone.run(); disable = false; });
		alert.show();
	}
}
