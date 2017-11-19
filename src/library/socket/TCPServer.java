package library.socket;

import java.io.*;
import java.net.*;

public class TCPServer implements Runnable {
	ServerSocket server;
	Socket client;
	
	BufferedReader inFromClient;
	DataOutputStream outToClient;
	
	public TCPServer(int port) throws Exception {		
		server = new ServerSocket(port);
	}
	
	public boolean isClientConneted() {
		return (client != null && client.isConnected());
	}
	
	@Override
	public void run() {
		try {
			client = server.accept();
			
			inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			outToClient = new DataOutputStream(client.getOutputStream());
		}
		catch (Exception ex) { 
			
		}		
	}
	
	public int write(String msg) {
		if (client == null) 
			return 0;
		
		try {
			outToClient.writeBytes(msg);
			return msg.length();
		}
		catch (IOException ex) { 
			client = null;
		}
		return 0;
	}
	
	public String read() {
		if (client == null)
			return null;
		
		try {
			return inFromClient.readLine();
		}
		catch (IOException ex) { 
			client = null;
		}
		return null;
	}
	
	public void close() {
		try { server.close(); } catch (IOException ex) { }
		try { client.close(); } catch (IOException ex) { }
	}
}