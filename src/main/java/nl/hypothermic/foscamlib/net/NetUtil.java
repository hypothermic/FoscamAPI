package nl.hypothermic.foscamlib.net;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetUtil {
	
	public static InetAddress getConnectedSubnet() throws IOException {
		Enumeration nics = NetworkInterface.getNetworkInterfaces();
		while (nics.hasMoreElements()) {
			Enumeration<InetAddress> ias = ((NetworkInterface) nics.nextElement()).getInetAddresses();
			while (ias.hasMoreElements()) {
				InetAddress iaddr = ias.nextElement();
				if (iaddr instanceof Inet4Address && !iaddr.isAnyLocalAddress() && !iaddr.isLoopbackAddress()) {
					return iaddr;
				}
			}
		}
		throw new IOException("No LAN subnet found.");
	}
}
