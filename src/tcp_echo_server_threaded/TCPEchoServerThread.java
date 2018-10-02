package tcp_echo_server_threaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import tcp_echo_protocol.EchoProtocol;

public class TCPEchoServerThread {

	public static void main(String[] args) throws IOException{
		if(args.length != 1) {
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}
		int serverPort = Integer.parseInt(args[1]);
		
		ServerSocket serverSocket = new ServerSocket(serverPort);
		Logger logger = Logger.getLogger("pratical");
		
		while(true) {
			Socket cSocket = serverSocket.accept();
			Thread thread  = new Thread(new EchoProtocol(cSocket,logger));
			thread.start();
			logger.info("Started a new Thread " + thread.getName());
		}
	}
}