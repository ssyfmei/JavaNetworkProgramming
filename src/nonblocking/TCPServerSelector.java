package nonblocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {

	private static final int BUFSIZE = 256;
	private static final int TIMEOUT = 3000;

	public static void main(String[] args) throws IOException {

		if (args.length < 1) {
			throw new IllegalArgumentException();
		}

		Selector selector = Selector.open();

		for (String arg : args) {
			ServerSocketChannel listnChannel = ServerSocketChannel.open();
			listnChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)));
			listnChannel.configureBlocking(false);
			listnChannel.register(selector, SelectionKey.OP_ACCEPT); // multiple channels register on one selector
		}

		TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);

		while (true) {
			// Wait for some channel to be ready (or timeout)
			if (selector.select(TIMEOUT) == 0) { // returns # of ready chans
				System.out.print(".");
				continue;
			}
			Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();

			// single threaded execution
			while (keyIter.hasNext()) {
				SelectionKey key = keyIter.next();
				if (key.isAcceptable()) {
					protocol.handleAccept(key);
				}
				if (key.isReadable()) {
					protocol.handleRead(key);
				}
				if (key.isValid() && key.isWritable()) {
					protocol.handleWrite(key);
				}
				keyIter.remove();
			}
		}
	}
}