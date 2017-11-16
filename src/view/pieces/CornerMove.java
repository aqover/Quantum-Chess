package view.pieces;

import java.util.ArrayList;

import view.Team;
import view.Tuple;

public interface CornerMove {
	static final int MAX_CORNER_MOVE = 8;
	static boolean DIRECTION_TOP_LEFT = true;
	static boolean DIRECTION_TOP_RIGHT = true;
	static boolean DIRECTION_BOTTOM_LEFT = true;
	static boolean DIRECTION_BOTTOM_RIGHT = true;
	
	public ArrayList<Tuple<Integer, Integer> > drawCanMove(int i, int j);
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=straightMove; k>= 0 && (i-k) >= 0 && (j-k) >= 0; k--)
			{
				t = i-k; 
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
			for(int k=0; k < straightMove && (i+k) < 8 && (j+k) < 8; k++)
			{
				t = i+k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=straightMove; k>= 0 && (i-k) >= 0 && (j+k) < 8; k--)
			{
				t = i-k; 
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
			for(int k=0; k < straightMove && (i+k) < 8 && (j-k) >= 0; k++)
			{
				t = i+k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomRight(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=0; k < straightMove && (i+k) < 8  && (j+k) < 8; k++)
			{
				t = i+k; 
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
			for(int k=straightMove; k>= 0 && (i-k) >= 0 && (j-k) >= 0; k--)
			{
				t = i-k; 
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
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomLeft(Team team, int straightMove, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		if (team == Team.A)
		{
			for(int k=0; k < straightMove && (i+k) < 8 && (j-k) >= 0; k++)
			{
				t = i+k; 
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
			for(int k=straightMove; k>= 0 && (i-k) >= 0 && (j+k) < 8; k--)
			{
				t = i-k; 
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
