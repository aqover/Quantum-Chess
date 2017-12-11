package library.socket;

import library.socket.TCPCommand;

public interface TCPListener {
	public void OnReceived(TCPCommand cmd, String value);
	public void OnSended(String msg);
	public void OnClosed();
	public void OnConnected();
}
