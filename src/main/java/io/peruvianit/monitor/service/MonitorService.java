package io.peruvianit.monitor.service;

import java.util.List;

import io.peruvianit.monitor.dto.LogDto;
import io.peruvianit.monitor.dto.ThreadMxDto;
import io.peruvianit.monitor.error.exception.FileUtilsException;

public interface MonitorService {
	
	ThreadMxDto loadThreadsMx();
	
	List<LogDto> loadLogs(String webServerPathLog) throws FileUtilsException;
	
}
