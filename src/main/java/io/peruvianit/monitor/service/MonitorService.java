package io.peruvianit.monitor.service;

import io.peruvianit.monitor.dto.DashboardDto;
import io.peruvianit.monitor.error.exception.FileUtilsException;

public interface MonitorService {
	DashboardDto loadDashboard(String webServerPathLog) throws FileUtilsException;
}
