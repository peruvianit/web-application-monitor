package io.peruvianit.monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostUtils {

	public static String name() throws UnknownHostException{
		InetAddress IP =InetAddress.getLocalHost();	
	 
		return IP.getHostName();
	}
	
	public static String ipAddress() throws UnknownHostException{
		InetAddress IP =InetAddress.getLocalHost();	
	 
		return IP.getHostAddress();
	}
	
}
