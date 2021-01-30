package io.peruvianit.monitor.web.rest_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.peruvianit.monitor.dto.DashboardDto;
import io.peruvianit.monitor.error.exception.FileUtilsException;
import io.peruvianit.monitor.service.MonitorService;

@RestController
@RequestMapping(DashboardController.URL_BASE)
@Validated
public class DashboardController{
	
	public static final String URL_BASE = "/io-peruvianit/monitor/";
	
	private final String webServerPathLog;
	
	private final MonitorService monitorService;
	
	@Autowired
	public DashboardController(Environment environment, MonitorService monitorService) {
		this.webServerPathLog = environment.getProperty("web-server.path.log");
		this.monitorService = monitorService;
	}

	@GetMapping(path = {"dashboard/"})
	public DashboardDto loadDashboard() throws FileUtilsException{
		return  monitorService.loadDashboard(webServerPathLog);
	}
	
}
