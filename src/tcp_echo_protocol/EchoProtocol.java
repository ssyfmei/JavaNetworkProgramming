package tcp_echo_protocol;

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
		this.logger = logger;
	}
	public static void handleEchoClient(Socket cSocket, Logger logger) {
		int recvMsgSize;
		int totalMsgSize = 0;
		byte[] bufferBytes = new byte[BUFSIZE];
		try {
			InputStream inputStream = cSocket.getInputStream();
			OutputStream outputStream = cSocket.getOutputStream();
			while((recvMsgSize = inputStream.read(bufferBytes)) != -1) {
				outputStream.write(bufferBytes, 0, recvMsgSize);
				totalMsgSize += recvMsgSize;
			}
			logger.info("Client " + cSocket.getRemoteSocketAddress() +", echoed"
					+ totalMsgSize + " bytes.");
		} catch (IOException e) {
			logger.log(Level.WARNING, "Exception in Echo Protocol", e);
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
