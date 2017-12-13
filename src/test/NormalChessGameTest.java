package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.NormalChessGame;
import model.ChessBoard;

class NormalChessGameTest {

	private NormalChessGame chess;
	
	public NormalChessGameTest() {
		chess = new NormalChessGame();
	}
	
	@Test
	void tesConstructor() {
		Assertions.assertEquals(chess.getBoard().toString(""), "RNBQKBNR" + "!@#$%^&*" + "........" + "........" + "........" + "........" + "12345678" + "rnbqkbnr");
	}
}
