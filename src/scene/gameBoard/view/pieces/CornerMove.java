package scene.gameBoard.view.pieces;

import java.util.ArrayList;

import helper.Team;
import helper.Tuple;

public interface CornerMove {
	static final int MAX_CORNER_MOVE = 8;
	static boolean DIRECTION_TOP_LEFT = true;
	static boolean DIRECTION_TOP_RIGHT = true;
	static boolean DIRECTION_BOTTOM_LEFT = true;
	static boolean DIRECTION_BOTTOM_RIGHT = true;
		
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopLeft(String[] board, Team team, int straightMove, int i, int j) {
		int t,r;  char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.PLAYER_WHITE)? i+k: i-k; 
			r = (team == Team.PLAYER_WHITE)? j+k: j-k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.NONE));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverTopRight(String[] board, Team team, int straightMove, int i, int j) {
		int t,r;  char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.PLAYER_WHITE)? i+k: i-k; 
			r = (team == Team.PLAYER_WHITE)? j-k: j+k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.NONE));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomRight(String[] board, Team team, int straightMove, int i, int j) {
		int t,r;  char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.PLAYER_WHITE)? i-k: i+k; 
			r = (team == Team.PLAYER_WHITE)? j-k: j+k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.NONE));
		}
		return data;
	}
	
	public default ArrayList<Tuple<Integer, Integer> > drawHoverBottomLeft(String[] board, Team team, int straightMove, int i, int j) {
		int t,r;  char c;
		ArrayList<Tuple<Integer, Integer> > data = new ArrayList<Tuple<Integer, Integer> >();
		for(int k=1; k <= straightMove; k++)
		{
			t = (team == Team.PLAYER_WHITE)? i-k: i+k; 
			r = (team == Team.PLAYER_WHITE)? j+k: j-k;
			if (t < 0 || t > 7 || r < 0 || r > 7) continue;
			c = board[t].charAt(r);
			if(c != '.')
			{
				if (getTeamFromBoard(c) != team)
					data.add(new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c)));
				break;
			}
			else
				data.add(new Tuple<Integer, Integer>(t, r, Team.NONE));
		}
		return data;
	}
	
	static Team getTeamFromBoard(char c) {
		return (Character.isUpperCase(c)? Team.PLAYER_WHITE: Team.PLAYER_BLACK);
	}
}
