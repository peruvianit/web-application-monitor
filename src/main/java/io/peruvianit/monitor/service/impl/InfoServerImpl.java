/**
 * 
 */
package io.peruvianit.monitor.service.impl;

import java.net.UnknownHostException;

import org.springframework.stereotype.Service;

import io.peruvianit.monitor.dto.ServerInfoDto;
import io.peruvianit.monitor.service.ServerService;
import io.peruvianit.monitor.util.HostUtils;

/**
 * @author Sergio Arellano {PeruViANit}
 *
 */
@Service
public class InfoServerImpl implements ServerService {

	@Override
	public ServerInfoDto infoServer() throws UnknownHostException {
		return ServerInfoDto.crea(HostUtils.name(), HostUtils.ipAddress());
	}

}
