package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			primaryStage.setOnCloseRequest(e->{
				Platform.exit();
				System.exit(0);
			});
			controller.SceneManager.initialize(primaryStage);

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
