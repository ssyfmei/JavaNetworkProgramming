package shutDownExample;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CompressClient {
	
	public static final int BUFSIZE = 256;
	
	public static void main(String[] args) throws IOException {
		
		if (args.length != 3) {
			throw new IllegalArgumentException("Parameter(s): <Server> <Port> <File>");
		}
		
		String server = args[0];
		int servPort = Integer.parseInt(args[1]);
		String file  = args[2];
		
		FileInputStream fileInputStream = new FileInputStream(file);
		FileOutputStream fileOutputStream= new FileOutputStream(file + ".gz");
		
		Socket socket = new Socket(server, servPort);
		sendBytes(socket, fileInputStream);
		
		InputStream socketIn = socket.getInputStream();
		
		int recvMsgSize;
		byte[] buffer = new byte[BUFSIZE];
		
		while((recvMsgSize = socketIn.read(buffer)) != -1) {
			fileOutputStream.write(buffer, 0, recvMsgSize);
		}
		// socket.shutdownInput();
		socket.close();
		fileInputStream.close();
		fileOutputStream.close();
	}
	
	private static void sendBytes(Socket socket, InputStream fileIn)
		throws IOException {
		
		OutputStream outputStream = socket.getOutputStream();
		int recvMsgSize;
		byte[] buffer = new byte[BUFSIZE];
		while((recvMsgSize = fileIn.read(buffer)) != -1) {
			outputStream.write(buffer, 0, recvMsgSize);
		}
		socket.shutdownOutput();
	}
}
