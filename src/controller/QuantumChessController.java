package controller;

import java.lang.reflect.Constructor;
import java.util.Arrays;

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
import scene.gameBoard.QuantumChessDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;

public class QuantumChessController extends ChessController {
	
	protected QuantumChessGame quantumChessGame;
	protected QuantumChessDetail detail;
	
	protected double moveProb;
	
	public QuantumChessGame getQuantumChessGame() {
		return quantumChessGame;
	}

	public QuantumChessDetail getQuantumDetail() {
		return this.detail;
	}
	
	public QuantumChessController() {
		resetGame();
		initialPane();
	} 
	
	public void setMoveProb(double moveProb) { this.moveProb = moveProb; }
	public double getMoveProb() { return this.moveProb; }
	
	private void resetGame() {
		quantumChessGame = new QuantumChessGame();
		board = new ChessBoard();
		board.setBoard(quantumChessGame);
		selectedPiece = null;
		lastMovedPiece = null;
		disable = false;
		moveProb = 0.5;
	} 

	@Override
	public void endTurn() {
		checkEndGame();
		if (board.isBoardFlipped() != (quantumChessGame.getTurn() != quantumChessGame.FIRST_TURN)) {
			flipBoard(); 
		}
	}

	@Override
	public void pass() {
		quantumChessGame.pass();
		endTurn();
		select(null);
	}
	
	@Override
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
	
	@Override
	protected void upgradePawn(ChessPiece piece) {
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
	
	@Override
	protected void checkUpgradePawn(Runnable onDone) {
		
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
	
	@Override
	public Team getTurn() {
		return quantumChessGame.getTurn();
	}
	
	protected double[][] possibility = null;
	
	@Override
	protected void select(ChessPiece piece) {
		if (selectedPiece != null) {
			possibility = null;
			selectedPiece.setSelected(false);	
			selectedPiece = null;
		}
		if (piece != null && piece.getTeam() == quantumChessGame.getTurn()) {
			possibility = quantumChessGame.getPossibilityMoves(piece.getI(), piece.getJ());
			selectedPiece = piece;
			selectedPiece.setSelected(true);
		}
	}

	@Override
	public void update() {
		if (Animation.getInstance().isAnimating())
			return;
		
		if (InputUtility.isMouseLeftClicked()) {
			mouse = InputUtility.getMousePosition();
			if (board.isBoardFlipped()) {
				mouse = new Tuple<Integer, Integer>(7 - mouse.getI(), 7 - mouse.getJ());
			}	
			
			ChessPiece piece = GameHolder.getInstance().getPiece(mouse);
			if(selectedPiece != null) {		
				if (possibility[mouse.getI()][mouse.getJ()] > 0) {
					if (movePiece(selectedPiece, mouse)) {
						piece = null;
						select(null);
					}
				} else if (piece != null && piece != selectedPiece && getTurn() == piece.getTeam()) {
					select(piece);
				} else {
					select(null);
				}
				
				System.gc();
			} else {
				select(piece);
			}
		}			
	}

	@Override
	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse) {
		
		Move move = new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ());
		if (quantumChessGame.successProb(move) > 0) {

			quantumChessGame.move(new QuantumMove(getMoveProb(), move));
			
			Tuple<Integer, Integer> target = new Tuple<Integer, Integer>(mouse);
			if (board.isBoardFlipped()) {
				target = new Tuple<Integer, Integer> (7 - mouse.getI(), 7 - mouse.getJ());
			}
			
			Animation.getInstance().startAnimate(
				source, 
				target, 
				() -> {
					
					if (lastMovedPiece != null) {
						lastMovedPiece.setLastMoved(false);
					}
					lastMovedPiece = source;
					
					select(null);
					source.setLastMoved(true);
					
					source.setOnlyPosition(mouse.getI(), mouse.getJ());
					board.setBoard(quantumChessGame);
					
					checkUpgradePawn(() -> { 
						endTurn(); 
					});
				}
			);
			return true;
		}
			
		return false;
	}
	
	public boolean movePiece(ChessPiece source, Tuple<Integer, Integer> mouse, boolean status) {
		
		Move move = new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ());
		if (quantumChessGame.successProb(move) > 0) {

			quantumChessGame.move(new QuantumMove(getMoveProb(), move), status);
			
			Tuple<Integer, Integer> target = new Tuple<Integer, Integer>(mouse);
			if (board.isBoardFlipped()) {
				target = new Tuple<Integer, Integer> (7 - mouse.getI(), 7 - mouse.getJ());
			}
			
			Animation.getInstance().startAnimate(
				source, 
				target, 
				() -> {
					
					if (lastMovedPiece != null) {
						lastMovedPiece.setLastMoved(false);
					}
					lastMovedPiece = source;
					
					select(null);
					source.setLastMoved(true);
					
					source.setOnlyPosition(mouse.getI(), mouse.getJ());
					board.setBoard(quantumChessGame);
					
					checkUpgradePawn(() -> { 
						 endTurn(); 
					});
				}
			);
			return true;
		}
			
		return false;
	}
	@Override
	protected void initialPane() {
		pane = new HBox();
		detail = new QuantumChessDetail(this);
		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {				

				//Detail
				detail.update();
				detail.increseTime(now - timePrevious);
				
				// Board game
				Animation.getInstance().update(now);
				GameHolder.getInstance().update();
				board.paintComponentQuantum();
				
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
	}
	
}
