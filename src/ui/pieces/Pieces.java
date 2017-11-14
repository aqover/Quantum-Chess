package ui.pieces;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Pieces {
	protected Team team;
	protected Canvas canvas;
	protected GraphicsContext gc;
	
	public Pieces(Canvas canvas) {
		team = Team.None;
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
	}
	
	public void draw() {
		
	}
	
	public void drawHover(int i, int j) {
		
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
