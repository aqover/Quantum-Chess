package view.pieces;

import java.util.ArrayList;

import control.Team;
import view.Entity;
import view.Tuple;

public class Pieces extends Entity {
	protected Team team;
	protected int i, j;
	protected double x, y;
	
	public Pieces(int i, int j) {
		team = Team.None;
		setPosition(i, j);
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void setPosition(int i, int j)
	{
		this.i = i;
		this.j = j;
		x = j * shareObject.RenderableHolder.size;
		y = i * shareObject.RenderableHolder.size;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}

	public ArrayList<Tuple<Integer, Integer>> drawCanMove() {
		// TODO Auto-generated method stub
		return null;
	}
}
