package library.socket;

import library.socket.TCPCommand.Command;

public interface TCPSocket {
	
	public int write(String msg, boolean savePacket);
	public default int write(Command cmd, String string) { return write(cmd.toString() + string, true); }
	public String read();
	public boolean isConnected();
	
	public default void sendLossPacket() {write(Command.TCP_FAIL.toString(), false); }
	
	public void destroy();
}
