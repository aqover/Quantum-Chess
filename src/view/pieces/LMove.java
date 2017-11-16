package view.pieces;

import java.util.ArrayList;

import view.Team;
import view.Tuple;

public interface LMove {
	static boolean DIRECTION_TOP_LEFT = true;
	static boolean DIRECTION_TOP_RIGHT = true;
	static boolean DIRECTION_RIGHT_TOP = true;
	static boolean DIRECTION_RIGHT_BOTTOM = true;
	static boolean DIRECTION_BOTTOM_RIGHT = true;
	static boolean DIRECTION_BOTTOM_LEFT = true;
	static boolean DIRECTION_LEFT_BOTTOM = true;
	static boolean DIRECTION_LEFT_TOP = true;
	
	public ArrayList<Tuple<Integer, Integer> > drawCanMove(int i, int j);
	
	public default Tuple<Integer, Integer> drawHoverTopLeft(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i-2;
			r = j-1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i+2;
			r = j+1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverTopRight(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i-2;
			r = j+1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i+2;
			r = j-1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightTop(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i-1;
			r = j+2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i+1;
			r = j-2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightBottom(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i+1;
			r = j+2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i-1;
			r = j-2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomRight(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i+2;
			r = j+1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i-2;
			r = j-1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomLeft(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i+2;
			r = j-1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i-2;
			r = j+1;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftBottom(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i+1;
			r = j-2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i-1;
			r = j+2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftTop(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		if(team == Team.A)
		{
			t = i-1;
			r = j-2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		else if (team == Team.B)
		{
			t = i+1;
			r = j+2;
			if(stateGame[t][r] != null)
			{
				if (stateGame[t][r].getTeam() != team)
					return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
			}
			else
				return new Tuple<Integer, Integer>(t, r, Team.None);
		}
		return null;
	}
}
