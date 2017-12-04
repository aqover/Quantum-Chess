package controller;

import helper.OnlineMethod;
import helper.Team;
import helper.Tuple;
import library.socket.*;
import model.piece.ChessPiece;
import model.ChessBoard.Move;

public class BoardGameOnlineController extends ChessController implements TCPListener, TCPCommand {
	
	private TCPSocket socket;
	private OnlineMethod onlineMethod;
	
	public BoardGameOnlineController(TCPSocket socket) {
		super();
		super.isOnline = true;
		
		if (socket instanceof TCPServer)
			this.getNormalChessGame().firstTurn = Team.PLAYER_WHITE;
		else if (socket instanceof TCPClient)
			this.getNormalChessGame().firstTurn = Team.PLAYER_BLACK;
		
		this.socket = socket;
		onlineMethod = new OnlineMethod(this);
	}
	
	@Override
	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		// TODO Auto-generated method stub
		if (super.movePiece(source, mouse))
		{
			socket.write(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()).toString());
			return true;
		}
		
		return false;
	}
	



	@Override
	public void OnReceived(Command cmd, String value) {
		// TODO Auto-generated method stub
		if (cmd == Command.MOVE)
		{
			onlineMethod.move(value);
		}
		else if (cmd == Command.SET_BOARD_VERSION) {
			onlineMethod.setVersion(value);
		}
		else if (cmd == Command.GET_BOARD_VERSION) {
			socket.write(onlineMethod.getVersion());
		}
		else if (cmd == Command.SET_TIME_PLAYER_BLACK) { 
			onlineMethod.setTime(Team.PLAYER_BLACK, value);
		}
		else if (cmd == Command.GET_TIME_PLAYER_BLACK) { 
			socket.write(onlineMethod.getTime(Team.PLAYER_BLACK));
		}
		else if (cmd == Command.SET_TIME_PLAYER_WHITE) { 
			onlineMethod.setTime(Team.PLAYER_WHITE, value);
		}
		else if (cmd == Command.GET_TIME_PLAYER_WHITE) { 
			socket.write(onlineMethod.getTime(Team.PLAYER_WHITE));
		}
		else if (cmd == Command.BLACK_DRAW) {
			onlineMethod.draw(Team.PLAYER_BLACK);
		}
		else if (cmd == Command.BLACK_END_TURN) {
			onlineMethod.endTurn(Team.PLAYER_BLACK);
		}
		else if (cmd == Command.BLACK_SURRENDER) {
			onlineMethod.surrender(Team.PLAYER_BLACK);
		}
		else if (cmd == Command.WHITE_DRAW) {
			onlineMethod.draw(Team.PLAYER_WHITE);
		}
		else if (cmd == Command.WHITE_END_TURN) { 
			onlineMethod.endTurn(Team.PLAYER_WHITE);
		}
		else if (cmd == Command.WHITE_SURRENDER) {
			onlineMethod.surrender(Team.PLAYER_WHITE);
		}
		else if (cmd == Command.GAME_RESULT) { }
		
	}

	@Override public void OnSended(String msg) { }

	@Override public void OnClosed() { }

	@Override public void OnConnected() { }
}
