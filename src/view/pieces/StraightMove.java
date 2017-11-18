package view.pieces;

import java.util.ArrayList;

import control.Team;
import view.Tuple;

public interface StraightMove {
	static final int MAX_STRAIGHT_MOVE = 8;
	static boolean DIRECTION_TOP = true;
	static boolean DIRECTION_RIGHT = true;
	static boolean DIRECTION_BOTTOM = true;
	static boolean DIRECTION_LEFT = true;
		
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTop(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = shareObject.RenderableHolder.getInstance().getStateGame();
		int t, r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i+k : i-k; 
			r = j;
			if (t < 0 || t > 7) continue;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = i; 
			r = (team == Team.A)? j-k: j+k;
			if (t < 0 || t > 7) continue;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottom(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i-k: i+k;
			r = j;
			if (t < 0 || t > 7) continue;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = i; 
			r = (team == Team.A)? j+k: j-k;
			if (t < 0 || t > 7) continue;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
}
