package controller;

import java.util.Arrays;

import helper.InputUtility;
import helper.OnlineMethod;
import helper.Team;
import helper.Tuple;
import javafx.scene.control.ButtonType;
import library.socket.*;
import model.piece.Bishop;
import model.piece.ChessPiece;
import model.piece.Knight;
import model.piece.Queen;
import model.piece.Rook;
import model.ChessBoard.Move;

public class BoardGameOnlineController extends ChessController implements TCPListener, TCPCommand {
	
	private final Team playerTurn;

	private TCPSocket socket;
	private OnlineMethod onlineMethod;
	
	public BoardGameOnlineController(TCPSocket socket) {
		super();
		this.playerTurn = (socket instanceof TCPServer) ? Team.PLAYER_WHITE : Team.PLAYER_BLACK;
		this.socket = socket;
		this.onlineMethod = new OnlineMethod(this);

		if (this.playerTurn == Team.PLAYER_BLACK) {
			flipBoard();
		}
	}
	
	public boolean isCurrentTurn() {
		return playerTurn == getTurn();
	}
	
	@Override
	public void endTurn() {
		checkEndGame();
	}

	@Override
	public void update() {
		if (isCurrentTurn()) {
			super.update();
		}
	}

	@Override
	protected void checkUpgradePawn(Runnable onDone) {
		if (isCurrentTurn() && getNormalChessGame().isUpgradePawnAvailable()) {
				
			ButtonType buttonTypeQueen = new ButtonType("Queen");
			ButtonType buttonTypeKnight = new ButtonType("Knight");
			ButtonType buttonTypeBishop = new ButtonType("Bishop");
			ButtonType buttonTypeRook = new ButtonType("Rook");
			
			SceneManager.showMessage(
				"Select new piece", 
				Arrays.asList(
					buttonTypeQueen, 
					buttonTypeKnight,
					buttonTypeBishop,
					buttonTypeRook
				),
				new SceneManager.onFinish() {
					@Override
					public void run(ButtonType btn) {
						
						// TODO send message to other players
						
						if (btn == buttonTypeKnight) {
							upgradePawn(Knight.getInstance());
						} else if (btn == buttonTypeBishop) {
							upgradePawn(Bishop.getInstance());
						} else if (btn == buttonTypeRook) {
							upgradePawn(Rook.getInstance());
						} else {
							upgradePawn(Queen.getInstance());
						}

						onDone.run();
					}
				}
			);

		} else {
			onDone.run();
		}
	}
	
	@Override
	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		if (super.movePiece(source, mouse)) {
			// TODO send message to the other team
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
