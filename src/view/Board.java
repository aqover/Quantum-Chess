package view;

import Input.InputUtility;
import control.Team;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import shareObject.IRenderable;
import shareObject.RenderableHolder;

public class Board extends Canvas {
	public Board () {
		this.setHeight(8 * shareObject.RenderableHolder.size);
		this.setWidth(8 * shareObject.RenderableHolder.size);
		
		drawBackground();
		addListenEvents();
	}
	
	public void drawHover(int i, int j, Color color) {
//		System.out.println(string.format("i = %d, j = %d", i, j));
		GraphicsContext gc = this.getGraphicsContext2D();
		gc.setLineWidth(shareObject.RenderableHolder.LineWidth);
		gc.setStroke(color);
		gc.strokeRect(
			j * shareObject.RenderableHolder.size, 
			i * shareObject.RenderableHolder.size,
			shareObject.RenderableHolder.size,
			shareObject.RenderableHolder.size
		);
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
					shareObject.RenderableHolder.size
				);
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
			
			if (event.getButton() == MouseButton.SECONDARY)
			{
				InputUtility.mouseRightDown();
			}
		});
		
		this.setOnMouseReleased((MouseEvent event) -> {
			if (event.getButton() == MouseButton.PRIMARY)
			{
				InputUtility.mouseLeftRelease();
			}
			
			if (event.getButton() == MouseButton.SECONDARY)
			{
				InputUtility.mouseRightRelease();
			}
		});
	}
	
	public void paintComponent() {
		drawBackground();
		
		GraphicsContext gc = this.getGraphicsContext2D();
		IRenderable[][] entity = RenderableHolder.getInstance().getStateGame();
		for (int i=0;i<8;i++)
		{
			for(int j=0;j<8;j++) {
				if (entity[i][j] == null)
					continue;
				
				if (entity[i][j].isVisible() && !entity[i][j].isDestroyed()) {
					entity[i][j].draw(gc);
				}
			}
		}
			
		if (shareObject.RenderableHolder.isHover)
		{
			for(Tuple<Integer, Integer> walkway: shareObject.RenderableHolder.getInstance().getWalkWay())
			{
				drawHover(walkway.getI(), walkway.getJ(),
					(walkway.getTeam() != Team.None)? Color.RED: Color.WHITE);
			}
		}
	}
}
