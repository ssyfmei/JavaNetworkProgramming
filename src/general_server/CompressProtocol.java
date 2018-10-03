package general_server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

public class CompressProtocol implements Runnable, ServerProtocol{
	
	public static final int BUFSIZE = 512;
	private Socket cSocket;
	private Logger logger;
	
	public CompressProtocol(Socket cSocket, Logger logger) {
		this.cSocket = cSocket;
		this.logger = logger;
	}
	
	public static void handleCompressClient(Socket cSocket, Logger logger) {
		try {
			InputStream inputStream = cSocket.getInputStream();
			GZIPOutputStream outputStream = new 
					GZIPOutputStream(cSocket.getOutputStream());
			
			int recvMsgSize;
			byte[] buffer = new byte[BUFSIZE];
			
			while((recvMsgSize = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, recvMsgSize);
			}
			outputStream.finish();
			
			logger.info("Client " + cSocket.getRemoteSocketAddress() + " finished");
			
		} catch (IOException ex) {
			logger.log(Level.WARNING, "Exception in Echo Protocol", ex);
		} finally {
			try{
				cSocket.close();
			}catch (Exception ex) {
				logger.info("IOExceptin: " + ex.getMessage());
			}
		}
	}

	@Override
	public void run() {
		handleCompressClient(cSocket, logger);
	}

}
