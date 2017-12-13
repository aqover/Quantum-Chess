package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import library.socket.TCPCommand;
import library.socket.TCPListener;
import library.socket.TCPServer;

public class GQOController extends GOController implements TCPListener {
	
	private static final GQOController INSTANCE = new GQOController();

	private static QuantumChessOnlineController chessControl;
	private static final String MY_GAME_TYPE = "QUANTUM_CHESS";
	public GQOController() {
		super();
	}

	@Override
	@FXML
	public void startGame(MouseEvent arg0) {

		if (ip.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert ip:port server.");
			return;
		}

		if (name.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert a name.");
			return;
		}
		
		nameBlack = name.getText();
		String host = ip.getText().split(":")[0];
		String port = ip.getText().split(":")[1];

		createTCPClient(host, port, INSTANCE);
		
		waiting = new AcceptClient();
		waiting.addListener(() -> {linkReady(waiting);});
		waiting.start();
	}
	
	@Override
	@FXML
	public void startServer(MouseEvent arg0) {
		if (ip.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert ip:port server.");
			return;
		}
		
		if (name.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert a name.");
			return;
		}
		nameWhite = name.getText();		
		String port = ip.getText().split(":")[1];
		
		this.createTCPServer(port, INSTANCE);
		
		waiting = new AcceptClient();
		waiting.addListener(() -> {linkReady(waiting);});
		waiting.start();
	}

	@Override
	protected void createGameController() {
//		System.out.println(nameWhite + " " + nameBlack + " " + gameType);
		if (!gameType.equals(MY_GAME_TYPE))
		{
			socket.destroy();
			return;
		}
		
		chessControl = new QuantumChessOnlineController(socket instanceof TCPServer ? nameWhite : nameBlack, socket);
        chessControl.getOnlineDetail().setName(nameWhite, nameBlack);
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
	
	@Override
	public void OnReceived(TCPCommand cmd, String value) {
		switch(cmd)
		{
			case SET_NAME_PLAYER_WHITE:
				nameWhite = value; break;
			case SET_NAME_PLAYER_BLACK:
				nameBlack = value; break;
			case SET_GAME_TYPE:
				gameType = value; break;
			case GET_NAME_PLAYER_WHITE:
				socket.write(TCPCommand.SET_NAME_PLAYER_WHITE, nameWhite);
				break;
			case GET_NAME_PLAYER_BLACK:
				socket.write(TCPCommand.SET_NAME_PLAYER_BLACK, nameBlack);
				break;
			case GET_GAME_TYPE:
				socket.write(TCPCommand.SET_GAME_TYPE, MY_GAME_TYPE);
				break;
			default:
				break;
		}
		
		if (chessControl != null) {
			chessControl.OnReceived(cmd, value);
		}
	}
}
