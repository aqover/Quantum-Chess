package controller;

import java.util.ArrayList;

import helpper.Team;
import helpper.Tuple;
import input.InputUtility;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.control.Alert.AlertType;
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
	
	private long timePrevious;
	
	private Tuple<Integer, Integer> mouse;
	private Pieces piece, selectedPiece;
	private Team turnTeam;
	
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
		turnTeam = Team.A;
	} 
	
	public void startGame() {
		animationTimer.start();
		timePrevious = System.nanoTime();
	}
	
	public void endTurn() {
		turnTeam = (turnTeam == Team.A)? Team.B : Team.A;
	}
	
	public void update() {
		if (Animation.getInstance().isAnimating())
			return;
		
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
	
	public Team getTurnTeam() {
		return turnTeam;
	}

	private boolean movePiece(Pieces source, Tuple<Integer, Integer> mouse) {
		String moveString = String.format("%d%C%d%C", source.getI() + 1, source.getJ() + 'A', mouse.getI() + 1, mouse.getI() + 'A');
		if (normalChessGame.isMoveValid(moveString))
		{
			scene.gameBoard.shareObject.Animation.getInstance().startAnimate(source, mouse);
			return true;
		}			
		else
		{
			Alert alert = new Alert(AlertType.NONE, "The move is not valid, Please try again.", ButtonType.OK);
			alert.show();
		}
			
		return false;
	}

	private ArrayList<IRenderable> createPiecesTeamA() {
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
		for(IRenderable tmp: entity) ((Pieces) tmp).setTeam(Team.A);
		
		return entity;
	}
	
	private ArrayList<IRenderable> createPiecesTeamB() {
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
		for(IRenderable tmp: entity) ((Pieces) tmp).setTeam(Team.B);
		
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
