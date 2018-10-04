package blocking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {
	
	private static final int ECHOMAX = 255;  // Maximun size of datagram
	
	public static void main(String[] args) throws IOException{
		
		if(args.length != 1) {
			throw new IllegalArgumentException("Parameter: <PortNum>");
		}
		
		int serverPort = Integer.parseInt(args[0]);
		
		DatagramSocket socket = new DatagramSocket(serverPort); // This method blocks until a datagram is received.
		DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);
		
		while(true) {
			socket.receive(packet);
			System.out.println("Handling client at" + packet.getAddress().getHostAddress()
													+ " on port " + packet.getPort());
			socket.send(packet);
			packet.setLength(ECHOMAX);
		}
	}
}