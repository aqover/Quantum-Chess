package library.socket;

import library.socket.TCPCommand.Command;

public interface TCPSocket {
	
	public int write(String msg);
	public default int write(Command cmd, String string) { return write(cmd.toString() + string); }
	public String read();
	public boolean isConnected();
	
	public default void sendLossPacket() {write(Command.TCP_FAIL.toString()); }
	
	public void destroy();
}
