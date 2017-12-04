package library.socket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import library.socket.TCPCommand.Command;

public class TCPClient extends Thread implements Runnable, TCPSocket {	
	private static Socket socket;
	
	private String host;
	private int port;
		
	private ArrayList<TCPListener> listeners = new ArrayList<TCPListener>();
	public void addListener(TCPListener listener) { listeners.add(listener); }
	
	public TCPClient(String host, int port) { this.host = host; this.port = port; }
	
	public void connect() {		
		try {
			socket = new Socket(host, port);			
			OnConnected();
		}
		catch (Exception ex)
		{
			close();
		}		
	}
	
	public boolean isConnected() {
		return (socket != null && socket.isConnected());
	}
	
	@Override
	public String read()
	{
		if (socket == null)
			return null;
		
		try {
			if (socket.getInputStream().available() > 0)
			{
				byte[] buf = new byte[socket.getInputStream().available()];
				if (socket.getInputStream().read(buf) <= 0)
					return null;
				
				return new String(buf, "UTF-16");
			}
		} catch (IOException e) { 
			close();
		}
		
		return null;
	}
	
	@Override
	public int write(String msg)
	{
		if (socket == null)
			return 0;
		
		String text = String.format("%03d%s", msg.length(), msg);
		try {
			socket.getOutputStream().write(text.getBytes(StandardCharsets.UTF_16));
			OnSended(text);
			return msg.length();
		}
		catch (IOException ex) { 
			close();
		}
		
		return 0;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		connect();
		
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
	
	public void close() {
		if (socket != null) try { socket.close(); } catch (IOException e) { }
		
		socket = null;
		OnClosed();
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
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