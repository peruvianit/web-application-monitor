/**
 * 
 */
package io.peruvianit.monitor.dto;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
public class ServerInfoDto {

	private String hostname;
	private String ipAddress;
	
	private ServerInfoDto(String hostname, String ipAddress) {
		super();
		this.hostname = hostname;
		this.ipAddress = ipAddress;
	}
	
	public static ServerInfoDto crea(String hostname, String ipAddress) {
		return new ServerInfoDto(hostname, ipAddress);
	}

	public String getHostname() {
		return hostname;
	}

	public String getIpAddress() {
		return ipAddress;
	}
}
