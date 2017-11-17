package view;

import Input.InputUtility;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import shareObject.IRenderable;
import shareObject.RenderableHolder;

public class Board extends Canvas {
	private Team turnTeam;

	public Board () {
		this.setHeight(8 * shareObject.RenderableHolder.size);
		this.setWidth(8 * shareObject.RenderableHolder.size);
		
		drawBackground();
		addListenEvents();
		
		turnTeam = Team.A;
	}
	
	public Team getTurnTeam() {
		return turnTeam;
	}

	public void setTurnTeam(Team turnTeam) {
		this.turnTeam = turnTeam;
	}
	
	private void drawBackground() {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				this.getGraphicsContext2D().drawImage(
						((i+j)%2 == 0)? shareObject.RenderableHolder.bgDark: shareObject.RenderableHolder.bgLight, 
						i * shareObject.RenderableHolder.size, 
						j * shareObject.RenderableHolder.size,
						shareObject.RenderableHolder.size,
						shareObject.RenderableHolder.size);
			}
	}
	
	private void addListenEvents() {
		this.setOnMousePressed((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
			{
				InputUtility.mouseX = event.getX();
				InputUtility.mouseY = event.getY();
				InputUtility.mouseLeftDown();
			}
		});
	}
	
	public void paintComponent() {
		GraphicsContext gc = this.getGraphicsContext2D();

		IRenderable[][] entity = RenderableHolder.getInstance().getStateGame();
		for (int i=0;i<8;i++) 
			for(int j=0;j<8;j++) {
				if (entity[i][j] == null)
					continue;
				
				if (entity[i][j].isVisible() && !entity[i][j].isDestroyed()) {
					entity[i][j].draw(gc);
				}
			}
	}
}
