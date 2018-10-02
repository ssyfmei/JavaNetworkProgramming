package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class VoteMulticastReceiver {
	private static final int BUFSIZE = 32;
	
	public static void main(String[] args) throws IOException {
		if(args.length != 2) {
			throw new IllegalArgumentException("Parameter(s): <Multicast Address> <Port>");
		}
		InetAddress address = InetAddress.getByName(args[0]);
		if(!address.isMulticastAddress()) {
			throw new IllegalArgumentException("Not a multicast address");
		}
		
		int port = Integer.parseInt(args[1]);
		MulticastSocket socket = new MulticastSocket(port);
		
		/*
		 * A MulticastSocket is a (UDP) DatagramSocket, with additional capabilities 
		 * for joining "groups" of other multicast hosts on the internet.
		 * A multicast group is specified by a class D IP address and 
		 * by a standard UDP port number. 
		 * Class D IP addresses are in the range 224.0.0.0 to 239.255.255.255, 
		 * inclusive. The address 224.0.0.0 is reserved and should not be used.
		 */
		socket.joinGroup(address);
		
		DatagramPacket packet = new DatagramPacket(new byte[BUFSIZE], BUFSIZE);
		socket.receive(packet);
		
		System.out.println("received " + packet.getLength() + " bytes");
		System.out.println(packet.getData());
		
		socket.close();
	}
}