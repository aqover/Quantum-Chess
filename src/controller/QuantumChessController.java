package controller;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import helper.InputUtility;
import helper.Team;
import helper.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import model.ChessBoard.Move;
import model.QuantumChessGame;
import model.QuantumChessGame.QuantumMove;
import model.piece.Bishop;
import model.piece.ChessPiece;
import model.piece.Knight;
import model.piece.Queen;
import model.piece.Rook;
import scene.gameBoard.ChessBoard;
import scene.gameBoard.ChessDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;

public class QuantumChessController extends ChessController {
	
	private HBox pane;
	private ChessBoard board;
	private ChessDetail detail;	
	private QuantumChessGame quantumChessGame;
	private AnimationTimer animationTimer;
	private Tuple<Integer, Integer> mouse;
	
	private long timePrevious;
	
	protected ChessPiece selectedPiece;
	protected ChessPiece lastMovedPiece;
	
	public boolean disable;
	
	public HBox getPane() {
		return pane;
	}
	
	public ChessBoard getBoard() {
		return board;
	}
	
	public QuantumChessGame getQuantumChessGame() {
		return quantumChessGame;
	}

	public ChessDetail getDetail() {
		return detail;
	}

	public QuantumChessController() {
		resetGame();
		initialPane();
	} 
	
	private void resetGame() {
		quantumChessGame = new QuantumChessGame();
		board = new ChessBoard();
		board.setBoard(quantumChessGame);
		selectedPiece = null;
		lastMovedPiece = null;
		disable = false;
	} 

	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() {
		checkEndGame();
		if (board.isBoardFlipped() != (quantumChessGame.getTurn() != quantumChessGame.firstTurn)) {
			flipBoard(); 
		}
	}
	
	private void changeLastMovedPiece(ChessPiece piece) {
		if (lastMovedPiece != null) {
			lastMovedPiece.setLastMoved(false);
		}
		lastMovedPiece = piece;
		if (piece != null) {
			lastMovedPiece.setLastMoved(true);
		}
	}
	
	public void update() {
		if (Animation.getInstance().isAnimating())
			return;
		
		if (InputUtility.isMouseLeftClicked()) {
			mouse = InputUtility.getMousePosition();
			if (board.isBoardFlipped()) {
				mouse = new Tuple<Integer, Integer>(7 - mouse.getI(), 7 - mouse.getJ());
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
					if (piece != selectedPiece && getTurn() == piece.getTeam()) {
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

	protected void checkEndGame() {
		int result = quantumChessGame.getGameResult();
		if (result != QuantumChessGame.GAME_RESULT_ONGOING) {
			SceneManager.showMessage(
				QuantumChessGame.getResultMessage(result), 
				new SceneManager.onFinish() {
					@Override
					public void run() {
						animationTimer.stop();
						SceneManager.setSceneMainMenu();
					}
				}
			);
		} 
	}
	
	protected void updatePawn(ChessPiece piece) {
		quantumChessGame.upgradePawn(
				piece.getWhitePiece(), 
				piece.getBlackPiece()
			);

		try {
			Constructor<? extends ChessPiece> constructor = piece.getClass().getConstructor(Integer.class, Integer.class, Team.class);
			board.updatePawn(constructor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void checkUpdatePawn(Runnable onDone) {
		
		if (!quantumChessGame.isUpgradePawnAvailable()) {
			onDone.run();
			return;
		}
			
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
					
					if (btn == buttonTypeKnight) {
						updatePawn(Knight.getInstance());
					} else if (btn == buttonTypeBishop) {
						updatePawn(Bishop.getInstance());
					} else if (btn == buttonTypeRook) {
						updatePawn(Rook.getInstance());
					} else {
						updatePawn(Queen.getInstance());
					}

					onDone.run();
				}
			}
		);
	}
	
	public void flipBoard() {
		board.flipBoard();
	}
		
	public Team getTurn() {
		return quantumChessGame.getTurn();
	}
	
	private void select(ChessPiece piece) {
		if (selectedPiece != null) {
			selectedPiece.setSelected(false);	
			selectedPiece = null;
		}
		if (piece != null && piece.getTeam() == quantumChessGame.getTurn()) {
			selectedPiece = piece;
			selectedPiece.setSelected(true);
		}
	}

	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		
		Move move = new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ());
		if (quantumChessGame.successProb(move) > 0) {
			quantumChessGame.move(new QuantumMove(0.5, move));

			Animation.getInstance().startAnimate(
				source, 
				InputUtility.getMousePosition(), 
				() -> {
					
					if (lastMovedPiece != null) {
						lastMovedPiece.setLastMoved(false);
					}
					lastMovedPiece = source;
					
					source.setLastMoved(true);
					
					source.setOnlyPosition(mouse.getI(), mouse.getJ());
					checkUpdatePawn(() -> { 
						endTurn(); 
					});
				}
			);
			return true;
		} else {
			SceneManager.showMessage("The move is not valid", new SceneManager.onFinish() {});
		}
			
		return false;
	}

	protected void initialPane() {
		pane = new HBox();
		detail = new ChessDetail(this);
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				

				//Detail
				detail.update();
				detail.decreseTime(now - timePrevious);
				
				// Board game
				Animation.getInstance().update(now);
				GameHolder.getInstance().update();
				board.paintComponent();
				
//				if (selectedPiece != null) {
//					board.paintValidMoves(quantumChessGame.getValidMoves(
//						selectedPiece.getI(), 
//						selectedPiece.getJ()
//					));
//				}
				
				update();
				InputUtility.update();
				
				timePrevious = now;
			}
		};
		
		pane.getChildren().add(detail);
		pane.getChildren().add(board);
	}
	
}
