package shutDownExample;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

public class SingleThreadedCompressServer {
	
	public static final int BUFSIZE = 512;
	
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Parameter(s): <Port>");
		}
		
		int servPort = Integer.parseInt(args[0]);
		ServerSocket serverSocket = new ServerSocket(servPort);
		
		while(true) {
			Socket cSocket = serverSocket.accept();
			
			InputStream inputStream = cSocket.getInputStream();
			GZIPOutputStream outputStream = new 
					GZIPOutputStream(cSocket.getOutputStream());
			
			int recvMsgSize;
			byte[] buffer = new byte[BUFSIZE];
			
			while((recvMsgSize = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, recvMsgSize);
			}
			// Finishes writing compressed data to the output stream without 
			// closing the underlying stream. Use this method when applying 
			// multiple filters in succession to the same output stream.
			outputStream.finish(); 	

			try {
				cSocket.close();
			}catch (IOException ex) {
				System.out.println("IOExceptin: " + ex.getMessage());
			}
		}
	}
	
}