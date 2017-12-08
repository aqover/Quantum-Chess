package controller;

import java.io.IOException;

import controller.GOController.AcceptClient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import library.socket.TCPClient;
import library.socket.TCPCommand.Command;
import library.socket.TCPListener;
import library.socket.TCPServer;
import library.socket.TCPSocket;

public class GQOController extends GOController implements TCPListener {
	
	private static final GQOController instance = new GQOController();

	private static QuantumChessOnlineController chessControl;
	
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

		try {
			socket = new TCPClient(host, Integer.parseInt(port));
			((TCPClient) socket).addListener(instance);
		} catch (NumberFormatException ex) {
			showAlert(AlertType.WARNING, "The ip or port invalid.");
			return;
		} catch (Exception e) {
			System.out.println(e);
			showAlert(AlertType.WARNING, "System error, Contact the adminstrator.");
			return;
		}
		
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
		
		try {
			socket = new TCPServer(Integer.parseInt(port));			
			((TCPServer) socket).addListener(instance);

		} catch (NumberFormatException ex) {
			showAlert(AlertType.WARNING, "The ip or port invalid.");
			return;
			
		} catch (Exception e) {
			System.out.println(e);
			showAlert(AlertType.WARNING, "System error, Contact the adminstrator.");
			return;
		}
		
		waiting = new AcceptClient();
		waiting.addListener(() -> {linkReady(waiting);});
		waiting.start();
	}

	@Override
	public void createGameController() {
		chessControl = new QuantumChessOnlineController(socket instanceof TCPServer ? nameWhite : nameBlack, socket);
        chessControl.getOnlineDetail().setName(nameWhite, nameBlack);
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
	

	@Override
	public void OnReceived(Command cmd, String value) {
		if (cmd == Command.NAME_PLAYER) {
			super.OnReceived(cmd, value);
		}
		
		System.out.println(cmd + " " + value);
		
		if (chessControl != null) {
			chessControl.OnReceived(cmd, value);
		}
	}
}
