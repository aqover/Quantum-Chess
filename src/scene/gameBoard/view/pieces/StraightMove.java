package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;

public interface StraightMove {
	static final int MAX_STRAIGHT_MOVE = 8;
	static boolean DIRECTION_TOP = true;
	static boolean DIRECTION_RIGHT = true;
	static boolean DIRECTION_BOTTOM = true;
	static boolean DIRECTION_LEFT = true;
		
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTop(String[] board, Team team, int straightMove, int i, int j) {
		int t, r; char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i+k : i-k; 
			r = j;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverRight(String[] board, Team team, int straightMove, int i, int j) {
		int t,r; char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = i; 
			r = (team == Team.A)? j-k: j+k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottom(String[] board, Team team, int straightMove, int i, int j) {
		int t,r; char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.A)? i-k: i+k;
			r = j;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverLeft(String[] board, Team team, int straightMove, int i, int j) {
		int t,r; char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = i; 
			r = (team == Team.A)? j+k: j-k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.None));
		}
		return data;
	}
	
	static Team getTeamFromBoard(char c) {
		return (Character.isUpperCase(c)? Team.A: Team.B);
	}
}
