package view;

import javafx.scene.canvas.GraphicsContext;
import shareObject.IRenderable;

public class Entity implements IRenderable {
	
	protected double x,y;
	protected int z;
	protected boolean visible, destroyed;
	
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

	@Override
	public void draw(GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

}
