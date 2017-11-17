package view.pieces;

import java.util.ArrayList;

import view.Team;
import view.Tuple;

public interface StraightMove {
	static final int MAX_STRAIGHT_MOVE = 8;
	static boolean DIRECTION_TOP = true;
	static boolean DIRECTION_RIGHT = true;
	static boolean DIRECTION_BOTTOM = true;
	static boolean DIRECTION_LEFT = true;
	
	public ArrayList<Tuple<Integer, Integer> > drawCanMove(int i, int j);
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTop(Team team, int straightMove, int i, int j) {
//		System.out.println("Draw Top in");
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t, r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
//			System.out.println(String.format("i = %d, j = %d, k = %d", i, j, straightMove));
			for(int k=straightMove; k>= 0 && (i-k) >= 0; k--)
			{
				t = i-k;
				r = j;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		else if(team == Team.B)
		{
			for(int k=0; k < straightMove && (i+k) < 8; k++)
			{
				t = i+k;
				r = j;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=0; k < straightMove && (j+k) < 8; k++)
			{
				t = i;
				r = j+k;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		else if(team == Team.B)
		{
			for(int k=straightMove; k>= 0 && (j-k) >= 0; k--)
			{
				t = i;
				r = j-k;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottom(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=0; k < straightMove && (i+k) < 8; k++)
			{
				t = i+k;
				r = j;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		else if(team == Team.B)
		{
			for(int k=straightMove; k>= 0 && (i-k) >= 0; k--)
			{
				t = i-k;
				r = j;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=0; k < straightMove && (j+k) < 8; k++)
			{
				t = i;
				r = j-k;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		else if(team == Team.B)
		{
			for(int k=straightMove; k>= 0 && (j-k) >= 0; k--)
			{
				t = i;
				r = j+k;
				if(stateGame[t][r] != null)
				{
					if (stateGame[t][r].getTeam() != team)
						data.add(new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam()));
					break;
				}
				else
					data.add(new Tuple<Integer, Integer>(t, r, Team.None));
			}
		}
		return data;
	}
}
