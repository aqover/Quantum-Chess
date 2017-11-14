package ui;

import java.util.ArrayList;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import ui.pieces.Bishop;
import ui.pieces.King;
import ui.pieces.Knight;
import ui.pieces.Pawn;
import ui.pieces.Pieces;
import ui.pieces.Queen;
import ui.pieces.Rook;
import ui.pieces.Team;

public class Board extends GridPane {
	
	private double size = 120;
	
	private Tile[][] tiles = new Tile[8][8];
	
	public Pieces[][] stateStatus = new Pieces[8][8];;
	
	public Board () {		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{
				tiles[i][j] = new Tile();
				tiles[i][j].setSize(size);
				tiles[i][j].drawBackgroud((i+j)%2);
				this.add(tiles[i][j], j, i);
			}
		
//		widthProperty().addListener(evt -> drawBackground());
//        heightProperty().addListener(evt -> drawBackground());
		initPieces();
	}
	
	private void drawBackground() {
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				tiles[i][j].drawBackgroud((i+j)%2);
	}
	
	private void initPieces() {
		//Team A
		stateStatus[0][0] = new Rook(tiles[0][0]);
		stateStatus[0][1] = new Knight(tiles[0][1]);
		stateStatus[0][2] = new Bishop(tiles[0][2]);
		stateStatus[0][3] = new Queen(tiles[0][3]);
		stateStatus[0][4] = new King(tiles[0][4]);
		stateStatus[0][5] = new Bishop(tiles[0][5]);
		stateStatus[0][6] = new Knight(tiles[0][6]);
		stateStatus[0][7] = new Rook(tiles[0][7]);
		for(int i=0;i<8;i++)
		{
			stateStatus[1][i] = new Pawn(tiles[1][i]);
			stateStatus[1][i].setTeam(Team.A);
			stateStatus[0][i].setTeam(Team.A);
			stateStatus[0][i].draw();
			stateStatus[1][i].draw();
		}
		
		
		//Team B
		stateStatus[7][0] = new Rook(tiles[7][0]);
		stateStatus[7][1] = new Knight(tiles[7][1]);
		stateStatus[7][2] = new Bishop(tiles[7][2]);
		stateStatus[7][3] = new Queen(tiles[7][3]);
		stateStatus[7][4] = new King(tiles[7][4]);
		stateStatus[7][5] = new Bishop(tiles[7][5]);
		stateStatus[7][6] = new Knight(tiles[7][6]);
		stateStatus[7][7] = new Rook(tiles[7][7]);
		for(int i=0;i<8;i++)
		{
			stateStatus[6][i] = new Pawn(tiles[6][i]);
			stateStatus[6][i].setTeam(Team.B);
			stateStatus[7][i].setTeam(Team.B);
			stateStatus[6][i].draw();
			stateStatus[7][i].draw();
		}
	}
}
