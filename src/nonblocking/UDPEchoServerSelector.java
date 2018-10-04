package nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

public class UDPEchoServerSelector {
	
	private static final int TIMEOUT = 3000;
	
	private static final int ECHOMAX = 255;
	
	public static void main(String[] args) throws IOException {
		
		if(args.length != 1)
			throw new IllegalArgumentException("<Port>");
		
		int servPort = Integer.parseInt(args[0]);
		
		Selector selector = Selector.open();
		DatagramChannel channel = DatagramChannel.open();
		
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(servPort));
		channel.register(selector, SelectionKey.OP_READ, new ClientRecord());
		
		while(true) {
			// listen for task until time expires
			if(selector.select(TIMEOUT) == 0) {
				System.out.print(".");
				continue;
			}
			
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while(keyIter.hasNext()) {
				SelectionKey key = keyIter.next();
				
				if(key.isReadable()) {
					// Read data from socket channel to attached buffer
					handleRead(key);
				}
				if(key.isValid() && key.isWritable()) {
					handleWrite(key);
				}
				keyIter.remove();
			}
		}
	}
	
	public static void handleRead(SelectionKey key) throws IOException {
		DatagramChannel channel = (DatagramChannel) key.channel();
		ClientRecord record = (ClientRecord) key.attachment();
		record.buffer.clear();
		record.clientAddress = channel.receive(record.buffer); // not blocking
		
		if(record.clientAddress != null) {
			key.interestOps(SelectionKey.OP_WRITE);
		}
	}
	
	private static void handleWrite(SelectionKey key) throws IOException {
		DatagramChannel channel = (DatagramChannel) key.channel();
		ClientRecord record = (ClientRecord) key.attachment();
		record.buffer.flip();
		
		int bytesSend = channel.send(record.buffer, record.clientAddress);
		if(bytesSend != 0) {
			key.interestOps(SelectionKey.OP_READ);
		}
		
	}
	
	static class ClientRecord {
		public SocketAddress clientAddress;
		public ByteBuffer buffer = ByteBuffer.allocate(ECHOMAX);
	}
}