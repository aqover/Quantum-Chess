package controller;

import helper.InputUtility;
import helper.Team;
import helper.Tuple;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
import model.ChessBoard.Move;
import model.NormalChessGame;
import scene.gameBoard.ChessBoard;
import scene.gameBoard.ChessDetail;
import scene.gameBoard.shareObject.Animation;
import scene.gameBoard.shareObject.GameHolder;
import scene.gameBoard.view.pieces.Pieces;

public class ChessController {
	
	private HBox pane;
	private NormalChessGame normalChessGame;
	private ChessDetail detail;
	private ChessBoard board;
	private AnimationTimer animationTimer;
	private Tuple<Integer, Integer> mouse;
	
	private boolean disable;
	
	private long timePrevious;
	
	private Pieces piece, selectedPiece;
	
	public HBox getPane() {
		return pane;
	}
		
	public ChessDetail getDetail() {
		return detail;
	}

	public ChessController() {
		normalChessGame = new NormalChessGame();
		initialPane();
		board.setBoard(normalChessGame.getBoard().getBoard());
		selectedPiece = piece = null;		
		disable = false;
	} 
	
	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() { }
	
	public void update() {
		if (Animation.getInstance().isAnimating())
			return;
		
		synchronized (this) {
			
			if (disable) {
				return;
			}
			
			if (InputUtility.isMouseLeftClicked())
			{
				mouse = InputUtility.getMousePosition();
				piece = scene.gameBoard.shareObject.GameHolder.getInstance().getPieceFromMouse(mouse);
				
				if(selectedPiece != null)
				{
					if (piece == null || (piece != null && selectedPiece.getTeam() != piece.getTeam()))
							if(movePiece(selectedPiece, mouse))
								endTurn();			
					
					selectedPiece.setSelected(false);
					selectedPiece = null;
					System.gc();
				}
				else
				{
					if(piece != null)
					{
						if (getTurnTeam() != piece.getTeam())
							return;
						
						selectedPiece = piece;
						selectedPiece.setSelected(true);
					}
				}
			}			
		}
	}
	
	public Team getTurnTeam() {
		return normalChessGame.getTurn();
	}

	private boolean movePiece(Pieces source, Tuple<Integer, Integer> mouse) {
		synchronized (this) {
			if (normalChessGame.isMoveValid(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ())))
			{
				normalChessGame.move(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()));
				scene.gameBoard.shareObject.Animation.getInstance().startAnimate(source, mouse);
				return true;
			}			
			else
			{
					
				disable = true;
				
				Alert alert = new Alert(AlertType.NONE, "The move is not valid, Please try again.", ButtonType.OK);
				alert.setOnHidden(e -> { disable = false; });
				alert.show();
			}
		}
			
		return false;
	}

	private void initialPane() {
		pane = new HBox();
		detail = new ChessDetail(this);
		board = new ChessBoard();		
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
				update();
				InputUtility.update();
				
				timePrevious = now;
			}
		};
		
		pane.getChildren().add(detail);
		pane.getChildren().add(board);
	}
	
}
