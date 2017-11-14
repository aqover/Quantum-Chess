package ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Chat extends VBox {
	public Chat() {
		super();
		Label label = new Label();
		label.setText("Chat");
		label.setPrefWidth(200);
		label.setAlignment(Pos.CENTER);
		label.setPadding(new Insets(20, 0, 20, 0));
		
		getChildren().add(label);
	}
}
