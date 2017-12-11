package controller;

import java.util.Arrays;

import helper.InputUtility;
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
import scene.gameBoard.Chat;
import scene.gameBoard.ChessOnlineDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;
import model.ChessBoard;
import model.NormalChessGame;
import model.ChessBoard.Move;

public class BoardGameOnlineController extends ChessController implements TCPListener, TCPCommand {
	
	private final Team PLAYER_TURN;
	
	protected Chat chat;
	protected ChessOnlineDetail detail;
	
	private TCPSocket socket;
	
	public Chat getChat() { return this.chat; }
	public ChessOnlineDetail getOnlineDetail() { return detail; }
	
	public BoardGameOnlineController(String username, TCPSocket socket) {
		super();
		
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
		detail = new ChessOnlineDetail(this);
		chat = new Chat();
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				

				//Detail
				detail.update();
				detail.decreseTime(now - timePrevious);
				
				// Board game
				Animation.getInstance().update(now);
				GameHolder.getInstance().update();
				board.paintComponent();
				
				// chat
				//chat.update();
				
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
		pane.getChildren().add(chat);
	}

	@Override
	public void endGame() {
		socket.write(this.PLAYER_TURN == Team.PLAYER_WHITE ? Command.WHITE_SURRENDER : Command.BLACK_SURRENDER, "");
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
					BoardGameOnlineController.super.endGame();
					socket.destroy();
				}
			});
	}
	
	@Override
	public void endTurn() {

		int result = normalChessGame.getGameResult();
		if (result != NormalChessGame.GAME_RESULT_ONGOING) {
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
				socket.write(Command.MOVE, move.toString());
				return true;
			}
			return false;
		} else {
			return super.movePiece(source, mouse);
		}
	}

	@Override
	public void OnReceived(Command cmd, String msg) {

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

			case WHITE_SURRENDER: case BLACK_SURRENDER:
				surrender();
				break;
				
			case SEND_TEXT:
				chat.insert(new Chat.ChatField(msg));
				break;
				
			default: // no command
		}
	}


	@Override public void OnSended(String msg) { }

	@Override public void OnClosed() { surrender(); }

	@Override public void OnConnected() { }
}
