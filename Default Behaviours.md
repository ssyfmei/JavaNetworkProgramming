### Default Behaviors

1. Keep-Alive

If no data is transferred for a while,  a probe message would be sent to the other endpoint. If the endpoint is alive and well, it sends back an acknowledgement. 

After sending a few probe messages without getting acknowledgement, the socket would be closed, eliciting an exception on the next operation on the Socket.

The socket would keep alive is keep-alive option is enabled.

```java
// Socket
boolean getKeepAlive()
void setKeepAlive(boolean on)
```

By default, keep-alive is disabled. Call the setKeepAlive() method with true to enable
keep-alive.



2. Send and Receive Buffer Size

After a Socket or DatagramSocket is created, the OS would allocate buffers to hold data. The size can be set and accessed through certain methods.

```java
// Socket, DatagramSocket
int getReceiveBufferSize()
int getSendBufferSize()
void setReceiveBufferSize(int size)
void setSendBufferSize(int size)
```



```java
// ServerSocket: 
int getReceiveBufferSize()
void setReceiveBufferSize(int size)
```

Set or access buffer size of a socket accepted. The send buffer size should be set after the socket is created.



3. Timeout

```java
// Socket, ServerSocket, DatagramSocket
int getSoTimeout()
void setSoTimeout(int timeout)
```

With this option set to a non-zero timeout, a read() call on the Input Stream associated with this Socket will block for only this amount of time. 

If the timeout expires, a java.net.SocketTimeoutException is raised, though the Socket is still valid.The option must be enabled prior to entering the blocking operation to have effect. The timeout must be > 0. A timeout of zero is interpreted as an infinite timeout.



4. Address Reuse

   ```java
   // Socket, ServerSocket, DatagramSocket:
   boolean getReuseAddress()
   void setReuseAddress(boolean on)
   // read and set reuse address permissions
   ```

   Case 1. To bind multiple sockets to the same socket address

   Case 2.  To reuse a socket, avoiding the overhead of closing and creating a socket.

   ​

5. Eliminating Buffering Delay

   ```java
   // Socket
   boolean getTcpNoDelay()
   void setTcpNoDelay(boolean on)
   // set/read buffering delay provided by TCP.
   ```

   ​

6. Urgent Data

   ​

7. Lingering after close

   ​

8. Broadcast Permission

   ​

9. Traffic Class

   ​

10. Performance-Based Protocol Selection



