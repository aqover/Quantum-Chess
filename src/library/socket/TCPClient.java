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
	private int totalPacket;
	private int lossPacket;
	private String lastPacket;
		
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
			close(false, true);
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
			close(false, true);
		}
		
		return null;
	}
	
	@Override
	public int write(String msg)
	{
		if (socket == null) return 0;
		
		lastPacket = msg;
		
		String text = String.format("%03d%s", msg.length(), msg);
		try {
			socket.getOutputStream().write(text.getBytes(StandardCharsets.UTF_16));
			OnSended(text);
			return msg.length();
		}
		catch (IOException ex) { }
		
		return 0;
	}
	
	@Override
	public void run() {

		connect();
		
		String msg;
		Command cmd;
		int len = 0;
		while(isConnected())
		{			
			msg = read();
			if (msg != null)
			{				
				totalPacket++;
				try {
					len = Integer.parseInt(msg.substring(0, 3));
					if (msg.length() < 3+len)
						throw new Exception("Losing Packet.");
					
					cmd = Command.valueOf(Integer.parseInt(msg.substring(3, 5)));
					if (cmd == Command.TCP_FAIL)
					{
						lossPacket++;
						write(lastPacket);
					}
					else
						OnReceived(cmd, msg.substring(5, 3+len));
				}
				catch (Exception ex) {
					sendLossPacket();
					continue;
				}
			}
		}
		
		close(true, false);
	}
	
	public void close(boolean runListener, boolean isException) {
		if (socket != null) try { socket.close(); } catch (IOException e) { }
		
		socket = null;
		if (runListener)
			OnClosed();
	}

	@Override
	public void destroy() {
		close(false, false);
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
	
	public int getTotalPacket() {
		return totalPacket;
	}

	public int getLossPacket() {
		return lossPacket;
	}

}