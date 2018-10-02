package tcp_echo_server_threaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import tcp_echo_protocol.EchoProtocol;

public class TCPEchoServerExecutor {

	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Parameter: <Port> ");
		}
		int serverPort = Integer.parseInt(args[1]);

		ServerSocket serverSocket = new ServerSocket(serverPort);
		Logger logger = Logger.getLogger("pratical");
		
		Executor service = Executors.newCachedThreadPool();
		
		while(true) {
			Socket cSocket = serverSocket.accept();
			service.execute(new EchoProtocol(cSocket, logger));
			logger.info("Executing a new task.");
		}
	}
}