package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import ui.GamePlay;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
	
		// list of objects
//		Label topicLabel = new Label("Topic:");
//		Label dateLabel = new Label("Date:");
//		
//		TextField topicTextField = new TextField("");
//		topicTextField.setPrefWidth(200);
//		
//		DatePicker datePicker = new DatePicker();
//		datePicker.setPrefWidth(150);
//		
//		TextArea textArea = new TextArea();
//		
//		Button okBtn = new Button("OK");
//		okBtn.setPrefWidth(60);
//		
//		Button clearBtn = new Button("Clear");
//		clearBtn.setPrefWidth(60);
//		
//		// create Pane
//		BorderPane root = new BorderPane();
//		
//		VBox top = new VBox(3);
//		HBox line1 = new HBox();
//		HBox line2 = new HBox();
//		line1.getChildren().addAll(topicLabel, topicTextField);
//		line2.getChildren().addAll(dateLabel, datePicker);
//		top.getChildren().addAll(line1, line2);
//		
//		HBox center = new HBox();
//		
//		center.setPadding(new Insets(8, 0, 8, 0));
//		center.getChildren().add(textArea);
//		
//		HBox bottom = new HBox(3);
//		bottom.setAlignment(Pos.BOTTOM_RIGHT);
//		bottom.getChildren().addAll(okBtn, clearBtn);
//		
//		root.setPadding(new Insets(10, 5, 10, 5));
//		root.setTop(top);
//		root.setCenter(center);
//		root.setBottom(bottom);
//	    
//		// create scene
//		Scene scene = new Scene(root, 250, 280);
//		
//		primaryStage.setTitle("MyNote");
//		primaryStage.setScene(scene);
//		
//		primaryStage.show();
		
		try {
			Pane root = new Pane();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			GamePlay gamePlay = new GamePlay();
			root.getChildren().add(gamePlay);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
