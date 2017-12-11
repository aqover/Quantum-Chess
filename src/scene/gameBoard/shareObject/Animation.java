package scene.gameBoard.shareObject;

import helper.Tuple;
import javafx.animation.AnimationTimer;
import model.piece.ChessPiece;

public class Animation extends AnimationTimer {	
	private static final Animation INSTANCE = new Animation();
	
	private Runnable onFinished;
	
	private ChessPiece source;
	private Tuple<Integer, Integer> sink;
	
	private boolean isAnimating = false;
	private int animationTimeSpeed = 1000; // millisecond
	private long startNanoTime, stopNanoTime;
	@SuppressWarnings("unused")
	private double tmpTime, tmpX, tmpY, tmpDistance;

	public static Animation getInstance() {
		return INSTANCE;
	}

	public Animation() {
		source = null;
		sink = null;
		startNanoTime = stopNanoTime = System.nanoTime();
		tmpTime = tmpX = tmpY = tmpDistance = 0.0;
	}
	
	public Animation(ChessPiece source, Tuple<Integer, Integer> mouse, Runnable onFinished) {		
		this();
		startAnimate(source, mouse, onFinished);
	}

	public boolean isAnimating() {
		return isAnimating;
	}
	
	public void startAnimate(ChessPiece source, Tuple<Integer, Integer> mouse, Runnable onFinished) {
//		System.out.println("start animation in");
		this.source = source;
		this.sink = mouse;
		
		isAnimating = true;
		tmpDistance = calculateDistance();
		
		startNanoTime = System.nanoTime();
		stopNanoTime = startNanoTime + (animationTimeSpeed * 1000000l);
		
		this.onFinished = onFinished;
		if (GameHolder.pieceMove != null) {
			GameHolder.pieceMove.play();
		}
	}
	
	public void update(long currentNanoTime) {
		if (!isAnimating) return;
				
		tmpTime = (Math.min(stopNanoTime, currentNanoTime) - startNanoTime) / 1000000.0;
		tmpX = source.getX() + ((sink.getJ() * GameHolder.size - source.getX()) * (tmpTime / animationTimeSpeed));
		tmpY = source.getY() + ((sink.getI() * GameHolder.size - source.getY()) * (tmpTime / animationTimeSpeed));

		source.setPositionOnScreen(tmpX, tmpY);
//		System.out.println(String.format("x = %f, y = %f", tmpX * GameHolder.size, (7.0 - tmpY) * GameHolder.size));
		if (currentNanoTime > stopNanoTime)
		{
			stopAnimate();
		}
	}
	
	public void stopAnimate() {
		this.onFinished.run();
		
		isAnimating = false;
		source = null;
		sink = null;
		
		if (GameHolder.pieceMove != null) {
			GameHolder.pieceMove.stop();
		}
	}
	
	private double calculateDistance() {
//		double x = Math.pow((source.getJ() - sink.getJ()), 2.0);
//		double y = Math.pow((source.getI() - sink.getI()), 2.0);
//		return Math.sqrt(x+y);
		return Math.hypot(source.getJ() - sink.getJ(), source.getI() - sink.getI());
	}

	@Override
	public void handle(long now) {
		// TODO Auto-generated method stub
		update(now);
		GameHolder.getInstance().update();
	}
}
