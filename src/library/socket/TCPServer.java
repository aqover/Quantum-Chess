package library.socket;

import java.io.*;
import java.net.*;
import java.util.Set;

import library.socket.TCPCommand.Command;

public class TCPServer implements Runnable, TCPSocket {
	private ServerSocket server;
	private Socket client;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	
	private int port;
	
	private Set<TCPListener> listeners;
	public void addListener(TCPListener listener) { listeners.add(listener); }
	
	public TCPServer(int port) { this.port = port;}
	
	public boolean isClientConnected() {
		return (client != null && client.isConnected());
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			client = server.accept();
			
			inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outToClient = new DataOutputStream(client.getOutputStream());
			
			for(TCPListener listener: listeners)
				listener.OnConnected();
		}
		catch (Exception ex) { 
			close();
		}
		
		String msg;
		Command cmd;
		int len;
		while(isClientConnected())
		{
			msg = read();
			if (msg != null)
			{
				for(TCPListener listener: listeners)
				{
					len = Integer.getInteger(msg.substring(0, 3));
					cmd = Command.valueOf(Integer.getInteger(msg.substring(3, 5)));
					listener.OnReceived(cmd, msg.substring(5, len - 5));
				}
			}
		}
		
		close();
	}
	
	@Override
	public String read() {
		if (client == null) return null;
		
		try {
			return inFromClient.readLine();
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
			outToClient.writeBytes(text);
			for(TCPListener listener: listeners)
				listener.OnSended(text);
			return msg.length();
		}
		catch (IOException ex) { 
			close();
		}
		
		return 0;
	}
		
	public void close() {
		if (server == null || client == null) return;
		
		try { client.close(); } catch (IOException ex) { }
		try { server.close(); } catch (IOException ex) { }
		
		client = null;
		server = null;
		
		for(TCPListener listener: listeners)
			listener.OnClosed();
	}
}