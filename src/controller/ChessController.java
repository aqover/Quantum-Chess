package controller;

import java.util.ArrayList;

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
import scene.gameBoard.shareObject.IRenderable;
import scene.gameBoard.view.pieces.Bishop;
import scene.gameBoard.view.pieces.King;
import scene.gameBoard.view.pieces.Knight;
import scene.gameBoard.view.pieces.Pawn;
import scene.gameBoard.view.pieces.Pieces;
import scene.gameBoard.view.pieces.Queen;
import scene.gameBoard.view.pieces.Rook;

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
		scene.gameBoard.shareObject.GameHolder.getInstance().addEntity(createPiecesTeamA());
		scene.gameBoard.shareObject.GameHolder.getInstance().addEntity(createPiecesTeamB());
		
		selectedPiece = piece = null;
		
		disable = false;
	} 
	
	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() {}
	
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

	private ArrayList<IRenderable> createPiecesTeamA() {
		
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();
		entity.add(new Rook(7, 0));
		entity.add(new Knight(7, 1));
		entity.add(new Bishop(7, 2));
		entity.add(new Queen(7, 3));
		entity.add(new King(7, 4));
		entity.add(new Bishop(7, 5));
		entity.add(new Knight(7, 6));
		entity.add(new Rook(7, 7));
		
		for(int i=0; i<8; i++) entity.add(new Pawn(6, i));
		for(IRenderable tmp: entity) ((Pieces) tmp).setTeam(Team.PLAYER_WHITE);
		
		return entity;
	}

	private ArrayList<IRenderable> createPiecesTeamB() {
		ArrayList<IRenderable> entity = new ArrayList<IRenderable>();
		entity.add(new Rook(0, 0));
		entity.add(new Knight(0, 1));
		entity.add(new Bishop(0, 2));
		entity.add(new Queen(0, 3));
		entity.add(new King(0, 4));
		entity.add(new Bishop(0, 5));
		entity.add(new Knight(0, 6));
		entity.add(new Rook(0, 7));
		
		for(int i=0; i<8; i++) entity.add(new Pawn(1, i));
		for(IRenderable tmp: entity) ((Pieces) tmp).setTeam(Team.PLAYER_BLACK);
		
		return entity;
	}
	
	
	private void initialPane() {
		pane = new HBox();
		detail = new ChessDetail(this);
		board = new ChessBoard();		
		animationTimer = new AnimationTimer() {
			public void handle(long now) {
				ArrayList<IRenderable> entity = GameHolder.getInstance().getEntity();
				for(IRenderable tmp: entity)
					((Pieces) tmp).update();
				
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
