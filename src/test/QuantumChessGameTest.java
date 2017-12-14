package test;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.QuantumChessGame;
import scene.gameBoard.shareObject.GameHolder;

class QuantumChessGameTest {

	private QuantumChessGame chess;
	
	public QuantumChessGameTest() {
		chess = new QuantumChessGame();
	}
	
	@Test
	public void tesConstructor() {
		Assertions.assertEquals(chess.getDisplayBoard().toString(""), "RNBQKBNR" + "!@#$%^&*" + "........" + "........" + "........" + "........" + "12345678" + "rnbqkbnr");
	}

	@Test
	public void testMeasure() {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {
				String result = chess.getDisplayBoard().toString("");
				chess.measure(new ArrayList<>(Arrays.asList(i)), new ArrayList<>(Arrays.asList(j)));
				Assertions.assertEquals(result, chess.getDisplayBoard().toString(""));				
			}
		}
	}

	@Test
	public void testChessIsPawnUpgradeable() {
		Assertions.assertFalse(chess.isUpgradePawnAvailable());
	}
	
	@Test
	public void testIsDead() {
		for (int i = 0; i < 8; ++i) {
			for (int j = 0; j < 8; ++j) {	
				Assertions.assertFalse(chess.isDead(chess.getDisplayBoard().getAt(i, j)));
			}
		}
	}
	
	@Test
	public void testGameResult() {
		Assertions.assertEquals(chess.getGameResult(), QuantumChessGame.GAME_RESULT_ONGOING);
	}
}
