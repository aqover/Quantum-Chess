package library.socket;

public interface TCPCommand {
	enum Command {
		MOVE (1),
		SET_BOARD_VERSION(2),
		GET_BOARD_VERSION(3),
		SET_TIME_PLAYER_WHITE(4),
		GET_TIME_PLAYER_WHITE(5),
		SET_TIME_PLAYER_BLACK(6),
		GET_TIME_PLAYER_BLACK(7),
		BLACK_DRAW(8),
		BLACK_SURRENDER(9),
		BLACK_END_TURN(10),
		WHITE_DRAW(11),
		WHITE_SURRENDER(12),
		WHITE_END_TURN(13),
		GAME_RESULT(14),
		NAME_PLAYER(15);
		
		private final int id;
		Command(int id) {
			this.id = id;
		}
		
		public String toString() { return String.format("%02d", id); }
		public int getValue() { return id; }
		
		public static Command valueOf(int command)
		{
			for(Command c: Command.values())
				if (command == c.getValue())
					return c;
			return null;
		}
	}
	
	enum GameResult {
		BLACK_WIN(1),
		WHITE_WIN(2),
		DRAW(5),
		CONTINUE(6);
		
		private final int id;
		GameResult(int id) {
			this.id = id;
		}
		
		public String toString() { return String.format("%01d", id); }
		public int getValue() { return id; }
	}
	
	enum TCPResponde {
		SUCCESS(1),
		FAILED(2);
		
		private final int id;
		TCPResponde(int id) {
			this.id = id;
		}
		
		public String toString() { return String.format("%01d", id); }
		public int getValue() { return id; }
		public static TCPResponde valueOf(int result)
		{
			for(TCPResponde c: TCPResponde.values())
				if (result == c.getValue())
					return c;
			return null;
		}
	}
}
