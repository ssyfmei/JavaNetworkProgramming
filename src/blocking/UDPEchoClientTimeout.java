package blocking;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPEchoClientTimeout {
	
	private static final int TIMEOUT = 3000;
	private static final int MAXTRIES = 5;   // Maximum attempts
	
	public static void main(String[] args) throws IOException{
		
		if((args.length < 2) || (args.length > 3)) {
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
		}
		InetAddress serverAddress = InetAddress.getByName(args[0]);
		byte[] dataBytes = args[1].getBytes();
		int serverPort = (args.length == 3) ? Integer.parseInt(args[2]):7;
		
		DatagramSocket socket = new DatagramSocket();
		
		// a call to receive() for this DatagramSocket will block for only this amount of time.
		socket.setSoTimeout(TIMEOUT);
		
		DatagramPacket sendPacket = new DatagramPacket(dataBytes, 
				dataBytes.length, serverAddress, serverPort);
		
		DatagramPacket recvPacket = 
				new DatagramPacket(new byte[dataBytes.length], dataBytes.length);
		
		int tries = 0;
		boolean receivedResponse = false;
		do {
			socket.send(sendPacket);
			try {
				socket.receive(recvPacket);
				
				if(!recvPacket.getAddress().equals(serverAddress)) {
					throw new IOException("Received Packet from unknown source");
				}
				receivedResponse = true;
			} catch (InterruptedIOException e) {
				tries += 1;
				System.out.println("Time out, " + (MAXTRIES - tries) + " more tries...");
			}
		} while ((!receivedResponse) && (tries < MAXTRIES));
		
		if(receivedResponse) {
			// getData returns byte[]
			// new String(byte[] byteArray)
			System.out.println("Received: " + new String(recvPacket.getData())); 
		} else {
			System.out.println("No reponse after multiply tries");
		}
		socket.close();
	}
}
