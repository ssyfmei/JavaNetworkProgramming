package multithread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPEchoServerPool {

	public static void main(String[] args) throws IOException{
		
		if(args.length != 2) {
			throw new IllegalArgumentException("Parameter(s): <Port> <Threads>");
		}
		
		int echoServPort = Integer.parseInt(args[0]);
		int threadPoolSize = Integer.parseInt(args[1]);
		
		final ServerSocket serverSocket = new ServerSocket(echoServPort);
		final Logger logger = Logger.getLogger("pratical");
		
		for (int i = 0; i < threadPoolSize; i++) {
			Thread thread = new Thread() {
				public void run() {
					while(true) {
						try {
							Socket cSocket = serverSocket.accept();
							EchoProtocol.handleEchoClient(cSocket, logger);
						} catch (IOException ex) {
							logger.log(Level.WARNING, "Client accept failed", ex);
						}
						
					}
				}
			};
			thread.start();
			logger.info("Started a new Thread " + thread.getName());
		}
		
		
	}

}
