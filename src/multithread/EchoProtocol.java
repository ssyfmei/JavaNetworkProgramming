package multithread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {
	private static final int BUFSIZE = 32;
	private Socket cSocket;
	private Logger logger;
	
	public EchoProtocol(Socket cSocket, Logger logger) {
		this.cSocket = cSocket;
		this.logger  = logger;
	}
	
	public static void handleEchoClient(Socket cSocket, Logger logger) {
		try {
			InputStream inputStream = cSocket.getInputStream();
			OutputStream outputStream = cSocket.getOutputStream();
			
			int recvMsgSize;
			int totalBytesEchoed = 0;
			byte[] echoBuffer = new byte[BUFSIZE];
			
			while((recvMsgSize = inputStream.read(echoBuffer)) != -1) {
				outputStream.write(echoBuffer, 0, recvMsgSize);
				totalBytesEchoed += recvMsgSize;
			}
			
			logger.info("Client " + cSocket.getRemoteSocketAddress() +", echoed"
					+ totalBytesEchoed + " bytes.");
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in echo protocol", ex);
		} finally {
			try {
				cSocket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void run() {
		handleEchoClient(cSocket, logger);
	}
}
