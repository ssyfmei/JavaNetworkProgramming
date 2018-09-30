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

1. getBytes()

```java
String
getBytes(), getBytes(Charset charset), getBytes(String charsetName)
Encodes this String into a sequence of bytes using the platform's default charset, storing the result into a new byte array.
```



1. fd
2. ​
3. ​