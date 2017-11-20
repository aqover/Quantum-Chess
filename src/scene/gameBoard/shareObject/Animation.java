package scene.gameBoard.shareObject;

import helper.Tuple;
import scene.gameBoard.view.pieces.Pieces;

public class Animation {	
	private static final Animation instance = new Animation();
	
	private Pieces source;
	private Tuple<Integer, Integer> sink;
	
	private boolean isAnimating = false;
	private int animationTimeSpeed = 1000; // millisecond
	private long startNanoTime, stopNanoTime;
	@SuppressWarnings("unused")
	private double tmpTime, tmpX, tmpY, tmpDistance;

	public static Animation getInstance() {
		return instance;
	}

	public Animation() {
		source = null;
		sink = null;
		startNanoTime = stopNanoTime = System.nanoTime();
		tmpTime = tmpX = tmpY = tmpDistance = 0.0;
	}

	public boolean isAnimating() {
		return isAnimating;
	}
	
	public void startAnimate(Pieces source, Tuple<Integer, Integer> mouse) {
//		System.out.println("start animation in");
		this.source = source;
		this.sink = mouse;
		
		isAnimating = true;
		tmpDistance = calculateDistance();
		
		startNanoTime = System.nanoTime();
		stopNanoTime = startNanoTime + (animationTimeSpeed * 1000000l);
	}
	
	public void update(long currentNanoTime) {
		if (!isAnimating) return;
				
		tmpTime = (Math.min(stopNanoTime, currentNanoTime) - startNanoTime) / 1000000.0;
		tmpX = source.getJ() + ((sink.getJ() - source.getJ()) * (tmpTime / animationTimeSpeed));
		tmpY = source.getI() + ((sink.getI() - source.getI()) * (tmpTime / animationTimeSpeed));

		source.setPositionOnScreen(tmpX*scene.gameBoard.shareObject.GameHolder.size, tmpY*scene.gameBoard.shareObject.GameHolder.size);
//		System.out.println(String.format("x = %f, y = %f", tmpX*shareObject.RenderableHolder.size, tmpY*shareObject.RenderableHolder.size));
		if (currentNanoTime > stopNanoTime)
		{
			isAnimating = false;
			source = null;
			sink = null;
		}
	}
	
	private double calculateDistance() {
		double x = Math.pow((source.getJ() - sink.getJ()), 2.0);
		double y = Math.pow((source.getI() - sink.getI()), 2.0);
		return Math.sqrt(x+y);
	}
}
