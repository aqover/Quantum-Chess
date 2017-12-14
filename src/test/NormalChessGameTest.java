package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.NormalChessGame;
import scene.gameBoard.shareObject.GameHolder;

class NormalChessGameTest {

	private NormalChessGame chess;
	
	public NormalChessGameTest() {
		chess = new NormalChessGame();
	}
	
	@Test
	public void tesConstructor() {
		Assertions.assertEquals(chess.getBoard().toString(""), "RNBQKBNR" + "!@#$%^&*" + "........" + "........" + "........" + "........" + "12345678" + "rnbqkbnr");
	}

	@Test
	public void testChessUndo() {
		String result = chess.getBoard().toString("");
		Assertions.assertFalse(chess.undo());
		Assertions.assertEquals(result, chess.getBoard().toString(""));
	}

	@Test
	public void testChessRedo() {
		String result = chess.getBoard().toString("");
		Assertions.assertFalse(chess.redo());
		Assertions.assertEquals(result, chess.getBoard().toString(""));
	}
	
	@Test
	public void testChessIsPawnUpgradeable() {
		Assertions.assertFalse(chess.isUpgradePawnAvailable());
	}
}
