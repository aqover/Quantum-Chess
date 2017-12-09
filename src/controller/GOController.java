package controller;

import java.io.IOException;
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

public class GOController extends Pane implements TCPListener {
	
	private static final GOController instance = new GOController();

	@FXML TextField ip;
	@FXML TextField name;
	@FXML Pane modal;
	@FXML Pane main;
	@FXML Label time;

	protected static final long timeout = 30000000000l; // 30 second
	protected static TCPSocket socket;
	protected AcceptClient waiting;
	
	private static BoardGameOnlineController chessControl;
	private static String MY_GAME_TYPE = "NORMAL_CHESS";
	
	protected static String nameWhite;
	protected static String nameBlack;
	protected static String gameType;

	public GOController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/scene/GameOnline.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
			System.out.print(ex);
		}
	}
	
	public void clear() {
		nameWhite = nameBlack = gameType = null;
		ip.setText("");
		name.setText("");
	}

	@FXML
	public void handlerBack(MouseEvent arg0) {
		SceneManager.setSceneSelectGame(true);
	}

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

		createTCPClient(host, port, instance);
		
		waiting = new AcceptClient();
		waiting.addListener(() -> {linkReady(waiting);});
		waiting.start();
	}

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
		
		this.createTCPServer(port, instance);
		
		waiting = new AcceptClient();
		waiting.addListener(() -> {linkReady(waiting);});
		waiting.start();
	}

	@FXML
	public void stopWaiting(MouseEvent arg0) {
		waiting.destroy();
	}

	public void showAlert(AlertType type, String msg) {
		Alert alert = new Alert(AlertType.NONE, msg, ButtonType.OK);
		alert.show();
	}

	private void setShowModal(boolean show) {
		main.setDisable(show);
		modal.setVisible(show);
	}
	
	public void linkReady(Thread thread) {
		if (((AcceptClient) thread).isSuccess())
		{
			createGameController();
		}
		else
		{
			Platform.runLater(()->{
				socket.destroy();
				socket = null;
			});			
		}
	}
	
	protected void createTCPClient(String host, String port, TCPListener instance) {
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
	}
	
	protected void createTCPServer(String port, TCPListener instance) {
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
	}

	protected void createGameController() {
		if (!gameType.equals(MY_GAME_TYPE))
		{
			socket.destroy();
			return;
		}
		
		chessControl = new BoardGameOnlineController(socket instanceof TCPServer ? nameWhite : nameBlack, socket);
        chessControl.getOnlineDetail().setName(nameWhite, nameBlack);
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
	
	protected class AcceptClient extends Thread {

		private long startTimeout;		
		private boolean isSuccess;
		private Runnable onDone;
		
		public boolean isSuccess() { return isSuccess; }

		public AcceptClient() { isSuccess = false;}
		public void addListener(Runnable onDone) {this.onDone = onDone;}

		@Override
		public final void run() {
			setShowModal(true);
			startTimeout = System.nanoTime();
			
			((Thread) socket).start();
			while ((System.nanoTime() - startTimeout) < timeout) {
				
				Platform.runLater(() -> {
					time.setText(String.format("%d", (timeout - (System.nanoTime() - startTimeout)) / 1000000000l));
				});
				
				if (socket.isConnected())
				{
					if (gameType == null) 
						socket.write(Command.GET_GAME_TYPE, "");
					if (nameWhite == null) 
						socket.write(Command.GET_NAME_PLAYER_WHITE, "");
					if (nameBlack == null) 
						socket.write(Command.GET_NAME_PLAYER_BLACK, "");
					
					if (gameType != null && nameWhite != null && nameBlack != null)
						break;
				}
				
				try { sleep(1000); } catch (InterruptedException e) { }
			}
			
			isSuccess = ((System.nanoTime() - startTimeout) < timeout);
			
			setShowModal(false);
			Platform.runLater(()->{
				onDone.run();
			});			
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			startTimeout = System.nanoTime() - timeout;
		}
		
	}

	@Override
	public void OnReceived(Command cmd, String value) {
		switch(cmd)
		{
			case SET_NAME_PLAYER_WHITE:
				nameWhite = value; break;
			case SET_NAME_PLAYER_BLACK:
				nameBlack = value; break;
			case SET_GAME_TYPE:
				gameType = value; break;
			case GET_NAME_PLAYER_WHITE:
				socket.write(Command.SET_NAME_PLAYER_WHITE, nameWhite);
				break;
			case GET_NAME_PLAYER_BLACK:
				socket.write(Command.SET_NAME_PLAYER_BLACK, nameBlack);
				break;
			case GET_GAME_TYPE:
				socket.write(Command.SET_GAME_TYPE, MY_GAME_TYPE);
				break;
			default:
				break;
		}
		
		if (chessControl != null) {
			chessControl.OnReceived(cmd, value);
		}
	}

	@Override
	public void OnSended(String msg) { }

	@Override
	public void OnClosed() { }

	@Override 
	public void OnConnected() { }
}
