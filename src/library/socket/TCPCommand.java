package library.socket;

public enum TCPCommand {
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
	NAME_PLAYER(15),
	SET_UPGRADE_PAWN(16),
	SEND_TEXT(17),
	SET_GAME_TYPE(18),
	GET_GAME_TYPE(19),
	SET_NAME_PLAYER_WHITE(20),
	GET_NAME_PLAYER_WHITE(21),
	SET_NAME_PLAYER_BLACK(22),
	GET_NAME_PLAYER_BLACK(23),
	TCP_KEEPALIVE(98),
	TCP_FAIL(99);
	
	private final int ID;
	TCPCommand(int id) {
		this.ID = id;
	}
	
	public String toString() { return String.format("%02d", ID); }
	public int getValue() { return ID; }
	
	public static TCPCommand valueOf(int command)
	{
		for(TCPCommand c: TCPCommand.values())
			if (command == c.getValue())
				return c;
		return null;
	}
}
