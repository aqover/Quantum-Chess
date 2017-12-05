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
import model.NormalChessGame;
import model.piece.Bishop;
import model.piece.ChessPiece;
import model.piece.Knight;
import model.piece.Queen;
import model.piece.Rook;
import scene.gameBoard.ChessBoard;
import scene.gameBoard.ChessDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;

public class ChessController {
	
	private HBox pane;
	private ChessBoard board;
	private ChessDetail detail;	
	private NormalChessGame normalChessGame;
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
	
	public NormalChessGame getNormalChessGame() {
		return normalChessGame;
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
		lastMovedPiece = null;
		disable = false;
	} 

	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() {
		checkEndGame();
		if (board.isBoardFlipped() != (normalChessGame.getTurn() != normalChessGame.firstTurn)) {
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
		int result = normalChessGame.getGameResult();
		if (result != NormalChessGame.GAME_RESULT_ONGOING) {
			SceneManager.showMessage(
				NormalChessGame.getResultMessage(result), 
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
	
	protected void upgradePawn(ChessPiece piece) {
		normalChessGame.upgradePawn(
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
	protected void checkUpgradePawn(Runnable onDone) {
		
		if (!normalChessGame.isUpgradePawnAvailable()) {
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
	}
	
	public void flipBoard() {
		board.flipBoard();
	}
	
	public void undo() {
		System.out.println("undo");
		
		if (normalChessGame.undo()) {
			if (board.isBoardFlipped()) {
				board.setBoard(normalChessGame);
				board.flipBoard();
			} else {
				board.setBoard(normalChessGame);
			}
			
			select(null);
			changeLastMovedPiece(null);
		}
	}
	
	public void redo() {
		
		if (normalChessGame.redo()) {
			if (board.isBoardFlipped()) {
				board.setBoard(normalChessGame);
				board.flipBoard();
			} else {
				board.setBoard(normalChessGame);
			}
			
			select(null);
			changeLastMovedPiece(null);
		}
	}
	
	public Team getTurn() {
		return normalChessGame.getTurn();
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

	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		if (normalChessGame.isMoveValid(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()))) {
			
			normalChessGame.move(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()));

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
					checkUpgradePawn(() -> { 
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
