package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
<<<<<<< HEAD
<<<<<<< HEAD
			Pane root = new Pane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
=======
//			Pane root = new Pane();
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
>>>>>>> parent of ed88e67... Revert "- add main menu"
			
//			GamePlay gamePlay = new GamePlay();
//			root.getChildren().add(gamePlay);
			
			controller.SceneManager.initialize(primaryStage);
			
=======
			controller.SceneManager.initialize(primaryStage);
>>>>>>> parent of ea98998... Revert "- add select game page for choose game type which online or offline"
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void startGame() {
		
	}
}
