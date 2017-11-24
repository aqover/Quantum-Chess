package controller;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import helper.ThreadCompleteListener;

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

import library.socket.TCPServer;

public class GOController extends Pane implements ThreadCompleteListener {

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
	private TCPServer server;
	private AcceptClient waiting;

	public GOController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../scene/GameOnline.fxml"));
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
		// System.out.println(playerA.getText());

		if (ip.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert ip:port server.");
			return;
		}

		if (name.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert a name.");
			return;
		}

	}

	@FXML
	public void startServer(MouseEvent arg0) {
		if (ip.getText().equals("")) {
			showAlert(AlertType.WARNING, "Please, insert ip:port server.");
			return;
		}
		
		String port = ip.getText().split(":")[1];

		try {
			server = new TCPServer(Integer.parseInt(port));
		} catch (NumberFormatException ex) {
			showAlert(AlertType.WARNING, "The ip or port invalid.");
			return;
		} catch (Exception e) {
			System.out.println(e);
			showAlert(AlertType.WARNING, "System error, Contact the adminstrator.");
			return;
		}
		
		waiting = new AcceptClient();
		waiting.addListener(this);
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
	
	@Override
	public void notifyOfThreadComplete(Thread thread) {
		if (((AcceptClient) thread).isSuccess())
		{
			
		}
		else
		{
			server.close();
		}
	}

	private class AcceptClient extends Thread {

		private ExecutorService executor;
		private long startTimeout;
		
		private boolean isSuccess;

		private final Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

		public final void addListener(final ThreadCompleteListener listener) {
			listeners.add(listener);
		}

		private final void notifyListeners() {
			for (ThreadCompleteListener listener : listeners) {
				listener.notifyOfThreadComplete(this);
			}
		}
		
		public boolean isSuccess() {
			return isSuccess;
		}

		public AcceptClient() {
			executor = Executors.newSingleThreadExecutor();
			executor.submit(server);
			isSuccess = false;
		}

		@Override
		public final void run() {
			setShowModal(true);
			startTimeout = System.nanoTime();

			while (System.nanoTime() - startTimeout < timeout) {
				Platform.runLater(() -> {
					time.setText(String.format("%d", (timeout - (System.nanoTime() - startTimeout)) / 1000000000l));
				});
				
				try {
					sleep(1000);
				} catch (InterruptedException e) { }
			}
			
			if (System.nanoTime() - startTimeout < timeout)
			{
				isSuccess = true;
			}
			
			executor.shutdownNow();
			setShowModal(false);
			notifyListeners();
		}

		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			startTimeout = System.nanoTime() - timeout;	
			executor.shutdownNow();
		}
		
	}
}
