package scene.gameBoard.view;

import javafx.scene.canvas.GraphicsContext;
import scene.gameBoard.shareObject.GameHolder;

public class ChessBackGround extends Entity {
	
	public ChessBackGround() {
		super();
		z = 0;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				gc.drawImage(
					((i+j)%2 == 0)? GameHolder.bgDark: GameHolder.bgLight, 
					i * GameHolder.size, 
					j * GameHolder.size,
					GameHolder.size,
					GameHolder.size
				);
			}
		}

	}

}
