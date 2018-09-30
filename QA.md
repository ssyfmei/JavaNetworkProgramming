# Questions and Answers
#### 1 Socket Address

1. Why NetworkInterface.getNetworkInterfaces uses Enumeration rather than Iterator?

   Answer: When using code, comply to the return value type.

   When developing code, always use iterator in preference to Enumeration.

   ​

2. What are some basic Java objects to interact with Networks

   ```java
   import java.net.InetAddress;  // Inet4Address, Inet6Address
   import java.net.NetworkInterface;

   // NetworkInterface: Creating, getting information
   static Enumeration<NetworkInterface> getNetworkInterfaces()
   static NetworkInterface getByInetAddress(InetAddress addr)
   static NetworkInterface getByName(String name)
   Enumeration<InetAddress> getInetAddresses()
   String getName()
   String getDisplayName()
     
   // InetAddress: Creating and accessing
   static InetAddress[ ] getAllByName(String host)
   static InetAddress getByName(String host)
   static InetAddress getLocalHost()
   byte[] getAddress()

   //  InetAddress: String representations
   String toString()
   String getHostAddress()
   String getHostName()
   String getCanonicalHostName()
     
     
   //  InetAddress: Testing properties
   boolean isAnyLocalAddress()
   boolean isLinkLocalAddress()
   boolean isLoopbackAddress()
   boolean isMulticastAddress()
   boolean isMCGlobal()
   boolean isMCLinkLocal()
   boolean isMCNodeLocal()
   boolean isMCOrgLocal()
   boolean isMCSiteLocal()
   boolean isReachable(int timeout)
   boolean isReachable(NetworkInterface netif, int ttl, int timeout)
   ```



#### TCP Socket

1. What does getBytes() do?

```java
String
getBytes(), getBytes(Charset charset), getBytes(String charsetName)
Encodes this String into a sequence of bytes using the platform's default charset, storing the result into a new byte array.
```



2. Common methods for Socket Class

```java
// Socket: Creation
Socket(InetAddress remoteAddr, int remotePort)
Socket(String remoteHost, int remotePort)
Socket(InetAddress remoteAddr, int remotePort, InetAddress localAddr, int localPort)
Socket(String remoteHost, int remotePort, InetAddress localAddr, int localPort)
Socket()
    
//  Socket: Operations
void connect(SocketAddress destination)
void connect(SocketAddress destination, int timeout)
InputStream getInputStream()
OutputStream getOutputStream()
void close()
void shutdownInput()
void shutdownOutput()
    
//  Socket: Getting/testing attributes
InetAddress getInetAddress()
int getPort()
InetAddress getLocalAddress()
int getLocalPort()
SocketAddress getRemoteSocketAddress()
SocketAddress getLocalSocketAddress()
```



3. InetSocketAddress class

```java
//  InetSocketAddress: Creating and accessing
InetSocketAddress(InetAddress addr, int port)
InetSocketAddress(int port)
InetSocketAddress(String hostname, int port)
static InetSocketAddress createUnresolved(String host, int port)
boolean isUnresolved()
InetAddress getAddress()
int getPort()
String getHostName()
String toString()
```



4. ServerSocket class

```java
Socket clientSocket = serverSocket.accept();
```

The sole purpose of a ServerSocket instance is to supply a new, connected Socket instance for each new incoming TCP connection. When the server is ready to handle a client, it calls accept(), which blocks until an incoming connection is made to the ServerSocket’s port. (If a connection arrives between the time the server socket is constructed and the call to accept(), the new connection is queued, and in that case accept() returns immediately. See Section 6.4.1 for details of connection establishment.) The accept() method of ServerSocket returns an instance of Socket that is already connected to the client’s remote socket and ready for reading and writing. 

```java
//  ServerSocket: Creation
ServerSocket(int localPort)
ServerSocket(int localPort, int queueLimit)
ServerSocket(int localPort, int queueLimit, InetAddress localAddr)
ServerSocket()
    
//  ServerSocket: Operation
void bind(int port)
void bind(int port, int queuelimit)
Socket accept()
void close()
    
//  ServerSocket: Getting attributes
InetAddress getInetAddress()
SocketAddress getLocalSocketAddress()
int getLocalPort()
```



5.  Instream and OutStream

```java
// InputStream: Operation
abstract int read()
int read(byte[ ] data)
int read(byte[ ] data, int offset, int length)
int available()
void close()

// OutputStream: Operation
abstract void write(int data)
void write(byte[ ] data)
void write(byte[ ] data, int offset, int length)
void flush()
void close()
```

