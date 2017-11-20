package scene.gameBoard.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import scene.gameBoard.shareObject.IRenderable;

public class Entity implements IRenderable {
	
	protected double x, y;
	protected int z;
	protected boolean visible, destroyed;
	protected boolean isSelected;
	
	protected Entity() {
		visible = true;
		destroyed = false;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return destroyed;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawHover(GraphicsContext gc) {
		gc.setLineWidth(scene.gameBoard.shareObject.GameHolder.LineWidth);
		gc.setStroke(Color.WHITE);
		gc.strokeRect(x,  y, scene.gameBoard.shareObject.GameHolder.size, scene.gameBoard.shareObject.GameHolder.size );
	}

	public void Destroy() {
		destroyed = true;
	}
}
