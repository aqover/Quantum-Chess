package helper;

import controller.ChessController;
import library.socket.TCPCommand;
import model.piece.ChessPiece;
import scene.gameBoard.shareObject.GameHolder;

public class OnlineMethod {
	
	private ChessController control; 
	
	public OnlineMethod(ChessController chess) {
		control = chess;
	}
	
	public void move(String move) {
		int si = move.charAt(0)-'1';
		int sj = move.charAt(1)-'A';
		int mi = move.charAt(2)-'1';
		int mj = move.charAt(3)-'A';
		
		ChessPiece source = GameHolder.getInstance().getPieceFromMouse(new Tuple<Integer, Integer>(7 - si, sj));
		Tuple<Integer, Integer> mouse = new Tuple<Integer, Integer>(7 - mi, mj);
		
		control.movePiece(source, mouse);
	}
	
	public void setVersion(String sBoard) {
		int row = Integer.getInteger(sBoard.substring(2, 3));
		int col = Integer.getInteger(sBoard.substring(3, 4));
		String board = sBoard.substring(4, 4+(row * col));
	}
	
	public String getVersion() {
		int row = control.getNormalChessGame().getBoard().getRows();
		int col = control.getNormalChessGame().getBoard().getColumns();
		String[] rows = control.getNormalChessGame().getBoard().getBoard();
		
		String board = String.format("%d%d", row, col);
		for(String tmp: rows) board += tmp;
		return board;
	}
	
	public void setTime(Team team, String sTime) {
		int lTime = Integer.getInteger(sTime.substring(0, 2));
		long time = Long.getLong(sTime.substring(2, 2 + lTime));
		
		if (team == Team.PLAYER_BLACK)
		{
			control.getDetail().setTimePlayerB(time);
		}
		else if (team == Team.PLAYER_WHITE)
		{
			control.getDetail().setTimePlayerW(time);
		}
	}
	
	public String getTime(Team team) {
		long time = 0l;
		if (team == Team.PLAYER_BLACK)
			time = control.getDetail().getTimePlayerB();
		else if(team == Team.PLAYER_WHITE)
			time = control.getDetail().getTimePlayerW();
		
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
