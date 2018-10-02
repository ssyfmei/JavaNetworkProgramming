package multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPEchoServerThread {
	public static void main(String[] args) throws IOException{
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Parameter: <PortNum>");
		}
		
		int echoServPort = Integer.parseInt(args[0]);
		
		ServerSocket serverSocket = new ServerSocket(echoServPort);
		
		Logger logger = Logger.getLogger("pratical");
		
		while(true) {
			Socket clientSock = serverSocket.accept();
			Thread thread = new Thread(new EchoProtocol(clientSock, logger));
			thread.start();
			logger.info("Started a new Thread " + thread.getName());
		}
	}
}
