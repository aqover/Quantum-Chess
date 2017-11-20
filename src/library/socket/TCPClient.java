package library.socket;

import java.io.*;
import java.net.*;

public class TCPClient {	
	Socket socket;
	DataOutputStream bufferStream;
	BufferedReader buffer;
	
	public TCPClient(String host, int port) throws UnknownHostException, IOException, SecurityException, IllegalArgumentException {
		Socket socket = new Socket(host, port);
		bufferStream = new DataOutputStream(socket.getOutputStream());
		buffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public boolean isConnected() {
		return (socket != null && socket.isConnected());
	}
	
	public String read()
	{
		if (socket == null)
			return null;
		
		try {
			if (buffer.ready())
				return buffer.readLine();
		} catch (IOException e) { 
			socket = null;
		}
		
		return null;
	}
	
	public int write(String msg)
	{
		if (socket == null)
			return 0;
		
		try {
			bufferStream.writeBytes(msg);
			return msg.length();
		}
		catch (IOException ex) { 
			socket = null;
		}
		
		return 0;
	}
	
	
	public void close() throws IOException {
		socket.close();
	}
}