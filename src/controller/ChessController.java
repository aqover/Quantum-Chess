package controller;

import java.util.List;

import helper.InputUtility;
import helper.Team;
import helper.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.HBox;
import model.ChessBoard.Move;
import model.NormalChessGame;

import model.piece.ChessPiece;
import scene.gameBoard.ChessBoard;
import scene.gameBoard.ChessDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;

public class ChessController {
	
	private HBox pane;
	private NormalChessGame normalChessGame;
	private ChessDetail detail;
	private ChessBoard board;
	private AnimationTimer animationTimer;
	private Tuple<Integer, Integer> mouse;
	
	private long timePrevious;
	
	private ChessPiece selectedPiece;
	
	public boolean disable;
	
	public HBox getPane() {
		return pane;
	}
		
	public ChessDetail getDetail() {
		return detail;
	}

	public ChessController() {
		resetGame();
		initialPane();
	} 
	
	private void resetGame() {
		normalChessGame = new NormalChessGame();
		board = new ChessBoard();
		board.setBoard(normalChessGame);
		selectedPiece = null;		
		disable = false;
	}
	
	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() { 
		
		int result = normalChessGame.getGameResult();
		if (result != NormalChessGame.GAME_RESULT_ONGOING) {
			SceneManager.showMessage(NormalChessGame.getResultMessage(result), () -> {
				SceneManager.setSceneSelectGame();
				resetGame();
			});
			return;
		} 
		
		if (board.isBoardFlipped() != (normalChessGame.getTurn() != normalChessGame.firstTurn)) {
			flipBoard(); 
		}
	}
	
	private void select(ChessPiece piece) {
		if (selectedPiece != null) {
			selectedPiece.setSelected(false);	
			selectedPiece = null;
		}
		if (piece != null && piece.getTeam() == normalChessGame.getTurn()) {
			selectedPiece = piece;
			selectedPiece.setSelected(true);
		}
	}
	
	public void update() {
		if (Animation.getInstance().isAnimating())
			return;
		
		if (InputUtility.isMouseLeftClicked())
		{
			mouse = InputUtility.getMousePosition();
			if (board.isBoardFlipped()) {
				mouse = new Tuple<Integer, Integer>(7 - mouse.getI(), 7 - mouse.getJ(), null);
			}	
			
			ChessPiece piece = GameHolder.getInstance().getPieceFromMouse(mouse);

			if(selectedPiece != null) {		
				if (piece == null || (piece != null && selectedPiece.getTeam() != piece.getTeam())) {
					if (movePiece(selectedPiece, mouse)) {
						if (piece != null) {
							piece.Destroy();
						}
						select(null);
					}
				} else {
					if (getTurnTeam() == piece.getTeam()) {
						select(piece);
					} else {
						select(null);
					}
				}
				
				System.gc();
			} else {
				select(piece);
			}
		}			
	}

	public void flipBoard() {
		board.flipBoard();
	}
	
	public Team getTurnTeam() {
		return normalChessGame.getTurn();
	}

	private boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		synchronized (this) {
			if (normalChessGame.isMoveValid(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()))) {
				normalChessGame.move(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()));
				Animation.getInstance().startAnimate(
					source, 
					InputUtility.getMousePosition(), 
					() -> {
						source.setOnlyPosition(mouse.getI(), mouse.getJ());
						endTurn();
					}
				);
				return true;
			} else {
				SceneManager.showMessage("The move is not valid", () -> {});
			}
		}
			
		return false;
	}

	private void initialPane() {
		pane = new HBox();
		detail = new ChessDetail(this);
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				
				//Detail
				detail.update();
				detail.decreseTime(now - timePrevious);
				
				//Chat
				
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
	
}
