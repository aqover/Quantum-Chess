package shareObject;

import javafx.scene.image.Image;
import view.Team;
import view.pieces.Bishop;
import view.pieces.King;
import view.pieces.Knight;
import view.pieces.Pawn;
import view.pieces.Pieces;
import view.pieces.Queen;
import view.pieces.Rook;

public class RenderableHolder {
	private static final RenderableHolder instance = new RenderableHolder();
	
	public static Image bb;
	public static Image bk;
	public static Image bn;
	public static Image bp;
	public static Image bq;
	public static Image br;
	public static Image wb;
	public static Image wk;
	public static Image wn;
	public static Image wp;
	public static Image wq;
	public static Image wr;
	
	public static Image bgDark;
	public static Image bgLight;
	
	public static double size;
	
	private IRenderable[][] stateGame = new IRenderable[8][8];
	
	public static RenderableHolder getInstance() {
		return instance;
	}
	
	public IRenderable[][] getStateGame() {
		return stateGame;
	}

	public void setStateGame(IRenderable[][] stateGame) {
		this.stateGame = stateGame;
	}

	static {
		loadResource();
	}
	
	public RenderableHolder() {
		size = 60;
		initPieces();
	}
	
	public void update() {
		for (int i=0;i<8;i++) 
			for(int j=0;j<8;j++) {
				if(stateGame[i][j].isDestroyed())
				{
					stateGame[i][j] = null;
					System.gc();
				}
			}
	}
	
	public static void loadResource() {
		bb = new Image("file:res/merida/bb.png");
		bk = new Image("file:res/merida/bk.png");
		bn = new Image("file:res/merida/bn.png");
		bp = new Image("file:res/merida/bp.png");
		bq = new Image("file:res/merida/bq.png");
		br = new Image("file:res/merida/br.png");
		wb = new Image("file:res/merida/wb.png");
		wk = new Image("file:res/merida/wk.png");
		wn = new Image("file:res/merida/wn.png");
		wp = new Image("file:res/merida/wp.png");
		wq = new Image("file:res/merida/wq.png");
		wr = new Image("file:res/merida/wr.png");
		
		bgDark = new Image("file:res/square_dark.jpg");
		bgLight = new Image("file:res/square_light.jpg");
	}
	
	private void initPieces() {
		//Team B
		stateGame[0][0] = new Rook(0, 0);
		stateGame[0][1] = new Knight(0, 1);
		stateGame[0][2] = new Bishop(0, 2);
		stateGame[0][3] = new Queen(0, 3);
		stateGame[0][4] = new King(0, 4);
		stateGame[0][5] = new Bishop(0, 5);
		stateGame[0][6] = new Knight(0, 6);
		stateGame[0][7] = new Rook(0, 7);
		for(int i=0;i<8;i++)
		{
			stateGame[1][i] = new Pawn(1, i);
			((Pieces) stateGame[1][i]).setTeam(Team.B);
			((Pieces) stateGame[0][i]).setTeam(Team.B);
		}
		
		
		//Team A
		stateGame[7][0] = new Rook(7, 0);
		stateGame[7][1] = new Knight(7, 1);
		stateGame[7][2] = new Bishop(7, 2);
		stateGame[7][3] = new Queen(7, 3);
		stateGame[7][4] = new King(7, 4);
		stateGame[7][5] = new Bishop(7, 5);
		stateGame[7][6] = new Knight(7, 6);
		stateGame[7][7] = new Rook(7, 7);
		for(int i=0;i<8;i++)
		{
			stateGame[6][i] = new Pawn(6, i);
			((Pieces) stateGame[6][i]).setTeam(Team.A);
			((Pieces) stateGame[7][i]).setTeam(Team.A);
		}
	}
}
