package general_server;

import java.io.IOException;

public class TCPServerExecutor {
	
	private ServerProtocol protocol;
	
	public TCPServerExecutor(ServerProtocol protocol) {
		this.protocol = protocol;
	}
	
	public void handleRequest(int serverPort) throws IOException {
		
	}
}
