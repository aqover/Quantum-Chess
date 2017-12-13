package library.socket;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javafx.application.Platform;
import library.socket.TCPCommand;

public class TCPClient extends Thread implements Runnable, TCPSocket {	
	private static final long KEEP_ALIVE_INTERVAL = 1000000000l;
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
	public void run() {

		connect();
		
		String msg;
		TCPCommand cmd;
		int len = 0;
		long prevTime = System.nanoTime();
		long nowTime;
		while(isConnected())
		{
			nowTime = System.nanoTime();
			if ((nowTime - prevTime) > KEEP_ALIVE_INTERVAL)
			{
				write(TCPCommand.TCP_KEEPALIVE, "");
				prevTime = nowTime;
			}
			
			msg = read();
			if (msg != null)
			{				
				totalPacket++;
				try {
					len = Integer.parseInt(msg.substring(0, 3));
					if (msg.length() < 3+len)
						throw new Exception("Losing Packet.");
					
					cmd = TCPCommand.valueOf(Integer.parseInt(msg.substring(3, 5)));
					if (cmd == TCPCommand.TCP_FAIL)
					{
						lossPacket++;
						write(lastPacket, true);
					}
					else if (cmd == TCPCommand.TCP_KEEPALIVE)
						continue;
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

	@Override
	public String read() {
		try {
			if (socket.getInputStream().available() > 0)
			{
				byte[] buf = new byte[socket.getInputStream().available()];
				if (socket.getInputStream().read(buf) <= 0)
					return null;
				
				return new String(buf, "UTF-16");
			}
		} catch (Exception e) { 
			close(false, true);
		}
		
		return null;
	}
	
	@Override
	public int write(String msg, boolean savePacket) {
		if (savePacket) lastPacket = msg;
		
		String text = String.format("%03d%s", msg.length(), msg);
		try {
			socket.getOutputStream().write(text.getBytes(StandardCharsets.UTF_16));
			OnSended(text);
			return msg.length();
		}
		catch (Exception ex) {
			close(false, true);
		}
		
		return 0;
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

	private void OnReceived(TCPCommand cmd, String msg) {
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