package controller;

import java.util.Arrays;

import helper.InputUtility;
import helper.Team;
import helper.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import library.socket.TCPCommand;
import library.socket.TCPListener;
import library.socket.TCPServer;
import library.socket.TCPSocket;
import model.QuantumChessGame;
import model.ChessBoard.Move;
import model.QuantumChessGame.QuantumMove;
import model.piece.Bishop;
import model.piece.ChessPiece;
import model.piece.Knight;
import model.piece.Queen;
import model.piece.Rook;
import scene.gameBoard.Chat;
import scene.gameBoard.QuantumChessOnlineDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;

public class QuantumChessOnlineController extends QuantumChessController implements TCPListener {

	private final Team PLAYER_TURN;
	
	protected Chat chat;
	protected QuantumChessOnlineDetail detail;
	
	private TCPSocket socket;
	
	public Chat getChat() { return this.chat; }
	public QuantumChessOnlineDetail getOnlineDetail() { return detail; }

	public QuantumChessOnlineController(String username, TCPSocket socket) {
		super();
		
		System.out.println(socket);
		
		chat.setUserName(username);
		chat.setSocket(socket);
		
		this.PLAYER_TURN = (socket instanceof TCPServer) ? Team.PLAYER_WHITE : Team.PLAYER_BLACK;
		this.socket = socket;
		
		if (this.PLAYER_TURN == Team.PLAYER_BLACK) {
			flipBoard();
		}
	}

	public boolean isCurrentTurn() {
		return PLAYER_TURN == getTurn();
	}

	@Override
	protected void initialPane() {
		pane = new HBox();
		detail = new QuantumChessOnlineDetail(this);
		chat = new Chat();
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				

				//Detail
				detail.update();
				detail.increseTime(now - timePrevious);
				
				// Board game
				Animation.getInstance().update(now);
				GameHolder.getInstance().update();
				board.paintComponentQuantum();
				
				// chat
				chat.update();
				
				// paint value
				if (possibility != null) {
					board.paintPossibilityMoves(possibility, getMoveProb(), selectedPiece);
				}
				
				update();
				InputUtility.update();
				
				timePrevious = now;
			}
		};
		
		pane.getChildren().add(detail);
		pane.getChildren().add(board);
		pane.getChildren().add(chat);
	}

	
	@Override
	public void endGame() {
		socket.write(this.PLAYER_TURN == Team.PLAYER_WHITE ? TCPCommand.WHITE_SURRENDER : TCPCommand.BLACK_SURRENDER, "");
		super.endGame();
		socket.destroy();
	}

	protected void surrender() {
		
		String opponentName = (this.PLAYER_TURN == Team.PLAYER_WHITE ? 
			getOnlineDetail().getNameWhite() : 
			getOnlineDetail().getNameBlack());
		
		SceneManager.showMessage(opponentName + " has resigned. You win!",
			new SceneManager.onFinish() {
				@Override
				public void run() {
					QuantumChessOnlineController.super.endGame();
					socket.destroy();
				}
			});
	}
	
	@Override
	public void endTurn() {
		int result = quantumChessGame.getGameResult();
		if (result != QuantumChessGame.GAME_RESULT_ONGOING) {
			socket.destroy();
		}
		super.checkEndGame();
	}

	@Override
	public void update() {
		if (isCurrentTurn()) {
			super.update();
		}
	}

	@Override
	protected void checkUpgradePawn(Runnable onDone) {
		if (isCurrentTurn() && getQuantumChessGame().isUpgradePawnAvailable()) {
				
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
							socket.write(TCPCommand.SET_UPGRADE_PAWN, Character.toString(Knight.getChar()));
							upgradePawn(Knight.getInstance());
						} else if (btn == buttonTypeBishop) {
							socket.write(TCPCommand.SET_UPGRADE_PAWN, Character.toString(Bishop.getChar()));
							upgradePawn(Bishop.getInstance());
						} else if (btn == buttonTypeRook) {
							socket.write(TCPCommand.SET_UPGRADE_PAWN, Character.toString(Rook.getChar()));
							upgradePawn(Rook.getInstance());
						} else {
							socket.write(TCPCommand.SET_UPGRADE_PAWN, Character.toString(Queen.getChar()));
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
		QuantumMove qmove = new QuantumMove(getMoveProb(), move);
		if (isCurrentTurn()) {
			if (super.movePiece(source, mouse)) {
				String status = quantumChessGame.lastMoveStatus() ? "1" : "0";
				socket.write(TCPCommand.MOVE, status + qmove.toString());
				return true;
			}
			return false;
		}
		return false;
	}
	
	@Override
	public void OnReceived(TCPCommand cmd, String msg) {

		switch (cmd) {
		
			case MOVE:
				boolean status = msg.charAt(0) == '1';
				QuantumMove qmove = new QuantumMove(msg.substring(1));
				ChessPiece source = GameHolder.getInstance().getPiece(new Tuple<Integer, Integer>(qmove.MOVE.row1, qmove.MOVE.col1));
				movePiece(source, new Tuple<Integer, Integer>(qmove.MOVE.row2, qmove.MOVE.col2), status);
				break;
			
			case SET_UPGRADE_PAWN: 
				try {
					this.upgradePawn(ChessPiece.getInstance(msg.charAt(0)));
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case SET_BOARD_VERSION: break;

			case WHITE_SURRENDER: case BLACK_SURRENDER:
				surrender();
				break;
				
			case SEND_TEXT:
				chat.insert(new Chat.ChatField(msg));
				break;
				
			default: // no command
		}
	}

	@Override public void OnSended(String msg) { System.out.println(msg + " is send");}

	@Override public void OnClosed() { }

	@Override public void OnConnected() { }
}
