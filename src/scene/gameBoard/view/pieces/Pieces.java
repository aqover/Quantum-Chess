package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;
import scene.gameBoard.view.Entity;

public class Pieces extends Entity{
	protected Team team;
	protected int i, j;
	
	public Pieces(int i, int j) {
		this(i, j, Team.NONE);
	}
	
	public Pieces(int i, int j, Team t) {
		team = t;
		setPosition(i, j);
	}

	public Team getTeam() {
		return team;
	}
	
	public void setPosition(int i, int j)
	{
		this.i = i;
		this.j = j;
		x = j * scene.gameBoard.shareObject.GameHolder.size;
		y = (7-i) * scene.gameBoard.shareObject.GameHolder.size;
	}
	
	public void setPositionOnScreen(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public ArrayList<Tuple<Integer, Integer>> getMoveValid(String[] board) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update() {
		// Animation was finnished;
		
	}
}
