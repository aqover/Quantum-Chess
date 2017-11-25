package controller;

import helper.OnlineMethod;
import helper.Team;
import library.socket.TCPCommand;
import library.socket.TCPListener;
import library.socket.TCPSocket;

public class BoardGameOnlineController extends ChessController implements TCPListener, TCPCommand {
	
	private TCPSocket socket;
	private OnlineMethod onlineMethod;
	
	public BoardGameOnlineController(TCPSocket socket) {
		this.socket = socket;
		onlineMethod = new OnlineMethod();
	}
	
	private void sendResponde(Command command, TCPResponde result) {
		String msg = String.format("%s%s", command.toString(), result.toString());
		socket.write(msg);
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
