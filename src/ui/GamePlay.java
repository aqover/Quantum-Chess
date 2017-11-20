package ui;

import Input.InputUtility;
import control.GameControl;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import view.Board;
import view.Chat;
import view.GameDetail;

public class GamePlay extends HBox {
	public GamePlay() {
		GameControl gameControl = new GameControl();
		
		GameDetail detail = new GameDetail();
		Board board = new Board();
		Chat chat = new Chat();
		
		getChildren().add(detail);
		getChildren().add(board);
		getChildren().add(chat);
		setHgrow(board, Priority.ALWAYS);
		
		AnimationTimer animation = new AnimationTimer() {
			public void handle(long now) {
				board.paintComponent();
				gameControl.update();
				InputUtility.update();
			}
		};
		animation.start();
	}
}
