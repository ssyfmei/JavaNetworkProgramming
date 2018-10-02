package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/*
 * Unicasting multiple copies over a single network connection 
 * wastes bandwidth by sending the same information multiple times.
 */
public class VoteMulticastSender {
	
	public static final int CANDIDATEID = 475;
	
	public static void main(String[] args) throws IOException {
	
		if((args.length < 2) || (args.length > 3)) {
			throw new IllegalArgumentException(
				"Parameter(s): <Multicast Address> <Port> [<TTL>]");
		}
		
		InetAddress destAddr = InetAddress.getByName(args[0]);
		if(!destAddr.isMulticastAddress()) {
			throw new IllegalArgumentException("Not a multicast address");
		}
		
		int destPort = Integer.parseInt(args[1]);
		int TTL = (args.length == 3)? Integer.parseInt(args[2]) : 1;
		
		MulticastSocket socket = new MulticastSocket();
		socket.setTimeToLive(TTL);
		
//		VoteMsgCoder coder = new VoteMsgTextCoder();
//		VoteMsg vote = new VoteMsg(true, true, CANDIDATEID, 1000001L);
		
		// send a datagram
//		byte[] msg = coder.toWire(vote);
		byte[] msg = new byte[] {1,11,111,127,126};
		DatagramPacket message = new DatagramPacket(msg, msg.length, destAddr, destPort);
		System.out.println("Sending encoded request"+msg.length+" bytes");
//		System.out.println(vote);
		socket.send(message);
		
		socket.close();
	}
	
	
	
}
