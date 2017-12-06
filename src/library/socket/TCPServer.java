package library.socket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javafx.application.Platform;
import library.socket.TCPCommand.Command;

public class TCPServer extends Thread implements TCPSocket {
	
	private static ServerSocket server;
	private static Socket client;
	
	private int port;
	
	private ArrayList<TCPListener> listeners = new ArrayList<TCPListener>();
	public void addListener(TCPListener listener) { listeners.add(listener); }
	
	public TCPServer(int port) {
		this.port = port;
	}
	
	public boolean isConnected() {
		return (client != null && client.isConnected());
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			client = server.accept();
			
			OnConnected();
		}
		catch (IOException ex) {
			System.out.println("IOException " + ex);
		}
		catch (Exception ex) { 
			close();
		}
				
		String msg;
		Command cmd;
		int len;
		while(isConnected())
		{
			msg = read();
			if (msg != null)
			{
				len = Integer.parseInt(msg.substring(0, 3));
				cmd = Command.valueOf(Integer.parseInt(msg.substring(3, 5)));
				OnReceived(cmd, msg.substring(5, 3+len));
			}
		}
		
		close();
	}
	
	@Override
	public String read() {
		if (client == null) return null;
		
		try {
			if (client.getInputStream().available() > 0)
			{
				byte[] buf = new byte[client.getInputStream().available()];
				if (client.getInputStream().read(buf) <= 0)
					return null;
				
				return new String(buf, "UTF-16");
			}
		}
		catch (IOException ex) { 
			close();
		}
		
		return null;
	}	
	
	@Override
	public int write(String msg) {
		if (client == null) return 0;
		
		String text = String.format("%03d%s", msg.length(), msg);
		try {
			client.getOutputStream().write(text.getBytes(StandardCharsets.UTF_16));
			OnSended(text);
			return msg.length();
		}
		catch (IOException ex) { 
			close();
		}
		
		return 0;
	}
			
	public void close() {
		if (client != null) try { client.close(); } catch (IOException ex) { }
		if (server != null) try { server.close(); } catch (IOException ex) { }
		
		client = null;
		server = null;
		
		OnClosed();
	}

	@Override
	public void destroy() {
		close();
	}

	private void OnConnected() {
		for(TCPListener listener: listeners) 
			Platform.runLater(()->{
				listener.OnConnected();
			});
	}

	private void OnSended(String text) {
		for(TCPListener listener: listeners) 
			Platform.runLater(()->{
				listener.OnSended(text);
			});
	}

	private void OnReceived(Command cmd, String msg) {
		for(TCPListener listener: listeners)
			Platform.runLater(()->{
				listener.OnReceived(cmd, msg);
			});
	}

	private void OnClosed() {
		for(TCPListener listener: listeners) 
			Platform.runLater(()->{
				listener.OnClosed();
			});
	}
}