package view.pieces;

import java.util.ArrayList;

import control.Team;
import view.Tuple;

public interface CornerMove {
	static final int MAX_CORNER_MOVE = 8;
	static boolean DIRECTION_TOP_LEFT = true;
	static boolean DIRECTION_TOP_RIGHT = true;
	static boolean DIRECTION_BOTTOM_LEFT = true;
	static boolean DIRECTION_BOTTOM_RIGHT = true;
		
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i+k: i-k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i+k: i-k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i-k: i+k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i-k: i+k; 
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
