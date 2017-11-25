package helper;

import library.socket.TCPCommand;

public class OnlineMethod {
	
	public OnlineMethod() {
		
	}
	
	public void move(String move) {
		
	}
	
	public void setVersion(String sBoard) {
		int row = Integer.getInteger(sBoard.substring(2, 3));
		int col = Integer.getInteger(sBoard.substring(3, 4));
		String board = sBoard.substring(4, 4+(row * col));
	}
	
	public String getVersion() {
		int row = 8;
		int col = 8;
		String[] rows = null;
		
		String board = String.format("%d%d", row, col);
		for(String tmp: rows) board += tmp;
		return board;
	}
	
	public void setTime(Team team, String sTime) {
		int lTime = Integer.getInteger(sTime.substring(0, 2));
		long time = Long.getLong(sTime.substring(2, 2 + lTime));
		
		if (team == Team.PLAYER_BLACK)
		{
			
		}
		else if (team == Team.PLAYER_WHITE)
		{
			
		}
	}
	
	public String getTime(Team team) {
		long time = 0l;
		if (team == Team.PLAYER_BLACK)
			time = 0l;
		else if(team == Team.PLAYER_WHITE)
			time = 0l;
		
		String sTime = String.format("%ld", time);
		int lTime = sTime.length();
		return String.format("%02d%s", lTime, sTime);
	}
	
	public void draw(Team team) {
		
	}
	
	public void surrender(Team team) {
		
	}
	
	public void endTurn(Team team) {
		
	}
	
	public void gameResult(library.socket.TCPCommand.GameResult result) {
		
	}
	
}
