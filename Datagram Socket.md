# Datagram
**DatagramPacket**



```java
//	DatagramPacket: Creation
DatagramPacket(byte[ ] data, int length)
DatagramPacket(byte[ ] data, int offset, int length)
DatagramPacket(byte[ ] data, int length, InetAddress remoteAddr, int remotePort)
DatagramPacket(byte[ ] data, int offset, int length, InetAddress remoteAddr, int remotePort)
DatagramPacket(byte[ ] data, int length, SocketAddress sockAddr)
DatagramPacket(byte[ ] data, int offset, int length, SocketAddress sockAddr)
    
//  DatagramPacket: Addressing
InetAddress getAddress()
void setAddress(InetAddress address)
int getPort()
void setPort(int port)
SocketAddress getSocketAddress()
void setSocketAddress(SocketAddress sockAddr)
    
//	DatagramPacket: Handling data
int getLength()
void setLength(int length)
int getOffset()
byte[ ] getData()
void setData(byte[ ] data)
void setData(byte[ ] buffer, int offset, int length)
```



**DatagramSocket**

```java
//	DatagramSocket: Creation
DatagramSocket()
DatagramSocket(int localPort)
DatagramSocket(int localPort, InetAddress localAddr)
    
//	DatagramSocket: Connection and Closing
void connect(InetAddress remoteAddr, int remotePort)
void connect(SocketAddress remoteSockAddr)
void disconnect()
void close()
    
//	DatagramSocket: Addressing
InetAddress getInetAddress()
int getPort()
SocketAddress getRemoteSocketAddress()
InetAddress getLocalAddress()
int getLocalPort()
SocketAddress getLocalSocketAddress()
    
//	DatagramSocket: Sending and receiving
void send(DatagramPacket packet)
void receive(DatagramPacket packet)

//	DatagramSocket: Options
int getSoTimeout()
void setSoTimeout(int timeoutMillis)
```

