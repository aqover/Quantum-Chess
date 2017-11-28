package scene.gameBoard.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import scene.gameBoard.shareObject.GameHolder;
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

	public double getX() {
		return this.x;
	}
	public double getY() {
		return this.y;
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

	public void setZ(int z) {
		this.z = z;
	}
	
	public void setPositionOnScreen(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		
	}
	
	public void drawHover(GraphicsContext gc) {
		gc.setLineWidth(GameHolder.LineWidth);
		gc.setStroke(Color.WHITE);
		gc.strokeRect(x,  y, GameHolder.size, GameHolder.size );
	}

	public void Destroy() {
		destroyed = true;
	}
}
