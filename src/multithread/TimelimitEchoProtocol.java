package multithread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TimelimitEchoProtocol implements Runnable {
	private static final int BUFSIZE = 32;
	private static final String TIMELIMIT = "10000"; 
	
	private static int timelimit;
	private Socket cSocket;
	private Logger logger;
	
	public TimelimitEchoProtocol(Socket cSocket, Logger logger) {
		this.cSocket = cSocket;
		this.logger = logger;
		//  the first argument is the key to look up and the second argument is 
		//  a default value to return if the key cannot be found
		timelimit = Integer.parseInt(System.getProperty("Timelimit", TIMELIMIT));
	}
	
	public static void handleEchoClient(Socket cSocket, Logger logger) {
		
		try {
			InputStream inputStream = cSocket.getInputStream();
			OutputStream outputStream = cSocket.getOutputStream();
			int recvMsgSize;
			int totalBytesEchoed = 0;
			byte[] echoBuffer = new byte[BUFSIZE];
			long endTime = System.currentTimeMillis() + timelimit;
			int timeBoundMillis = timelimit;
			
			cSocket.setSoTimeout(timeBoundMillis);
			
			while((timeBoundMillis > 0) && 
				  ((recvMsgSize = inputStream.read(echoBuffer)) != -1)) {
				outputStream.write(echoBuffer, 0, recvMsgSize);
				totalBytesEchoed += recvMsgSize;
				timeBoundMillis = (int) (endTime - System.currentTimeMillis());
				cSocket.setSoTimeout(timeBoundMillis);
			}
			logger.info("Client " + cSocket.getRemoteSocketAddress() +
					", echoed" + totalBytesEchoed + " bytes.");
			
		} catch (IOException e) {
			logger.log(Level.WARNING, "Exception in Protocol", e);
		}
		
	}

	@Override
	public void run() {
		handleEchoClient(this.cSocket, this.logger);
	}
	
}
