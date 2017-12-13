package scene.gameBoard.shareObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import helper.Tuple;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import model.piece.ChessPiece;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class GameHolder {
	private static final GameHolder INSTANCE = new GameHolder();
	
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
	
	public static Clip pieceMove;
	public static Clip pieceDead;
		
	public static double size;
		
	public static boolean isTurnEnded;
	
	private List<IRenderable> entity = new CopyOnWriteArrayList<IRenderable>();
	
	public static GameHolder getInstance() {
		return INSTANCE;
	}
	
	//Add or Get Entity
	public List<IRenderable> getEntity() {
		synchronized (this) {
			entity.sort(new Comparator<IRenderable>() {
				@Override
				public int compare(IRenderable ir1, IRenderable ir2) {
					return ir1.getZ() - ir2.getZ();
				}
			});
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
	}
	
	public void update() {
		synchronized (this) {
	
			for(IRenderable tmp: entity)
			{
				if (tmp.isDestroyed())
				{
					if (pieceDead != null) {
						pieceDead.setMicrosecondPosition(0);
						pieceDead.start();
					}
					entity.remove(tmp);
				}
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

		try {
			URL url = GameHolder.class.getClassLoader().getResource("move.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			
			pieceMove = AudioSystem.getClip();
			pieceMove.open(audioInputStream);
			
			url = GameHolder.class.getClassLoader().getResource("dead.wav");
			audioInputStream = AudioSystem.getAudioInputStream(url);			
			pieceDead = AudioSystem.getClip();			
			pieceDead.open(audioInputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
