package codes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPEchoServer {
	
	private static final int BUFSIZE = 32;

	public static void main(String[] args) throws IOException{
		
		if(args.length != 1)
			throw new IllegalArgumentException("Parameter: <PortNum>");
		
		int servPort = Integer.parseInt(args[0]);
		
		ServerSocket serverSocket = new ServerSocket(servPort);
		
		int recvMsgSize;
		byte[] byteBuff = new byte[BUFSIZE];
		
		while(true) {
			Socket clientSocket = serverSocket.accept();
			
			// clientSocket.getPort() returns the port of the client
			System.out.println("Handling client at " + 
				clientSocket.getInetAddress().getHostAddress() + " on port " +
					clientSocket.getPort());
			
			InputStream inStream = clientSocket.getInputStream();
			OutputStream outStream = clientSocket.getOutputStream();
			
			while((recvMsgSize = inStream.read(byteBuff)) != -1)
				outStream.write(byteBuff, 0, recvMsgSize);
			
			clientSocket.close();
		}
	}

}
