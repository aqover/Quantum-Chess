package ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameDetail extends VBox {
	public GameDetail() {
		super();
		
		Label p1 = new Label();
		p1.setAlignment(Pos.CENTER);
		p1.setPrefWidth(200.0);
		p1.setText("Player 1");
		
		this.getChildren().add(p1);
	}
}
