package view.pieces;

import control.Team;
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
		
	public default Tuple<Integer, Integer> drawHoverTopLeft(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i+2: i-2;
		r = (team == Team.A)? j+1: j-1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverTopRight(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i+2: i-2;
		r = (team == Team.A)? j-1: j+1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightTop(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i+1: i-1;
		r = (team == Team.A)? j-2: j+2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightBottom(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i-1: i+1;
		r = (team == Team.A)? j-2: j+2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomRight(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i-2: i+2;
		r = (team == Team.A)? j-1: j+1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomLeft(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i-2: i+2;
		r = (team == Team.A)? j+1: j-1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftBottom(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i-1: i+1;
		r = (team == Team.A)? j+2: j-2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftTop(Team team, int i, int j) {
		Pieces[][] stateGame = (Pieces[][]) shareObject.RenderableHolder.getInstance().getStateGame();
		int t,r;
		t = (team == Team.A)? i+1: i-1;
		r = (team == Team.A)? j+2: j-2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		if(stateGame[t][r] != null)
		{
			if (stateGame[t][r].getTeam() != team)
				return new Tuple<Integer, Integer>(t, r, stateGame[t][r].getTeam());
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.None);
		return null;
	}
}
