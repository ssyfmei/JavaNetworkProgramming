package nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TCPEchoClientNonblocking {

	public static void main(String[] args) throws IOException {
		
		if ((args.length < 2) || (args.length > 3)) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");
		
		String server = args[0];
		byte[] argument = args[1].getBytes();
		
		int port = (args.length == 3)? Integer.parseInt(args[2]):7;
		
		SocketChannel sockChan = SocketChannel.open();
		sockChan.configureBlocking(false);
		
		// will not block. we the connection is established. finishConnect() will return true.
		if(!sockChan.connect(new InetSocketAddress(server, port))) {
			while(!sockChan.finishConnect()) {
				System.out.print(".");
			}
		}
		
		ByteBuffer writeBuf = ByteBuffer.wrap(argument);
		ByteBuffer readBuf  = ByteBuffer.allocate(argument.length);
		int totalMsgSize = 0;
		int recvMsgSize;
		
		while(totalMsgSize < argument.length) {
			if(writeBuf.hasRemaining()) {
				sockChan.write(writeBuf);
			}
			// The call to read() does not block but rather 
			// returns 0 when no data is available to return.
			if ((recvMsgSize = sockChan.read(readBuf)) == -1) {
				throw new SocketException("Connection closed prematurely");
			}
			totalMsgSize += recvMsgSize;
			System.out.print(".");
		}
		
		sockChan.close();
	}
}
