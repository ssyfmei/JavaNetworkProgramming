package codes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

public class TCPEchoClient {

	public static void main(String[] args) throws IOException{
		
		if((args.length < 2) || (args.length > 3)) {
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
		}
		String server = args[0];
		byte[] data = args[1].getBytes();
		int portNum= (args.length == 3) ? Integer.parseInt(args[2]):7;
		
		// create socket identified by server IP and port number
		Socket socket = new Socket(server, portNum);
		System.out.println("Connecting to server");
		
		InputStream inStream = socket.getInputStream();
		OutputStream outStream = socket.getOutputStream();
		
		outStream.write(data);
		
		int totalBytesRecvd = 0;
		int bytesRecvd;
		while (totalBytesRecvd < data.length) {
			if ((bytesRecvd = inStream.read(data, totalBytesRecvd,
							     data.length - totalBytesRecvd)) == -1) {
				throw new SocketException("The connection was closed prematurely");
			}
			totalBytesRecvd += bytesRecvd;
		}
		
		System.out.println("Receive data: " + new String(data));
		
		socket.close();
	}
}
