package scene.gameBoard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import library.socket.TCPCommand.Command;
import library.socket.TCPSocket;

public class Chat extends VBox {

	private static int CHAT_LIMIT = 20;
	
	protected List<ChatField> chatFieldData = new ArrayList<>();

	protected String username;
	protected TCPSocket socket;
	
	protected Label label = new Label();
	protected TextField inputMessage;
	protected VBox messageField;
	
	public static class ChatField {

		protected final String user;
		protected final String message;		
		protected final long time;
		
		protected Text text;
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
			this.text = new Text(this.user + ": " + this.message);
			this.textflow = new TextFlow(text);
			this.flowpane = new FlowPane();
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
		
		label.setText("Chat");
		label.setPrefWidth(200);
		label.setAlignment(Pos.CENTER);
		label.setPadding(new Insets(20, 0, 20, 0));
		
		messageField = new VBox();
		VBox.setVgrow(messageField, Priority.ALWAYS);
		
		inputMessage = new TextField();
		inputMessage.setPromptText("Message (Enter to send)");

		inputMessage.setOnKeyReleased(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent evt) {
				
				if (evt.getCode().equals(KeyCode.ENTER)) {

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
		});
		
		this.setPadding(new Insets(10, 5, 10, 5));
		
		getChildren().add(label);
		getChildren().add(messageField);
		getChildren().add(inputMessage);
	}
	
	public String getUserName() { return username; }	
	public void setUserName(String username) { this.username = username; }
	
	public TCPSocket getSocket() { return socket; }
	public void setSocket(TCPSocket socket) { this.socket = socket; }
	
	@Override
	public void requestFocus() {
		inputMessage.requestFocus();
	}
	
}
