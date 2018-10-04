package nonblocking;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


// P 152
public class EchoSelectorProtocol implements TCPProtocol{

	private int bufSize; // Size of I/O buffer

	public EchoSelectorProtocol(int bufSize) {
		this.bufSize = bufSize;
	}

	@Override
	public void handleAccept(SelectionKey key) throws IOException {
		SocketChannel sockChan = ((ServerSocketChannel) key.channel()).accept();
		sockChan.configureBlocking(false); // Must be nonblocking to register
		
		// Register the selector with new channel for read and attach byte buffer
		sockChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
	}

	@Override
	public void handleRead(SelectionKey key) throws IOException {
		SocketChannel sockChan = (SocketChannel) key.channel();
		ByteBuffer buf = (ByteBuffer) key.attachment();
		
		// nonblocking
		long bytesRead = sockChan.read(buf);
		if (bytesRead == -1) {
			sockChan.close();
		} else if(bytesRead > 0) {
			// Indicate via key that reading/writing are both of interest now.
			key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
		}
	}

	@Override
	public void handleWrite(SelectionKey key) throws IOException {
		// channel enables writing now
		// key is valid: connection not closed
		ByteBuffer buf = (ByteBuffer) key.attachment();
		buf.flip(); // ready to write
		SocketChannel sockChan = (SocketChannel) key.channel();
		
		sockChan.write(buf);
		if(!buf.hasRemaining()) {
			key.interestOps(SelectionKey.OP_READ);
		}
		buf.compact();
	}
}
