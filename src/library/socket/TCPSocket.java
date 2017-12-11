package library.socket;

import library.socket.TCPCommand;

public interface TCPSocket {
	
	public int write(String msg, boolean savePacket);
	public default int write(TCPCommand cmd, String string) { return write(cmd.toString() + string, true); }
	public String read();
	public boolean isConnected();
	
	public default void sendLossPacket() {write(TCPCommand.TCP_FAIL.toString(), false); }
	
	public void destroy();
}
