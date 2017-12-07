package scene.gameBoard.shareObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import helper.Tuple;
import javafx.scene.image.Image;
import model.piece.ChessPiece;

public class GameHolder {
	private static final GameHolder instance = new GameHolder();
	
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
	
	public static File pieceMove;
	
	public static double scoreA, scoreB;
	
	public static double size;
	public static double LineWidth;
		
	public static boolean isTurnEnded;
	
	private List<IRenderable> entity = new CopyOnWriteArrayList<IRenderable>();
	
	public static GameHolder getInstance() {
		return instance;
	}
	
	//Add or Get Entity
	public List<IRenderable> getEntity() {
		synchronized (this) {
			return entity;
		}
	}
	
	public ChessPiece getPiece(Tuple<Integer, Integer> target) {
		synchronized (this) {
			for(IRenderable en : entity) {
				if (en instanceof ChessPiece) {
					ChessPiece piece = (ChessPiece) en;
					if (piece.getI() == target.getI() && piece.getJ() == target.getJ()) {
						return piece;				
					}
				}
			}
			return null;
		}
	}
	
	public ChessPiece getPieceFromMouse(Tuple<Integer, Integer> target) {
		synchronized (this) {
			for(IRenderable en : entity) {
				if (en instanceof ChessPiece) {
					ChessPiece piece = (ChessPiece) en;
					if (piece.getI() == target.getI() && piece.getJ() == target.getJ()) {
						return piece;				
					}
				}
			}
			return null;
		}
	}
	
	public void addEntity(IRenderable entity) {
		synchronized (this) {
			this.entity.add(entity);
		}
	}
	
	public void addEntity(ArrayList<IRenderable> entity) {
		synchronized (this) {
			this.entity.addAll(entity);
		}
	}
	
	public void setEntity(ArrayList<IRenderable> entity) {
		synchronized (this) {	
			this.entity.clear();
			this.entity.addAll(entity);
			this.entity.sort(new Comparator<IRenderable>() {
				
				@Override
				public int compare(IRenderable a, IRenderable b) {
					return a.getZ() - b.getZ();
				}
			});
		}
	}

	//-----------------------------------------------------------------------------//
	static {
		loadResource();
	}
	
	public GameHolder() {
		size = 60;
		LineWidth = 5;
		scoreA = scoreB = 0;
	}
	
	public void update() {
		synchronized (this) {
	
			for(IRenderable tmp: entity)
			{
				if (tmp.isDestroyed())
					entity.remove(tmp);
			}
			System.gc();
		}
	}
	
	public static void loadResource() {
		bb = new Image("merida/bb.png");
		bk = new Image("merida/bk.png");
		bn = new Image("merida/bn.png");
		bp = new Image("merida/bp.png");
		bq = new Image("merida/bq.png");
		br = new Image("merida/br.png");
		wb = new Image("merida/wb.png");
		wk = new Image("merida/wk.png");
		wn = new Image("merida/wn.png");
		wp = new Image("merida/wp.png");
		wq = new Image("merida/wq.png");
		wr = new Image("merida/wr.png");
		
		bgDark = new Image("square_dark.jpg");
		bgLight = new Image("square_light.jpg");
		
		pieceMove = new File("res\\move.mp3");
	}
	
}
