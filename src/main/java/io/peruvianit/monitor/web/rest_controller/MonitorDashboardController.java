package io.peruvianit.monitor.web.rest_controller;

import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.peruvianit.monitor.dto.LogDto;
import io.peruvianit.monitor.dto.ServerInfoDto;
import io.peruvianit.monitor.dto.ThreadMxDto;
import io.peruvianit.monitor.error.exception.FileUtilsException;
import io.peruvianit.monitor.service.MonitorService;
import io.peruvianit.monitor.service.ServerService;

@RestController
@RequestMapping(MonitorDashboardController.URL_BASE)
@Validated
public class MonitorDashboardController{
	
	public static final String URL_BASE = "/io-peruvianit/monitor/dashboard/";
	
	public static final String URL_THREAD_MX = "thread-mx/";
	public static final String URL_LOGS = "logs/";
	public static final String URL_INFO_SERVER = "info-server/";
	
	private final String webServerPathLog;
	
	private final MonitorService monitorService;
	private final ServerService serverService;
	
	@Autowired
	public MonitorDashboardController(Environment environment, MonitorService monitorService, ServerService serverService) {
		this.webServerPathLog = environment.getProperty("web-server.path.log");
		this.monitorService = monitorService;
		this.serverService = serverService;
	}

	@GetMapping(URL_THREAD_MX)
	public ThreadMxDto loadThreadMx(){
		return monitorService.loadThreadsMx();
	}
	
	@GetMapping(URL_LOGS)
	public List<LogDto> loadLogs() throws FileUtilsException{
		return monitorService.loadLogs(webServerPathLog);
	}
	
	@GetMapping(URL_INFO_SERVER)
	public ServerInfoDto loadInfoServer() throws UnknownHostException {
		return serverService.infoServer();
	}
	
}
