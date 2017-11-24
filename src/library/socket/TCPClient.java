package library.socket;

import java.io.*;
import java.net.*;
import java.util.Set;

public class TCPClient implements Runnable, TCPSocket {	
	private Socket socket;
	private DataOutputStream bufferStream;
	private BufferedReader buffer;
	
	private String host;
	private int port;
		
	private Set<TCPListener> listeners;
	public void addListener(TCPListener listener) { listeners.add(listener); }
	
	public TCPClient(String host, int port) { this.host = host; this.port = port; }
	
	public void connect() {		
		try {
			socket = new Socket(host, port);
			bufferStream = new DataOutputStream(socket.getOutputStream());
			buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			for(TCPListener listener: listeners)
				listener.OnConnected();
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
			if (buffer.ready())
				return buffer.readLine();
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
		
		try {
			bufferStream.writeBytes(msg);
			for(TCPListener listener: listeners)
				listener.OnSended(msg);
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
		while(isConnected())
		{
			msg = read();
			if (msg != null)
			{
				for(TCPListener listener: listeners)
					listener.OnReceived(msg);
			}
		}
		
		close();
	}
	
	public void close() {
		if (socket == null) return;
		
		try { socket.close(); } catch (IOException e) { }
		
		socket = null;
		
		for(TCPListener listener: listeners)
			listener.OnClosed();
	}

}