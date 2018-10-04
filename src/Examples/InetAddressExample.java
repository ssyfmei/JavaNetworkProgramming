package Examples;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class InetAddressExample {
	public static void main(String[] args) {
		/*
		 * The main function will find and display the network interfaces and associated
		 * addresses for the host
		 */
		try {
			// NetworkInterface.getNetworkInterfaces()
			Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
			if (interfaceList == null) {
				System.out.println("--No interfaces found--");
			} else {
				while (interfaceList.hasMoreElements()) {
					NetworkInterface interface1 = interfaceList.nextElement();
					System.out.println("Interface " + interface1.getName() + ":");
					
					// interface.getInetAddresses() : InetAddress
					Enumeration<InetAddress> inetAddresses = interface1.getInetAddresses();
					if (inetAddresses == null || !inetAddresses.hasMoreElements()) {
						System.out.println("\tNo Internet Address for this interface");
					} else {
						while (inetAddresses.hasMoreElements()) {
							InetAddress address = inetAddresses.nextElement();
							String info = "\tVersion ";
							if (address instanceof Inet4Address) {
								info += "4 ";
							} else {
								info += "6 ";
							}
							System.out.println(info + "internet address is:" + address.getHostAddress());
						}
					}
				}
			}
		} catch (SocketException se) {
			System.out.println("Error getting network interfaces:" + se.getMessage());
		}
		getAddresses(args);
	}

	private static void getAddresses(String[] args) {
		for (String host : args) {
			try {
				System.out.println(host + ":");
				InetAddress[] addressList = InetAddress.getAllByName(host);
				for (InetAddress address : addressList) {
					// address.getHostName() and address.getHostAddress()
					System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
				}
			} catch (UnknownHostException e) {
				System.out.println("\tUnable to find address for " + host);
			}
		}
	}
}