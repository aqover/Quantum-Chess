package controller;

import java.util.Collection;

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

	private static Scene quantumGameOffline;
	private static Scene quantumGameOnline;
	
	private static boolean disable;
	
	private static boolean isQuantumChess;
	
	static {
		mainMenu = new Scene(new MainMenuController());
		selectGame = new Scene(new SelectGameController());
		gameOffline = new Scene(new GFController());
		gameOnline = new Scene(new GOController());
		
		quantumGameOffline = new Scene(new GQFController());
		quantumGameOnline = new Scene(new GQOController());
		
		disable = false;
		isQuantumChess = false;
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
	
	public static void setSceneSelectGame(boolean isQuantumChess) {
		SceneManager.isQuantumChess = isQuantumChess;
		primaryStage.setScene(selectGame);
	}
	
	public static void setSceneGameOnline() {
		if (isQuantumChess)
		{
			((GOController) quantumGameOnline.getRoot()).clear();
			primaryStage.setScene(quantumGameOnline);
		}
		else
		{
			((GOController) gameOnline.getRoot()).clear();
			primaryStage.setScene(gameOnline);
		}
	}
	
	public static void setSceneGameOffline() {
		if (isQuantumChess)
		{
			((GFController) quantumGameOffline.getRoot()).clear();
			primaryStage.setScene(quantumGameOffline);
		}
		else
		{
			((GFController) gameOffline.getRoot()).clear();
			primaryStage.setScene(gameOffline);
		}
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
	
	public static synchronized boolean showMessage(String msg, onFinish onDone) {
		
		if (disable) { onDone.run(); return false; }
		disable = true;
		
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.setOnHidden(e -> { 
			disable = false; 
			onDone.run(); 
		});
		alert.show();
		
		return true;
	}
	
	public static synchronized boolean showMessage(String msg, Collection<? extends ButtonType> btns, onFinish onDone) {
		
		if (disable) { onDone.run(null); return false; }
		disable = true;
	
		Alert alert = new Alert(AlertType.NONE, "");
		alert.setHeaderText(msg);
		alert.getButtonTypes().addAll(btns);
		alert.setOnHidden((e) -> {
			disable = false;
			onDone.run(alert.getResult());
		});
		
		alert.show();
		
		return true;
	}
}
