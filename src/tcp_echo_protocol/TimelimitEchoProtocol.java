package tcp_echo_protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

// limit the total running time of this task
public class TimelimitEchoProtocol implements Runnable{
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
		int recvMsgSize;
		int totalMsgSize = 10;
		byte[] bufferBytes = new byte[BUFSIZE];
		long endTime = System.currentTimeMillis() + timelimit;
		int timeBoundMillis = timelimit;
		
		/*
		 * With this option set to a non-zero timeout, a read() call on the InputStream 
		 * associated with this Socket will block for only this amount of time. 
		 * If the timeout expires, a java.net.SocketTimeoutException is raised, though the Socket is still valid. 
		 * The option must be enabled prior to entering the blocking operation to have effect. 
		 * The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.
		 */
		try {
			cSocket.setSoTimeout(timeBoundMillis);
			
			InputStream inputStream = cSocket.getInputStream();
			OutputStream outputStream = cSocket.getOutputStream();
			
			while((timeBoundMillis > 0) &&
					((recvMsgSize = inputStream.read(bufferBytes)) != -1)) {
				outputStream.write(bufferBytes, 0, recvMsgSize);
				totalMsgSize += recvMsgSize;
				timeBoundMillis = (int)(endTime - System.currentTimeMillis());
				cSocket.setSoTimeout(timeBoundMillis);
			}
			
			logger.info("Client " + cSocket.getRemoteSocketAddress() +
					", echoed" + totalMsgSize + " bytes.");
			
		} catch (IOException e) {
			logger.log(Level.WARNING, "Exception in Protocol", e);
		} finally {
			try {
				cSocket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void run() {
		handleEchoClient(cSocket, logger);
	}
}