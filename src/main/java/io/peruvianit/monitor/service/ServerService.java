package io.peruvianit.monitor.service;

import java.net.UnknownHostException;

import io.peruvianit.monitor.dto.ServerInfoDto;

public interface ServerService {
	
	ServerInfoDto infoServer() throws UnknownHostException ;
	
}
