package scene.gameBoard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import library.socket.TCPCommand.Command;
import library.socket.TCPSocket;

public class Chat extends AnchorPane {

	private static int CHAT_LIMIT = 20;
	
	protected List<ChatField> chatFieldData = new ArrayList<>();

	protected String username;
	protected TCPSocket socket;
	
	@FXML Button clear;
	@FXML Button send;
	@FXML TextField inputMessage;
	@FXML VBox messageField;
		
	public static class ChatField {

		protected final String user;
		protected final String message;		
		protected final long time;
		
		protected Label text;
		protected TextFlow textflow;
		protected Pane flowpane;
		
		public ChatField(String msg) {
			
			String[] data = msg.split("\\|");
			this.user = data[0];
			this.message = data[1];
			this.time = Long.parseLong(data[2]);
			
			createPane();
		}
		
		public ChatField(String user, String message, long time) {
			this.user = user;
			this.message = message;
			this.time = time;
			
			createPane();
		}

		protected void createPane() {
			this.text = new Label();
			this.text.setText(this.user);
			this.text.setPadding(new Insets(5, 5, 5, 5));
			this.text.setStyle("-fx-background-color: #81D4FA;");
			this.textflow = new TextFlow(new Text(this.message));
			this.textflow.setPadding(new Insets(5, 5, 5, 5));
			this.flowpane = new FlowPane();
			this.flowpane.setMaxWidth(200.0);
			this.flowpane.setMinHeight(20.0);
			this.flowpane.getChildren().add(this.text);
			this.flowpane.getChildren().add(this.textflow);
		}
		
		public String encode() { 
			return this.user + "|" + this.message + "|" + this.time;
		}
		
		public String toString() {
			return encode();
		}
		
		public String getUser() { return this.user; }
		public String getMessage() { return this.message; }
		public long getTime() { return this.time; }
		
		public Pane getPane() { return this.flowpane; }
		
		public static long compare(ChatField cf1, ChatField cf2) {
			return cf1.getTime() - cf2.getTime();
		}
	}
	
	public void insert(ChatField chatField) {
		synchronized (chatFieldData) {
			int idx = chatFieldData.size();
			while (idx > 0 && ChatField.compare(chatField, chatFieldData.get(idx-1)) < 0) {
				idx -= 1;
			}
			chatFieldData.add(idx, chatField);
		}
		
		update();
	}
	
	public void update() {
		synchronized (chatFieldData) {
			messageField.getChildren().clear();
			for (int i = chatFieldData.size()-1, j = 0; i >= 0 && j < CHAT_LIMIT; --i, ++j) {
				messageField.getChildren().add(chatFieldData.get(i).getPane());
			}
		}
	}
	
	public Chat() {
		super();
		
		this.username = null;
		this.socket = null;
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Chat.fxml"));
			loader.setRoot(this);
			loader.setController(this);
			loader.load();
		} catch (IOException ex) {
            System.out.print(ex);
        }

		inputMessage.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent evt) {
				
				if (evt.getCode().equals(KeyCode.ENTER)) {
					sendMessage();
				}
			}
		});
	}
	
	public String getUserName() { return username; }	
	public void setUserName(String username) { this.username = username; }
	
	public TCPSocket getSocket() { return socket; }
	public void setSocket(TCPSocket socket) { this.socket = socket; }
	
	@Override
	public void requestFocus() {
		inputMessage.requestFocus();
	}
	
	@FXML
	protected void handleClear(MouseEvent event) {
		inputMessage.clear();
    }
	
	@FXML
	protected void handleSend(MouseEvent event) {
		sendMessage();
    }
	
	private void sendMessage() {
		// create new chatField
		String message = inputMessage.getText().trim();
		if (message == "") return;
		
		ChatField chatField = new ChatField(getUserName(), message, (new Date()).getTime());
		insert(chatField);
		
		// send message
		TCPSocket socket = getSocket();
		if (socket != null && socket.isConnected()) {
			socket.write(Command.SEND_TEXT, chatField.encode());
		}
		
		// clear input field
		inputMessage.clear();
	}
	
}
