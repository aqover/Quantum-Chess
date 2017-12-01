package controller;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
		gameOffline = new Scene(new GameOfflineController());
		gameOnline = new Scene(new GameOnlineController());
		
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
	
	public interface onFinish extends Runnable {
		
		default public void run() {}
		default public void run(ButtonType btn) {}
	}
	
	public static synchronized void showMessage(String msg, onFinish onDone) {
		
		if (disable) return;
		disable = true;
		
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.setOnHidden(e -> { onDone.run(); disable = false; });
		alert.show();
	}
	
	public static synchronized void showMessage(String msg, Collection<? extends ButtonType> btns, onFinish onDone) {
		
		if (disable) return;
		disable = true;
	
		Alert alert = new Alert(AlertType.NONE, "");
		alert.setHeaderText(msg);
		alert.getButtonTypes().addAll(btns);
		alert.setOnHidden((e) -> {
			onDone.run(alert.getResult());
			disable = false;
		});
		
		alert.show();
	}
}
