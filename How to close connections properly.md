### How to close connections Properly

**Echo server case:**

```java
// Client
while (totalBytesRecvd < data.length) {
	if ((bytesRecvd = inStream.read(data, totalBytesRecvd,
			data.length - totalBytesRecvd)) == -1) {
		throw new SocketException("The connection was closed prematurely");
	}
	totalBytesRecvd += bytesRecvd;
}
socket.close();
```

When there's no data available, the inStream.read() call will be blocked. 

When the server socket has been closed, read() will return -1.

Client should call close() first, because the server don't know when to close properly. The client doesn't need to receive data in this case.

```java
// server
while((recvMsgSize = inStream.read(byteBuff)) != -1)
	outStream.write(byteBuff, 0, recvMsgSize);
clientSocket.close();
```



**Http request case:**

Client send request: Get url .....

The server send back the required resource.

The server should close the socket, since the client have no idea of when the transmission is over.

The server would call close() to indicate the end-of-file.





**Compression server case:**

A client sends data to a server, the server compresses the data and send it back. As in the first case, a server is not able to close the connection properly, because it doesn't know when the data transmission is over.

The client knows when to close the connection. After sending out the last byte of data, it should close the socket. Unfortunately, this solution is broken. After doing so, the client would fail to receive the last piece of compressed data.

It seems that, at this moment, the server should close the sending connection, informing the server to close the receiving endpoint. 

After the receiving endpoint is closed, the server send the last piece of bytes and close the sending port. Subsequently, the client close the receiving port after obtaining the last message from the server. The shutdownInput() and shutdownOutput() methods of Socket allow the I/O streams to be closed independently.



### Flush

```java
outputStream.finish(); // Flush bytes from stream
```

What is the exact semantics of this line?

"Flushes this output stream and forces any buffered output bytes to be written out. The general contract of **flush** is that calling it is an indication that, if any bytes previously written have been buffered by the implementation of the output stream, such bytes should immediately be written to their intended destination.

If the intended destination of this stream is an abstraction provided by the underlying operating system, for example a file, then flushing the stream guarantees only that bytes previously written to the stream are passed to the operating system for writing; it does not guarantee that they are actually written to a physical device such as a disk drive."



