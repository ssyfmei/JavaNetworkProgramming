# Multicast

Multicast-Socket

```java
//	MulticastSocket: Creation
MulticastSocket()
MulticastSocket(int localPort)
MulticastSocket(SocketAddress bindaddr)
    
//  MulticastSocket: Group management
void joinGroup(InetAddress groupAddress)
void joinGroup(SocketAddress mcastaddr, NetworkInterface netIf)
void leaveGroup(InetAddress groupAddress)
void leaveGroup(SocketAddress mcastaddr, NetworkInterface netIf)
    
//	MulticastSocket: Setting/Getting multicast options
int getTimeToLive()
void setTimeToLive(int ttl)
boolean getLoopbackMode()
void setLoopbackMode(boolean disable)
InetAddress getInterface()
NetworkInterface getNetworkInterface()
void setInterface(InetAddress inf)
void setNetworkInterface(NetworkInterface netIf)
```

