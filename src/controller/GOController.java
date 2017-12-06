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

	@FXML
	TextField ip;
	@FXML
	TextField name;
	@FXML
	Pane modal;
	@FXML
	Pane main;
	@FXML
	Label time;

	private static final long timeout = 30000000000l; // 30 second
	private static TCPSocket socket;
	private static BoardGameOnlineController chessControl;
	private AcceptClient waiting;
	
	private static String nameWhite;
	private static String nameBlack;

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

	@FXML
	public void handlerBack(MouseEvent arg0) {
		SceneManager.setSceneSelectGame();
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
			this.startGame(nameWhite, nameBlack);
		}
		else
		{
			Platform.runLater(()->{
				((TCPServer) socket).destroy();
				socket = null;
			});			
		}
	}

	public void startGame(String w, String b) {
		chessControl = new BoardGameOnlineController(socket);
        chessControl.getDetail().setName(w, b);
        chessControl.startGame();
		SceneManager.setScene(chessControl.getPane());
	}
	
	private class AcceptClient extends Thread {

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
					if (socket instanceof TCPClient)
					{
						if (nameWhite != null)
							break;
					}
					else if (socket instanceof TCPServer)
					{
						if (nameBlack != null)
							break;
					}						
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
		if (cmd == Command.NAME_PLAYER) {
			if (socket instanceof TCPServer)
				nameBlack = value;
			else if(socket instanceof TCPClient)
				nameWhite = value;
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
	public void OnConnected() {
		if (socket instanceof TCPServer)
			socket.write(Command.NAME_PLAYER.toString() + nameWhite);
		else if(socket instanceof TCPClient)
			socket.write(Command.NAME_PLAYER.toString() + nameBlack);
				
	}
}
