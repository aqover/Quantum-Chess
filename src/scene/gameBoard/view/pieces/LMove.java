package scene.gameBoard.view.pieces;

import helper.Team;
import helper.Tuple;

public interface LMove {
	static boolean DIRECTION_TOP_LEFT = true;
	static boolean DIRECTION_TOP_RIGHT = true;
	static boolean DIRECTION_RIGHT_TOP = true;
	static boolean DIRECTION_RIGHT_BOTTOM = true;
	static boolean DIRECTION_BOTTOM_RIGHT = true;
	static boolean DIRECTION_BOTTOM_LEFT = true;
	static boolean DIRECTION_LEFT_BOTTOM = true;
	static boolean DIRECTION_LEFT_TOP = true;
		
	public default Tuple<Integer, Integer> drawHoverTopLeft(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i+2: i-2;
		r = (team == Team.PLAYER_WHITE)? j+1: j-1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverTopRight(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i+2: i-2;
		r = (team == Team.PLAYER_WHITE)? j-1: j+1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightTop(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i+1: i-1;
		r = (team == Team.PLAYER_WHITE)? j-2: j+2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverRightBottom(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i-1: i+1;
		r = (team == Team.PLAYER_WHITE)? j-2: j+2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomRight(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i-2: i+2;
		r = (team == Team.PLAYER_WHITE)? j-1: j+1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverBottomLeft(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i-2: i+2;
		r = (team == Team.PLAYER_WHITE)? j+1: j-1;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftBottom(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i-1: i+1;
		r = (team == Team.PLAYER_WHITE)? j+2: j-2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	public default Tuple<Integer, Integer> drawHoverLeftTop(String[] board, Team team, int i, int j) {
		int t,r;  char c;
		t = (team == Team.PLAYER_WHITE)? i+1: i-1;
		r = (team == Team.PLAYER_WHITE)? j+2: j-2;
		if (t < 0 || t > 7 || r < 0 || r > 7) return null;
		c = board[t].charAt(r);
		if(c != '.')
		{
			if (getTeamFromBoard(c) != team)
				return new Tuple<Integer, Integer>(t, r, getTeamFromBoard(c));
		}
		else
			return new Tuple<Integer, Integer>(t, r, Team.NONE);
		return null;
	}
	
	default Team getTeamFromBoard(char c) {
		return (Character.isUpperCase(c)? Team.PLAYER_WHITE: Team.PLAYER_BLACK);
	}
}
