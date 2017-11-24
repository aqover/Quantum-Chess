package library.socket;

public interface TCPListener {
	public void OnReceived(String msg);
	
	public void OnSended(String msg);
}
