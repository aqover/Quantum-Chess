package controller;

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
		normalChessGame = new NormalChessGame();
		initialPane();

		board.setBoard(normalChessGame);
		selectedPiece = null;		
		disable = false;
	} 
	
	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() { }
	
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
			ChessPiece piece = scene.gameBoard.shareObject.GameHolder.getInstance().getPieceFromMouse(mouse);
			
			if(selectedPiece != null)
			{
				mouse = InputUtility.getMousePosition();
				piece = scene.gameBoard.shareObject.GameHolder.getInstance().getPieceFromMouse(mouse);
				
				if(selectedPiece != null)
				{
					if (piece == null || (piece != null && selectedPiece.getTeam() != piece.getTeam()))
							if(movePiece(selectedPiece, mouse))
								endTurn();			

					select(null);
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
				
				System.gc();
			}
			else
			{
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
			
			if (normalChessGame.isMoveValid(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ())))
			{
				normalChessGame.move(new Move(source.getI(), source.getJ(), mouse.getI(), mouse.getJ()));
				scene.gameBoard.shareObject.Animation.getInstance().startAnimate(source, mouse);
				return true;
			}			
			else
			{
				
				System.out.println("ERROR");
				SceneManager.showMessage("The move is not valid", () -> {});
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
