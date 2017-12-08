package controller;

public class GQFController extends GFController {

	public GQFController() {
		super();
	}

	@Override
	protected void createGameController() {
		// TODO Auto-generated method stub
		chessControl = new QuantumChessController();
        ((QuantumChessController) chessControl).getQuantumDetail().setName(playerA.getText(), playerB.getText());
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
	
	
}
