package library.socket;

import library.socket.TCPCommand.Command;

public interface TCPListener {
	public void OnReceived(Command cmd, String value);
	public void OnSended(String msg);
	public void OnClosed();
	public void OnConnected();
}
