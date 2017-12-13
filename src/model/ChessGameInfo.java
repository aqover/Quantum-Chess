package model;

public interface ChessGameInfo {

	// game result
	public static final int GAME_RESULT_DRAW = 0;
	public static final int GAME_RESULT_WHITE_WINS = 1;
	public static final int GAME_RESULT_BLACK_WINS = 2;
	public static final int GAME_RESULT_ONGOING = 3;
	
	// board size
	public static final int BOARD_SIZE = 8;

	public interface Piece {
		public static final char WHITE_ROOK = 'r';
		public static final char BLACK_ROOK = 'R';
		public static final char WHITE_KNIGHT = 'n';
		public static final char BLACK_KNIGHT = 'N';
		public static final char WHITE_BISHOP = 'b';
		public static final char BLACK_BISHOP = 'B';
		public static final char WHITE_KING = 'k';
		public static final char BLACK_KING = 'K';
		public static final char WHITE_QUEEN = 'q';
		public static final char BLACK_QUEEN = 'Q';
		public static final char WHITE_PAWN = 'p';
		public static final char BLACK_PAWN = 'P';
		
		public static final char EMPTY_SPACE = '.';
		
		public static final String WHITE_PAWN_LIST = "12345678";
		public static final String BLACK_PAWN_LIST = "!@#$%^&*";
		
		public static boolean isWhitePawn(char ch) {
			return WHITE_PAWN_LIST.indexOf(ch) != -1;
		}
		
		public static boolean isBlackPawn(char ch) {
			return BLACK_PAWN_LIST.indexOf(ch) != -1;
		}
		
		public static boolean isPawn(char ch) {
			return isWhitePawn(ch) || isBlackPawn(ch);
		}
		
		public static boolean isWhite(char ch) {
			return Character.isLowerCase(ch) || isWhitePawn(ch);
		}
		
		public static boolean isBlack(char ch) {
			return Character.isUpperCase(ch) || isBlackPawn(ch);
		}
	}
}
