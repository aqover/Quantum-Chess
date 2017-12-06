package controller;

import java.util.Arrays;

import helper.InputUtility;
import helper.OnlineMethod;
import helper.Team;
import helper.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import library.socket.*;
import model.piece.Bishop;
import model.piece.ChessPiece;
import model.piece.Knight;
import model.piece.Queen;
import model.piece.Rook;
import scene.gameBoard.ChessDetail;
import scene.gameBoard.ChessOnlineDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;
import model.ChessBoard;
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
	protected void initialPane() {
		pane = new HBox();
		detail = new ChessOnlineDetail(this);
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				

				//Detail
				detail.update();
				detail.decreseTime(now - timePrevious);
				
				// Board game
				Animation.getInstance().update(now);
				GameHolder.getInstance().update();
				board.paintComponent();
				
				if (selectedPiece != null) {
					board.paintValidMoves(normalChessGame.getValidMoves(
						selectedPiece.getI(), 
						selectedPiece.getJ()
					));
				}
				
				update();
				InputUtility.update();
				
				timePrevious = now;
			}
		};
		
		pane.getChildren().add(detail);
		pane.getChildren().add(board);
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
							socket.write(Command.SET_UPGRADE_PAWN, Character.toString(Knight.getChar()));
							upgradePawn(Knight.getInstance());
						} else if (btn == buttonTypeBishop) {
							socket.write(Command.SET_UPGRADE_PAWN, Character.toString(Bishop.getChar()));
							upgradePawn(Bishop.getInstance());
						} else if (btn == buttonTypeRook) {
							socket.write(Command.SET_UPGRADE_PAWN, Character.toString(Rook.getChar()));
							upgradePawn(Rook.getInstance());
						} else {
							socket.write(Command.SET_UPGRADE_PAWN, Character.toString(Queen.getChar()));
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
		Move move = new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ());
		if (isCurrentTurn()) {
			if (super.movePiece(source, mouse)) {
				socket.write(Command.MOVE.toString() + move.toString());
				return true;
			}
			return false;
		} else {
			return super.movePiece(source, mouse);
		}
	}

	@Override
	public void OnReceived(Command cmd, String msg) {

		System.out.println(cmd + " ## " + msg);

		switch (cmd) {
		
			case MOVE:
				ChessBoard.Move move = new ChessBoard.Move(msg);
				ChessPiece source = GameHolder.getInstance().getPiece(new Tuple<Integer, Integer>(move.row1, move.col1));
				Tuple<Integer, Integer> mouse = new Tuple<Integer, Integer>(move.row2, move.col2);
				movePiece(source, mouse);
				break;
			
			case SET_UPGRADE_PAWN: 
				try {
					this.upgradePawn(ChessPiece.getInstance(msg.charAt(0)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case SET_BOARD_VERSION: break;

			default: // no command
		}
		if (cmd == Command.MOVE) {

			
		} else {
			
		}
		/*
		else if (cmd == Command.SET_BOARD_VERSION) {
			onlineMethod.setVersion(value);
		}
		else if (cmd == Command.GET_BOARD_VERSION) {
		//	socket.write(onlineMethod.getVersion());
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
		else if (cmd == Command.GAME_RESULT) {
		}
		*/
	}

	@Override public void OnSended(String msg) { }

	@Override public void OnClosed() { }

	@Override public void OnConnected() { }
}
